package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.specimen.CreateSpeciesInfoDTO;
import br.com.inv.florestal.api.dto.specimen.SpeciesInfoDTO;
import br.com.inv.florestal.api.models.specimen.SpeciesInfo;
import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import br.com.inv.florestal.api.repository.SpeciesInfoRepository;
import br.com.inv.florestal.api.repository.SpecimenObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpeciesInfoService {

    private final SpeciesInfoRepository speciesInfoRepository;
    private final SpecimenObjectRepository specimenObjectRepository;

    @Transactional
    public SpeciesInfoDTO create(CreateSpeciesInfoDTO dto) {
        SpecimenObject specimen = specimenObjectRepository.findById(dto.getObjectId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Espécime não encontrado com ID: " + dto.getObjectId()
                ));

        SpeciesInfo info = SpeciesInfo.builder()
                .object(specimen)
                .observationDate(dto.getObservationDate() != null ? dto.getObservationDate() : LocalDateTime.now())
                .heightM(dto.getHeightM())
                .dbmCm(dto.getDbmCm())
                .ageYears(dto.getAgeYears())
                .condition(dto.getCondition())
                .build();

        SpeciesInfo saved = speciesInfoRepository.save(info);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<SpeciesInfoDTO> getHistoryBySpecimenId(Long specimenId) {
        List<SpeciesInfo> history = speciesInfoRepository.findByObjectIdOrderByObservationDateDesc(specimenId);
        return history.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<SpeciesInfoDTO> getHistoryBySpecimenId(Long specimenId, int page, int size) {
        SpecimenObject specimen = specimenObjectRepository.findById(specimenId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Espécime não encontrado com ID: " + specimenId
                ));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "observationDate"));
        Page<SpeciesInfo> historyPage = speciesInfoRepository.findByObjectOrderByObservationDateDesc(specimen, pageable);
        
        return historyPage.map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public SpeciesInfoDTO getLatestBySpecimenId(Long specimenId) {
        return speciesInfoRepository.findLatestByObjectId(specimenId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public SpeciesInfoDTO getById(Long id) {
        return speciesInfoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Informação não encontrada com ID: " + id
                ));
    }

    @Transactional
    public SpeciesInfoDTO update(Long id, CreateSpeciesInfoDTO dto) {
        SpeciesInfo existing = speciesInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Informação não encontrada com ID: " + id
                ));

        if (dto.getHeightM() != null) {
            existing.setHeightM(dto.getHeightM());
        }
        if (dto.getDbmCm() != null) {
            existing.setDbmCm(dto.getDbmCm());
        }
        if (dto.getAgeYears() != null) {
            existing.setAgeYears(dto.getAgeYears());
        }
        if (dto.getCondition() != null) {
            existing.setCondition(dto.getCondition());
        }

        SpeciesInfo updated = speciesInfoRepository.save(existing);
        return toDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!speciesInfoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Informação não encontrada com ID: " + id
            );
        }
        speciesInfoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countBySpecimenId(Long specimenId) {
        SpecimenObject specimen = specimenObjectRepository.findById(specimenId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Espécime não encontrado com ID: " + specimenId
                ));
        return speciesInfoRepository.countByObject(specimen);
    }

    private SpeciesInfoDTO toDTO(SpeciesInfo info) {
        return SpeciesInfoDTO.builder()
                .id(info.getId())
                .objectId(info.getObject().getId())
                .observationDate(info.getObservationDate())
                .heightM(info.getHeightM())
                .dbmCm(info.getDbmCm())
                .ageYears(info.getAgeYears())
                .condition(info.getCondition())
                .createdAt(info.getCreatedAt())
                .updatedAt(info.getUpdatedAt())
                .build();
    }
}
