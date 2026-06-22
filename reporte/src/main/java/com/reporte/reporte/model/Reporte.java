package com.reporte.reporte.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Debes tener una razon para reportar. No puede estar en blanco")
    @Size(max = 500, message = "La razon del reporte no puede esperar el limite de 500 caracteres!")
    private String razonReporte;

    @Enumerated(EnumType.STRING)
    private EstadoReporte estadoReporte;

    private LocalDateTime fechaReporte;

    @PrePersist
    public void prePersist(){
        this.fechaReporte = LocalDateTime.now();
        if(this.estadoReporte == null){
            this.estadoReporte = EstadoReporte.EN_REVISION;
        }
    }

    @NotNull(message = "El perfil reportante no puede estar vacio")
    private Integer perfilReportante;

    @NotNull(message = "El perfil reportado no puede estar vacio")
    private Integer perfilReportado;

    @Builder.Default
    private boolean activo = true;
}
