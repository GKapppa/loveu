package com.interaccion.interaccion.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "swipes")
public class Swipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer perfilOrigenId;

    @NotNull
    private Integer perfilDestinoId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DecisionSwipe decision;

    private LocalDateTime fecha;

    @Builder.Default
    private Boolean activo = true;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }
}
