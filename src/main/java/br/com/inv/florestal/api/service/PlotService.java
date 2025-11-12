package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.BulkPlotImportRequest;
import br.com.inv.florestal.api.dto.PlotImportRequest;
import br.com.inv.florestal.api.dto.PlotRequest;
import br.com.inv.florestal.api.dto.PlotRepresentation;
import br.com.inv.florestal.api.models.collection.CollectionArea;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
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

    @Transactional
    public PlotRepresentation importPlot(PlotImportRequest request) {
        // Validar área de destino
        collectionAreaRepository.findById(request.getTargetAreaId())
                .orElseThrow(() -> new RuntimeException("Target Collection Area not found"));

        String geometry;
        BigDecimal totalArea = BigDecimal.ZERO;
        BigDecimal avgSlope = null;
        BigDecimal avgAspect = null;
        String notes = "";

        if (request.getImportType() == PlotImportRequest.ImportType.AREA) {
            // Importar área inteira como um único plot
            CollectionArea sourceArea = collectionAreaRepository.findById(request.getSourceAreaId())
                    .orElseThrow(() -> new RuntimeException("Source Collection Area not found"));
            
            // Usar a geometria da área
            geometry = sourceArea.getGeometry();
            
            // Buscar todos os plots da área fonte para calcular médias
            List<Plot> sourcePlots = plotRepository.findByAreaId(request.getSourceAreaId(), PageRequest.of(0, 1000)).getContent();
            
            if (!sourcePlots.isEmpty()) {
                // Calcular área total
                totalArea = sourcePlots.stream()
                        .map(Plot::getAreaM2)
                        .filter(area -> area != null)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                // Calcular média de inclinação
                long slopeCount = sourcePlots.stream().filter(p -> p.getSlopeDeg() != null).count();
                if (slopeCount > 0) {
                    BigDecimal slopeSum = sourcePlots.stream()
                            .filter(p -> p.getSlopeDeg() != null)
                            .map(Plot::getSlopeDeg)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    avgSlope = slopeSum.divide(BigDecimal.valueOf(slopeCount), 2, RoundingMode.HALF_UP);
                }
                
                // Calcular média de aspecto
                long aspectCount = sourcePlots.stream().filter(p -> p.getAspectDeg() != null).count();
                if (aspectCount > 0) {
                    BigDecimal aspectSum = sourcePlots.stream()
                            .filter(p -> p.getAspectDeg() != null)
                            .map(Plot::getAspectDeg)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    avgAspect = aspectSum.divide(BigDecimal.valueOf(aspectCount), 2, RoundingMode.HALF_UP);
                }
                
                notes = String.format("Importado da área '%s' (%d plots consolidados)", 
                        sourceArea.getName(), sourcePlots.size());
            } else {
                notes = String.format("Importado da área '%s'", sourceArea.getName());
            }
            
        } else if (request.getImportType() == PlotImportRequest.ImportType.PLOT) {
            // Importar plot individual
            Plot sourcePlot = plotRepository.findById(request.getSourcePlotId())
                    .orElseThrow(() -> new RuntimeException("Source Plot not found"));
            
            geometry = sourcePlot.getGeometry();
            totalArea = sourcePlot.getAreaM2();
            avgSlope = sourcePlot.getSlopeDeg();
            avgAspect = sourcePlot.getAspectDeg();
            notes = String.format("Importado do plot '%s' da área '%s'", 
                    sourcePlot.getPlotCode(), sourcePlot.getArea().getName());
            
            // Concatenar notas se existirem
            if (sourcePlot.getNotes() != null && !sourcePlot.getNotes().isEmpty()) {
                notes += "\nNotas originais: " + sourcePlot.getNotes();
            }
        } else {
            throw new RuntimeException("Invalid import type");
        }

        // Criar o novo plot usando query nativa
        String sql = "INSERT INTO plot (area_id, geometry, plot_code, area_m2, slope_deg, aspect_deg, notes, created_at) " +
                     "VALUES (?1, CAST(?2 AS polygon), ?3, ?4, ?5, ?6, ?7, CURRENT_TIMESTAMP) RETURNING id";
        
        Long id = ((Number) entityManager.createNativeQuery(sql)
                .setParameter(1, request.getTargetAreaId())
                .setParameter(2, geometry)
                .setParameter(3, request.getPlotCode())
                .setParameter(4, totalArea)
                .setParameter(5, avgSlope)
                .setParameter(6, avgAspect)
                .setParameter(7, notes)
                .getSingleResult()).longValue();
        
        // Buscar o registro completo
        Plot plot = plotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to import plot"));
        
        return toRepresentation(plot);
    }

    @Transactional
    public List<PlotRepresentation> importPlots(BulkPlotImportRequest bulkRequest) {
        // Validar área de destino
        collectionAreaRepository.findById(bulkRequest.getTargetAreaId())
                .orElseThrow(() -> new RuntimeException("Target Collection Area not found"));

        List<PlotRepresentation> importedPlots = new java.util.ArrayList<>();

        for (BulkPlotImportRequest.ImportItem item : bulkRequest.getItems()) {
            String geometry;
            BigDecimal totalArea = BigDecimal.ZERO;
            BigDecimal avgSlope = null;
            BigDecimal avgAspect = null;
            String notes = "";

            if (item.getImportType() == BulkPlotImportRequest.ImportType.AREA) {
                // Importar área inteira como um único plot
                CollectionArea sourceArea = collectionAreaRepository.findById(item.getSourceAreaId())
                        .orElseThrow(() -> new RuntimeException("Source Collection Area not found"));
                
                geometry = sourceArea.getGeometry();
                
                List<Plot> sourcePlots = plotRepository.findByAreaId(item.getSourceAreaId(), PageRequest.of(0, 1000)).getContent();
                
                if (!sourcePlots.isEmpty()) {
                    totalArea = sourcePlots.stream()
                            .map(Plot::getAreaM2)
                            .filter(area -> area != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    
                    long slopeCount = sourcePlots.stream().filter(p -> p.getSlopeDeg() != null).count();
                    if (slopeCount > 0) {
                        BigDecimal slopeSum = sourcePlots.stream()
                                .filter(p -> p.getSlopeDeg() != null)
                                .map(Plot::getSlopeDeg)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        avgSlope = slopeSum.divide(BigDecimal.valueOf(slopeCount), 2, RoundingMode.HALF_UP);
                    }
                    
                    long aspectCount = sourcePlots.stream().filter(p -> p.getAspectDeg() != null).count();
                    if (aspectCount > 0) {
                        BigDecimal aspectSum = sourcePlots.stream()
                                .filter(p -> p.getAspectDeg() != null)
                                .map(Plot::getAspectDeg)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        avgAspect = aspectSum.divide(BigDecimal.valueOf(aspectCount), 2, RoundingMode.HALF_UP);
                    }
                    
                    notes = String.format("Importado da área '%s' (%d plots consolidados)", 
                            sourceArea.getName(), sourcePlots.size());
                } else {
                    notes = String.format("Importado da área '%s'", sourceArea.getName());
                }
                
            } else if (item.getImportType() == BulkPlotImportRequest.ImportType.PLOT) {
                // Importar plot individual
                Plot sourcePlot = plotRepository.findById(item.getSourcePlotId())
                        .orElseThrow(() -> new RuntimeException("Source Plot not found"));
                
                geometry = sourcePlot.getGeometry();
                totalArea = sourcePlot.getAreaM2();
                avgSlope = sourcePlot.getSlopeDeg();
                avgAspect = sourcePlot.getAspectDeg();
                notes = String.format("Importado do plot '%s' da área '%s'", 
                        sourcePlot.getPlotCode(), sourcePlot.getArea().getName());
                
                if (sourcePlot.getNotes() != null && !sourcePlot.getNotes().isEmpty()) {
                    notes += "\nNotas originais: " + sourcePlot.getNotes();
                }
            } else {
                throw new RuntimeException("Invalid import type");
            }

            // Criar o novo plot
            String sql = "INSERT INTO plot (area_id, geometry, plot_code, area_m2, slope_deg, aspect_deg, notes, created_at) " +
                         "VALUES (?1, CAST(?2 AS polygon), ?3, ?4, ?5, ?6, ?7, CURRENT_TIMESTAMP) RETURNING id";
            
            Long id = ((Number) entityManager.createNativeQuery(sql)
                    .setParameter(1, bulkRequest.getTargetAreaId())
                    .setParameter(2, geometry)
                    .setParameter(3, item.getPlotCode())
                    .setParameter(4, totalArea)
                    .setParameter(5, avgSlope)
                    .setParameter(6, avgAspect)
                    .setParameter(7, notes)
                    .getSingleResult()).longValue();
            
            Plot plot = plotRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Failed to import plot"));
            
            importedPlots.add(toRepresentation(plot));
        }

        return importedPlots;
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
