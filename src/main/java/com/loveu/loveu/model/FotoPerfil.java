package com.loveu.loveu.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foto_perfil")
@Builder
public class FotoPerfil {
    /*
     - FotoPerfil representa una imagen asociada a un perfil
     - Un perfil puede tener varias fotos, pero solo una puede ser la foto principal (principal = true)
     - Princiupal indica cual imagen se mostrara como la imagen "Main".
     - El orden define la posicion en el cual se mostrará las fotos del perfil.
    */ 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="perfil_id", referencedColumnName="id", nullable=false)
    private Perfil perfil;

    @NotBlank(message="La URL de la foto no puede estar vacia!")
    @Column(name="url_foto", length=255, nullable=false)
    @Size(max=255, message="La URL de la foto no puede superar los 255 caracteres!")
    private String urlFoto;

    @Column(nullable = false)
    private boolean principal = false;

    @NotNull(message="El orden de la foto no puede ser nulo!")
    @Column(name="orden", nullable=false)
    @Min(value=1, message="El orden de la foto debe ser al menos 1!")
    @Max(value=6, message="El orden de la foto no puede ser mayor a 6!")
    private Integer orden;

    @Column(name="fecha_subida",nullable = false)
    private LocalDateTime fechaSubida;

    @Column(nullable = false)
    private boolean activo = true;
    // Antes de guardar la foto, se asigna automaticamente la fecha de subida si viene vacia.
    // Si la fecha subida no ha sido seteada, se le asigna la fecha actual al momento de crear la entidad.
    @PrePersist 
    public void prePersist(){
        if(this.fechaSubida == null){
            this.fechaSubida = LocalDateTime.now();
        }
    }
}
