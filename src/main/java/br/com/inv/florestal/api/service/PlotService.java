package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.PlotRequest;
import br.com.inv.florestal.api.dto.PlotRepresentation;
import br.com.inv.florestal.api.models.collection.CollectionArea;
import br.com.inv.florestal.api.models.collection.Plot;
import br.com.inv.florestal.api.repository.CollectionAreaRepository;
import br.com.inv.florestal.api.repository.PlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlotService {

    private final PlotRepository plotRepository;
    private final CollectionAreaRepository collectionAreaRepository;

    public PlotRepresentation create(PlotRequest request) {
        CollectionArea area = collectionAreaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new RuntimeException("Collection Area not found"));

        Plot plot = Plot.builder()
                .area(area)
                .geometry(request.getGeometry())
                .plotCode(request.getPlotCode())
                .areaM2(request.getAreaM2())
                .slopeDeg(request.getSlopeDeg())
                .aspectDeg(request.getAspectDeg())
                .notes(request.getNotes())
                .build();
        return toRepresentation(plotRepository.save(plot));
    }

    public Page<PlotRepresentation> search(Integer page, Integer size) {
        return plotRepository.findAll(PageRequest.of(page, size))
                .map(this::toRepresentation);
    }

    public Optional<PlotRepresentation> findById(Long id) {
        return plotRepository.findById(id).map(this::toRepresentation);
    }

    public PlotRepresentation update(Long id, PlotRequest request) {
        Plot plot = plotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plot not found"));

        CollectionArea area = collectionAreaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new RuntimeException("Collection Area not found"));

        plot.setArea(area);
        plot.setGeometry(request.getGeometry());
        plot.setPlotCode(request.getPlotCode());
        plot.setAreaM2(request.getAreaM2());
        plot.setSlopeDeg(request.getSlopeDeg());
        plot.setAspectDeg(request.getAspectDeg());
        plot.setNotes(request.getNotes());

        return toRepresentation(plotRepository.save(plot));
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
