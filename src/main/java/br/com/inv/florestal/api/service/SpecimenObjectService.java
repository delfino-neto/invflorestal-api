package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.SpecimenObjectRequest;
import br.com.inv.florestal.api.dto.SpecimenObjectRepresentation;
import br.com.inv.florestal.api.models.collection.Plot;
import br.com.inv.florestal.api.models.species.SpeciesTaxonomy;
import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.PlotRepository;
import br.com.inv.florestal.api.repository.SpecimenObjectRepository;
import br.com.inv.florestal.api.repository.SpeciesTaxonomyRepository;
import br.com.inv.florestal.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SpecimenObjectService {

    private final SpecimenObjectRepository specimenObjectRepository;
    private final PlotRepository plotRepository;
    private final SpeciesTaxonomyRepository speciesTaxonomyRepository;
    private final UserRepository userRepository;
    private final Random random = new Random();

    // URLs mockadas de imagens de plantas/árvores do Unsplash
    private static final List<String> SAMPLE_IMAGES = Arrays.asList(
            "https://images.unsplash.com/photo-1518531933037-91b2f5f229cc?w=800",
            "https://images.unsplash.com/photo-1466692476868-aef1dfb1e735?w=800",
            "https://images.unsplash.com/photo-1502082553048-f009c37129b9?w=800",
            "https://images.unsplash.com/photo-1513836279014-a89f7a76ae86?w=800",
            "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800",
            "https://images.unsplash.com/photo-1542273917363-3b1817f69a2d?w=800",
            "https://images.unsplash.com/photo-1501004318641-b39e6451bec6?w=800",
            "https://images.unsplash.com/photo-1470058869958-2a77ade41c02?w=800"
    );

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
                .imageUrls(generateMockImages())
                .createdAt(specimenObject.getCreatedAt())
                .updatedAt(specimenObject.getUpdatedAt())
                .build();
    }

    // Método temporário para gerar imagens mockadas
    private List<String> generateMockImages() {
        int count = random.nextInt(4) + 1; // 1 a 4 imagens
        return SAMPLE_IMAGES.subList(0, Math.min(count, SAMPLE_IMAGES.size()));
    }
}
