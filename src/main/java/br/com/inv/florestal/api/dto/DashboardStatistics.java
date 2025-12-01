package br.com.inv.florestal.api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatistics {
    private Long totalSpecimens;
    private Long totalSpecies;
    private Long totalCollectionAreas;
    private Long recentSpecimens; // últimos 7 dias
    private List<SpeciesDistribution> topSpecies;
    private List<RecentActivity> recentActivities;
    
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpeciesDistribution {
        private String speciesName;
        private Long count;
    }
    
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentActivity {
        private Long id;
        private String action;
        private String species;
        private String time;
        private String user;
    }
}
