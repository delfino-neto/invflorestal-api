package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.SpeciesTaxonomyRequest;
import br.com.inv.florestal.api.dto.SpeciesTaxonomyRepresentation;
import br.com.inv.florestal.api.models.species.SpeciesTaxonomy;
import br.com.inv.florestal.api.repository.SpeciesTaxonomyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpeciesTaxonomyService {

    private final SpeciesTaxonomyRepository speciesTaxonomyRepository;

    public SpeciesTaxonomyRepresentation create(SpeciesTaxonomyRequest request) {
        SpeciesTaxonomy speciesTaxonomy = SpeciesTaxonomy.builder()
                .scientificName(request.getScientificName())
                .commonName(request.getCommonName())
                .family(request.getFamily())
                .genus(request.getGenus())
                .code(request.getCode())
                .build();
        return toRepresentation(speciesTaxonomyRepository.save(speciesTaxonomy));
    }

    public Page<SpeciesTaxonomyRepresentation> search(Integer page, Integer size, String searchTerm, String family, String genus) {
        return speciesTaxonomyRepository.searchWithFilters(
                searchTerm, 
                family, 
                genus, 
                PageRequest.of(page, size)
        ).map(this::toRepresentation);
    }
    
    public List<String> getDistinctFamilies() {
        return speciesTaxonomyRepository.findDistinctFamilies();
    }
    
    public List<String> getDistinctGenera() {
        return speciesTaxonomyRepository.findDistinctGenera();
    }

    public Optional<SpeciesTaxonomyRepresentation> findById(Long id) {
        return speciesTaxonomyRepository.findById(id).map(this::toRepresentation);
    }

    public SpeciesTaxonomyRepresentation update(Long id, SpeciesTaxonomyRequest request) {
        SpeciesTaxonomy speciesTaxonomy = speciesTaxonomyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Species Taxonomy not found"));

        speciesTaxonomy.setScientificName(request.getScientificName());
        speciesTaxonomy.setCommonName(request.getCommonName());
        speciesTaxonomy.setFamily(request.getFamily());
        speciesTaxonomy.setGenus(request.getGenus());
        speciesTaxonomy.setCode(request.getCode());

        return toRepresentation(speciesTaxonomyRepository.save(speciesTaxonomy));
    }

    public void delete(Long id) {
        speciesTaxonomyRepository.deleteById(id);
    }

    private SpeciesTaxonomyRepresentation toRepresentation(SpeciesTaxonomy speciesTaxonomy) {
        return SpeciesTaxonomyRepresentation.builder()
                .id(speciesTaxonomy.getId())
                .scientificName(speciesTaxonomy.getScientificName())
                .commonName(speciesTaxonomy.getCommonName())
                .family(speciesTaxonomy.getFamily())
                .genus(speciesTaxonomy.getGenus())
                .code(speciesTaxonomy.getCode())
                .createdAt(speciesTaxonomy.getCreatedAt())
                .updatedAt(speciesTaxonomy.getUpdatedAt())
                .build();
    }
}
