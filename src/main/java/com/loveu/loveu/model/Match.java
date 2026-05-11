package com.loveu.loveu.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matches",
    uniqueConstraints = @UniqueConstraint(columnNames = {"perfil_a_id", "perfil_b_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {
    // match representa la conexion entre dos perfiles cuando ambos dan LIKE (el enum se encarga de esas opciones).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // Creamos un campo llamado PerfilA para representar al primer perfil que dio like.
    @ManyToOne
    @JoinColumn(name="perfil_a_id", referencedColumnName="id", nullable=false)
    private Perfil perfilA;
    // Creamos un campo llamado PerfilB para representar al segundo perfil que dio like.
    @ManyToOne
    @JoinColumn(name="perfil_b_id", referencedColumnName="id", nullable=false)
    private Perfil perfilB;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status;

    @Column(name = "matched_at", nullable = false)
    private LocalDateTime matchedAt;
    // Al crear un match, se asigna la fecha actual y el estado active por defecto.
    @PrePersist
    public void prePersist() {
        this.matchedAt = LocalDateTime.now();
        if (this.status == null) this.status = MatchStatus.ACTIVE;
    }
}
