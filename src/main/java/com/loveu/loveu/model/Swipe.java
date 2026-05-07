package com.loveu.loveu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import jakarta.persistence.PrePersist;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="swipes")
public class Swipe {
    // Swipe representa una accion realizada por un perfil origen sobre algún otro perfil.
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    // perfilOrigen realiza la accion; perfilDestine es el perfil que recibe esta acción de like.
    @ManyToOne
    @JoinColumn(name="perfil_origen_id", referencedColumnName="id", nullable=false)
    private Perfil perfilOrigen;
    // si dos perfiles de dan like mutuamente, el sistema genera un MATCH!.
    @ManyToOne
    @JoinColumn(name="perfil_destino_id", referencedColumnName="id", nullable=false)
    private Perfil  perfilDestino;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private DecisionSwipe decision;

    @Column(name="fecha", nullable=false)
    private LocalDateTime fecha;

    @Column(nullable=false)
    private boolean activo = true;

    /* Nota: Encontraomos este metodo en la documentacion de JPA
    Se ejecuta antes de insertar un nuevo Swipe en la base de datos. Si no se asgina fecha manualmente,
    se guarda fecha y hora actual
    */
    @PrePersist 
    public void prePersist() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
