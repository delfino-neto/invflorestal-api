package br.com.inv.florestal.api.controllers;

import br.com.inv.florestal.api.dto.BulkPlotImportRequest;
import br.com.inv.florestal.api.dto.PlotImportRequest;
import br.com.inv.florestal.api.dto.PlotRequest;
import br.com.inv.florestal.api.dto.PlotRepresentation;
import br.com.inv.florestal.api.service.PlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plots")
public class PlotController {

    private final PlotService plotService;

    @PostMapping
    public ResponseEntity<PlotRepresentation> create(@RequestBody PlotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plotService.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<PlotRepresentation>> search(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "areaId", required = false) Long areaId
    ) {
        if (areaId != null) {
            return ResponseEntity.ok(plotService.searchByArea(areaId, page, size));
        }
        return ResponseEntity.ok(plotService.search(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlotRepresentation> findById(@PathVariable Long id) {
        return plotService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlotRepresentation> update(
            @PathVariable Long id,
            @RequestBody PlotRequest request
    ) {
        return ResponseEntity.ok(plotService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        plotService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/import")
    public ResponseEntity<PlotRepresentation> importPlot(@RequestBody PlotImportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plotService.importPlot(request));
    }

    @PostMapping("/import/bulk")
    public ResponseEntity<List<PlotRepresentation>> importPlots(@RequestBody BulkPlotImportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plotService.importPlots(request));
    }
}
