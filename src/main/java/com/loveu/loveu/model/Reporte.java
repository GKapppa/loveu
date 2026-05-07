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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reportes")
public class Reporte {
    // Reporte representa una denuncia que un perfil realizo hacía otro perfil.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // perfilReportante es el perfil que crea el reporte; perfil Reportado es el denunciado.
    @ManyToOne
    @JoinColumn(name="perfil_reportante_id", referencedColumnName="id", nullable=false)
    private Perfil perfilReportante;
    // DENUNCIADO!
    @ManyToOne
    @JoinColumn(name="perfil_reportado_id", referencedColumnName="id", nullable=false)
    private Perfil perfilReportado;

    @NotBlank(message="Debes tener una razón para reportar. No puede estar en blanco!")
    @Column(name="razon_reporte", length=500, nullable=false)
    @Size(max=500, message="La razón del reporte no puede superar el limite de 500 caracteres!")
    private String razonReporte;
    // Aquí hicimos un enum llamado:  EstadoReporte para así solo tener 3 estados posibles para el reporte: EN_REVISION, RESUELTO, RECHAZADO. Esto nos ayuda a evitar errores de estados invalidos.
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private EstadoReporte estadoReporte;

    @Column(name="fecha_reporte", nullable=false)
    private LocalDateTime fechaReporte;
    // Antes de guardar el reporte se asigna la fecha actual y el estado revision por defecto en caso de que no venga seteado.
    @PrePersist
    public void prePersist(){
        this.fechaReporte = LocalDateTime.now();
        if(this.estadoReporte == null){
            this.estadoReporte = EstadoReporte.EN_REVISION;
        }
    }
    
    @Column(nullable = false)
    private Boolean activo = true;
}
