package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.DashboardStatistics;
import br.com.inv.florestal.api.repository.CollectionAreaRepository;
import br.com.inv.florestal.api.repository.SpecimenObjectRepository;
import br.com.inv.florestal.api.repository.SpeciesTaxonomyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    
    private final SpecimenObjectRepository specimenObjectRepository;
    private final SpeciesTaxonomyRepository speciesTaxonomyRepository;
    private final CollectionAreaRepository collectionAreaRepository;
    
    public DashboardStatistics getStatistics() {
        Long totalSpecimens = specimenObjectRepository.count();
        Long totalSpecies = speciesTaxonomyRepository.count();
        Long totalCollectionAreas = collectionAreaRepository.count();
        
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        Long recentSpecimens = specimenObjectRepository.countRecentSpecimens(sevenDaysAgo);
        
        List<DashboardStatistics.SpeciesDistribution> topSpecies = 
            specimenObjectRepository.findTopSpeciesDistribution()
                .stream()
                .limit(5)
                .map(obj -> DashboardStatistics.SpeciesDistribution.builder()
                    .speciesName((String) obj[0])
                    .count((Long) obj[1])
                    .build())
                .collect(Collectors.toList());
        
        List<DashboardStatistics.RecentActivity> recentActivities = 
            specimenObjectRepository.findRecentActivities()
                .stream()
                .limit(10)
                .map(obj -> {
                    LocalDateTime createdAt = (LocalDateTime) obj[3];
                    String timeAgo = getTimeAgo(createdAt);
                    
                    return DashboardStatistics.RecentActivity.builder()
                        .id((Long) obj[0])
                        .action("Novo espécime cadastrado")
                        .species((String) obj[1])
                        .time(timeAgo)
                        .user((String) obj[2])
                        .build();
                })
                .collect(Collectors.toList());
        
        return DashboardStatistics.builder()
                .totalSpecimens(totalSpecimens)
                .totalSpecies(totalSpecies)
                .totalCollectionAreas(totalCollectionAreas)
                .recentSpecimens(recentSpecimens)
                .topSpecies(topSpecies)
                .recentActivities(recentActivities)
                .build();
    }
    
    private String getTimeAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(dateTime, now);
        long hours = ChronoUnit.HOURS.between(dateTime, now);
        long days = ChronoUnit.DAYS.between(dateTime, now);
        
        if (minutes < 60) {
            return minutes + " minuto" + (minutes != 1 ? "s" : "") + " atrás";
        } else if (hours < 24) {
            return hours + " hora" + (hours != 1 ? "s" : "") + " atrás";
        } else {
            return days + " dia" + (days != 1 ? "s" : "") + " atrás";
        }
    }
}
