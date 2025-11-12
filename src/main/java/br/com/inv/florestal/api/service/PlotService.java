package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.PlotRequest;
import br.com.inv.florestal.api.dto.PlotRepresentation;
import br.com.inv.florestal.api.models.collection.Plot;
import br.com.inv.florestal.api.repository.CollectionAreaRepository;
import br.com.inv.florestal.api.repository.PlotRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlotService {

    private final PlotRepository plotRepository;
    private final CollectionAreaRepository collectionAreaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public PlotRepresentation create(PlotRequest request) {
        // Validar se a área existe
        collectionAreaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new RuntimeException("Collection Area not found"));

        // Usar query nativa para fazer o cast do geometry
        String sql = "INSERT INTO plot (area_id, geometry, plot_code, area_m2, slope_deg, aspect_deg, notes, created_at) " +
                     "VALUES (?1, CAST(?2 AS polygon), ?3, ?4, ?5, ?6, ?7, CURRENT_TIMESTAMP) RETURNING id";
        
        Long id = ((Number) entityManager.createNativeQuery(sql)
                .setParameter(1, request.getAreaId())
                .setParameter(2, request.getGeometry())
                .setParameter(3, request.getPlotCode())
                .setParameter(4, request.getAreaM2())
                .setParameter(5, request.getSlopeDeg())
                .setParameter(6, request.getAspectDeg())
                .setParameter(7, request.getNotes())
                .getSingleResult()).longValue();
        
        // Buscar o registro completo
        Plot plot = plotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to create plot"));
        
        return toRepresentation(plot);
    }

    public Page<PlotRepresentation> search(Integer page, Integer size) {
        return plotRepository.findAll(PageRequest.of(page, size))
                .map(this::toRepresentation);
    }

    public Page<PlotRepresentation> searchByArea(Long areaId, Integer page, Integer size) {
        return plotRepository.findByAreaId(areaId, PageRequest.of(page, size))
                .map(this::toRepresentation);
    }

    public Optional<PlotRepresentation> findById(Long id) {
        return plotRepository.findById(id).map(this::toRepresentation);
    }

    @Transactional
    public PlotRepresentation update(Long id, PlotRequest request) {
        // Verificar se o plot existe
        plotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plot not found"));

        // Validar se a área existe
        collectionAreaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new RuntimeException("Collection Area not found"));

        // Usar query nativa para fazer o cast do geometry
        String sql = "UPDATE plot SET area_id = ?1, geometry = CAST(?2 AS polygon), " +
                     "plot_code = ?3, area_m2 = ?4, slope_deg = ?5, aspect_deg = ?6, " +
                     "notes = ?7, updated_at = CURRENT_TIMESTAMP WHERE id = ?8";
        
        entityManager.createNativeQuery(sql)
                .setParameter(1, request.getAreaId())
                .setParameter(2, request.getGeometry())
                .setParameter(3, request.getPlotCode())
                .setParameter(4, request.getAreaM2())
                .setParameter(5, request.getSlopeDeg())
                .setParameter(6, request.getAspectDeg())
                .setParameter(7, request.getNotes())
                .setParameter(8, id)
                .executeUpdate();
        
        // Buscar o registro atualizado
        Plot updatedPlot = plotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to update plot"));
        
        return toRepresentation(updatedPlot);
    }

    public void delete(Long id) {
        plotRepository.deleteById(id);
    }

    private PlotRepresentation toRepresentation(Plot plot) {
        return PlotRepresentation.builder()
                .id(plot.getId())
                .areaId(plot.getArea().getId())
                .areaName(plot.getArea().getName())
                .geometry(plot.getGeometry())
                .plotCode(plot.getPlotCode())
                .areaM2(plot.getAreaM2())
                .slopeDeg(plot.getSlopeDeg())
                .aspectDeg(plot.getAspectDeg())
                .notes(plot.getNotes())
                .createdAt(plot.getCreatedAt())
                .updatedAt(plot.getUpdatedAt())
                .build();
    }
}
