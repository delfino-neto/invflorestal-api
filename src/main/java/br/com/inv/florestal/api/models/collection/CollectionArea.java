package br.com.inv.florestal.api.models.collection;

import br.com.inv.florestal.api.models.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "collection_area")
@EntityListeners(AuditingEntityListener.class)
public class CollectionArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "geometry", columnDefinition = "polygon")
    private String geometry;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;

    @Column(name = "biome")
    private String biome;

    @Column(name = "climate_zone")
    private String climateZone;

    @Column(name = "soil_type")
    private String soilType;

    @Column(name = "conservation_status")
    private String conservationStatus;

    @Column(name = "vegetation_type")
    private String vegetationType;

    @Column(name = "altitude_m", precision = 8, scale = 2)
    private java.math.BigDecimal altitudeM;

    @Column(name = "protected_area")
    private Boolean protectedArea;

    @Column(name = "protected_area_name")
    private String protectedAreaName;

    @Column(name = "land_owner")
    private String landOwner;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
}
