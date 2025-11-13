package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.SpecimenObjectRequest;
import br.com.inv.florestal.api.dto.SpecimenObjectRepresentation;
import br.com.inv.florestal.api.models.collection.Plot;
import br.com.inv.florestal.api.models.species.SpeciesTaxonomy;
import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.MediaRepository;
import br.com.inv.florestal.api.repository.PlotRepository;
import br.com.inv.florestal.api.repository.SpecimenObjectRepository;
import br.com.inv.florestal.api.repository.SpeciesTaxonomyRepository;
import br.com.inv.florestal.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de Espécimes (SpecimenObject)
 * 
 * Responsável por:
 * - CRUD de espécimes
 * - Busca de imagens associadas ao espécime via MediaRepository
 * - Conversão para DTO com dados completos (espécie, parcela, observador, imagens)
 */
@Service
@RequiredArgsConstructor
public class SpecimenObjectService {

    private final SpecimenObjectRepository specimenObjectRepository;
    private final PlotRepository plotRepository;
    private final SpeciesTaxonomyRepository speciesTaxonomyRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;

    public SpecimenObjectRepresentation create(SpecimenObjectRequest request) {
        Plot plot = plotRepository.findById(request.getPlotId())
                .orElseThrow(() -> new RuntimeException("Plot not found"));

        SpeciesTaxonomy species = speciesTaxonomyRepository.findById(request.getSpeciesId())
                .orElseThrow(() -> new RuntimeException("Species not found"));

        User observer = userRepository.findById(request.getObserverId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SpecimenObject specimenObject = SpecimenObject.builder()
                .plot(plot)
                .species(species)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .observer(observer)
                .build();
        
        return toRepresentation(specimenObjectRepository.save(specimenObject));
    }

    public Page<SpecimenObjectRepresentation> search(Integer page, Integer size) {
        return specimenObjectRepository.findAll(PageRequest.of(page, size))
                .map(this::toRepresentation);
    }

    public Optional<SpecimenObjectRepresentation> findById(Long id) {
        return specimenObjectRepository.findById(id).map(this::toRepresentation);
    }

    public SpecimenObjectRepresentation update(Long id, SpecimenObjectRequest request) {
        SpecimenObject specimenObject = specimenObjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specimen Object not found"));

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

    public void delete(Long id) {
        specimenObjectRepository.deleteById(id);
    }

    private SpecimenObjectRepresentation toRepresentation(SpecimenObject specimenObject) {
        return SpecimenObjectRepresentation.builder()
                .id(specimenObject.getId())
                .plotId(specimenObject.getPlot().getId())
                .plotCode(specimenObject.getPlot().getPlotCode())
                .areaId(specimenObject.getPlot().getArea() != null ? specimenObject.getPlot().getArea().getId() : null)
                .areaName(specimenObject.getPlot().getArea() != null ? specimenObject.getPlot().getArea().getName() : null)
                .speciesId(specimenObject.getSpecies().getId())
                .speciesScientificName(specimenObject.getSpecies().getScientificName())
                .speciesCommonName(specimenObject.getSpecies().getCommonName())
                .latitude(specimenObject.getLatitude())
                .longitude(specimenObject.getLongitude())
                .observerId(specimenObject.getObserver().getId())
                .observerFullName(specimenObject.getObserver().fullName())
                .imageUrls(getSpecimenImages(specimenObject.getId()))
                .createdAt(specimenObject.getCreatedAt())
                .updatedAt(specimenObject.getUpdatedAt())
                .build();
    }

    /**
     * Busca todas as URLs de imagens associadas a um espécime
     */
    private List<String> getSpecimenImages(Long specimenObjectId) {
        return mediaRepository.findByObjectId(specimenObjectId, PageRequest.of(0, 100))
                .getContent()
                .stream()
                .map(media -> media.getUrl())
                .collect(Collectors.toList());
    }
}
