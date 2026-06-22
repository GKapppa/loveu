package com.interaccion.interaccion.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El id del perfil A es obligatorio")
    private Integer perfilAId;

    @NotNull(message = "El id del perfil B es obligatorio")
    private Integer perfilBId;

    @NotBlank(message = "El estado del match no puede quedar vacío")
    @Size(min = 3, max = 50)
    private String status;

    @NotNull(message = "La fecha del match es obligatoria")
    private LocalDateTime matchedAt;

    @PrePersist
    public void prePersist() {
        this.matchedAt = LocalDateTime.now();
        if (this.status == null) this.status = MatchStatus.ACTIVE;
    }
}
