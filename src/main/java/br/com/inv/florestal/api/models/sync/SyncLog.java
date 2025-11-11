package br.com.inv.florestal.api.models.sync;

import br.com.inv.florestal.api.models.collection.Plot;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sync_log")
@EntityListeners(AuditingEntityListener.class)
public class SyncLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_plot_id")
    private Plot fromPlot;

    @ManyToOne
    @JoinColumn(name = "to_plot_id")
    private Plot toPlot;

    @ManyToOne
    @JoinColumn(name = "synced_by")
    private User syncedBy;

    @Column(name = "synced_at")
    private LocalDateTime syncedAt;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;
}
