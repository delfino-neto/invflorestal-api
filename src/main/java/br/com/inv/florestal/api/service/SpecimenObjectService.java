package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.SpecimenObjectRequest;
import br.com.inv.florestal.api.dto.SpecimenObjectRepresentation;
import br.com.inv.florestal.api.models.collection.Plot;
import br.com.inv.florestal.api.models.species.SpeciesTaxonomy;
import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import br.com.inv.florestal.api.models.specimen.SpeciesInfo;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.MediaRepository;
import br.com.inv.florestal.api.repository.PlotRepository;
import br.com.inv.florestal.api.repository.SpecimenObjectRepository;
import br.com.inv.florestal.api.repository.SpeciesInfoRepository;
import br.com.inv.florestal.api.repository.SpeciesTaxonomyRepository;
import br.com.inv.florestal.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.inv.florestal.api.aspect.Auditable;
import br.com.inv.florestal.api.models.audit.AuditLog.AuditAction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecimenObjectService {

    private final SpecimenObjectRepository specimenObjectRepository;
    private final PlotRepository plotRepository;
    private final SpeciesTaxonomyRepository speciesTaxonomyRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;
    private final SpeciesInfoRepository speciesInfoRepository;

    @Auditable(action = AuditAction.CREATE, entityName = "SpecimenObject", description = "Novo espécime registrado")
    public SpecimenObjectRepresentation create(SpecimenObjectRequest request) {
        Plot plot = plotRepository.findById(request.getPlotId())
                .orElseThrow(() -> new RuntimeException("Plot not found"));

        SpeciesTaxonomy species = speciesTaxonomyRepository.findById(request.getSpeciesId())
                .orElseThrow(() -> new RuntimeException("Species not found"));

        User observer;
        if (request.getObserverId() != null) {
            observer = userRepository.findById(request.getObserverId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            System.out.println("   Observer encontrado: " + observer.getName() + " (ID: " + observer.getId() + ")");
        } else {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            observer = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        }

        SpecimenObject specimenObject = SpecimenObject.builder()
                .plot(plot)
                .species(species)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .observer(observer)
                .build();
        
        specimenObject = specimenObjectRepository.save(specimenObject);
        
        if (hasSpeciesInfoData(request)) {
            SpeciesInfo speciesInfo = SpeciesInfo.builder()
                .object(specimenObject)
                .observationDate(request.getObservationDate() != null ? request.getObservationDate() : LocalDateTime.now())
                .heightM(request.getHeightM())
                .dbmCm(request.getDbmCm())
                .ageYears(request.getAgeYears())
                .condition(request.getCondition())
                .build();
            
            speciesInfoRepository.save(speciesInfo);
        }
        
        return toRepresentation(specimenObject);
    }

    public Page<SpecimenObjectRepresentation> search(Integer page, Integer size) {
        return specimenObjectRepository.findAll(PageRequest.of(page, size))
                .map(this::toRepresentation);
    }

    public Optional<SpecimenObjectRepresentation> findById(Long id) {
        return specimenObjectRepository.findById(id).map(this::toRepresentation);
    }
    
    public List<SpecimenObjectRepresentation> findByCollectionAreaId(Long areaId) {
        return specimenObjectRepository.findByCollectionAreaId(areaId).stream()
                .map(this::toRepresentation)
                .collect(Collectors.toList());
    }
    
    public List<SpecimenObjectRepresentation> findByPlotId(Long plotId) {
        return specimenObjectRepository.findByPlotId(plotId).stream()
                .map(this::toRepresentation)
                .collect(Collectors.toList());
    }
    
    public List<SpecimenObjectRepresentation> findByPlotIdAndObserverId(Long plotId, Long observerId) {
        List<SpecimenObject> specimens = specimenObjectRepository.findByPlotIdAndObserverId(plotId, observerId);
        
        return specimens.stream()
                .map(this::toRepresentation)
                .collect(Collectors.toList());
    }
    
    public List<SpecimenObjectRepresentation> findWithFilters(Long speciesId, Long areaId, Long observerId) {
        return specimenObjectRepository.findWithFilters(speciesId, areaId, observerId).stream()
                .map(this::toRepresentation)
                .collect(Collectors.toList());
    }

    @Auditable(action = AuditAction.UPDATE, entityName = "SpecimenObject", description = "Espécime atualizado")
    public SpecimenObjectRepresentation update(Long id, SpecimenObjectRequest request) {
        SpecimenObject specimenObject = specimenObjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specimen Object not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            if (!specimenObject.getObserver().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Você não tem permissão para editar este espécime");
            }
        } else {
            throw new RuntimeException("Usuário não autenticado");
        }

        Plot plot = plotRepository.findById(request.getPlotId())
                .orElseThrow(() -> new RuntimeException("Plot not found"));

        SpeciesTaxonomy species = speciesTaxonomyRepository.findById(request.getSpeciesId())
                .orElseThrow(() -> new RuntimeException("Species not found"));

        User observer = userRepository.findById(request.getObserverId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        specimenObject.setPlot(plot);
        specimenObject.setSpecies(species);
        specimenObject.setLatitude(request.getLatitude());
        specimenObject.setLongitude(request.getLongitude());
        specimenObject.setObserver(observer);

        return toRepresentation(specimenObjectRepository.save(specimenObject));
    }

    @Auditable(action = AuditAction.DELETE, entityName = "SpecimenObject", description = "Espécime excluído")
    public void delete(Long id) {
        SpecimenObject specimenObject = specimenObjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specimen Object not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            if (!specimenObject.getObserver().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Você não tem permissão para excluir este espécime");
            }
        } else {
            throw new RuntimeException("Usuário não autenticado");
        }

        specimenObjectRepository.deleteById(id);
    }

    private boolean hasSpeciesInfoData(SpecimenObjectRequest request) {
        return request.getHeightM() != null 
            || request.getDbmCm() != null 
            || request.getAgeYears() != null 
            || request.getCondition() != null
            || request.getObservationDate() != null;
    }

    private SpecimenObjectRepresentation toRepresentation(SpecimenObject specimenObject) {
        return SpecimenObjectRepresentation.builder()
                .id(specimenObject.getId())
                .plotId(specimenObject.getPlot() != null ? specimenObject.getPlot().getId() : null)
                .plotCode(specimenObject.getPlot() != null ? specimenObject.getPlot().getPlotCode() : null)
                .areaId(specimenObject.getPlot() != null && specimenObject.getPlot().getArea() != null ? specimenObject.getPlot().getArea().getId() : null)
                .areaName(specimenObject.getPlot() != null && specimenObject.getPlot().getArea() != null ? specimenObject.getPlot().getArea().getName() : null)
                .speciesId(specimenObject.getSpecies() != null ? specimenObject.getSpecies().getId() : null)
                .speciesScientificName(specimenObject.getSpecies() != null ? specimenObject.getSpecies().getScientificName() : null)
                .speciesCommonName(specimenObject.getSpecies() != null ? specimenObject.getSpecies().getCommonName() : null)
                .latitude(specimenObject.getLatitude())
                .longitude(specimenObject.getLongitude())
                .observerId(specimenObject.getObserver() != null ? specimenObject.getObserver().getId() : null)
                .observerFullName(specimenObject.getObserver() != null ? specimenObject.getObserver().fullName() : null)
                .imageUrls(getSpecimenImages(specimenObject.getId()))
                .createdAt(specimenObject.getCreatedAt())
                .updatedAt(specimenObject.getUpdatedAt())
                .build();
    }

    private List<String> getSpecimenImages(Long specimenObjectId) {
        return mediaRepository.findByObjectId(specimenObjectId, PageRequest.of(0, 100))
                .getContent()
                .stream()
                .map(media -> media.getUrl())
                .collect(Collectors.toList());
    }
}
