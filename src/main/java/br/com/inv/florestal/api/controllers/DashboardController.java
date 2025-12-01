package br.com.inv.florestal.api.controllers;

import br.com.inv.florestal.api.dto.DashboardStatistics;
import br.com.inv.florestal.api.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    @GetMapping("/statistics")
    public ResponseEntity<DashboardStatistics> getStatistics() {
        DashboardStatistics statistics = dashboardService.getStatistics();
        return ResponseEntity.ok(statistics);
    }
}
