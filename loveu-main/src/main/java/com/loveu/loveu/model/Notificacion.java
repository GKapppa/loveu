package com.loveu.loveu.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion{
    // Notificacion almacena avisos generados por eventos importantes del sistema.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="perfil_destinatario_id", referencedColumnName="id", nullable=false)
    private Perfil perfilDestinatario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificacionType type;  // MATCH, MESSAGE, LIKE

    @Column(nullable = false)
    private String message;
    
    @Builder.Default
    @Column(name = "is_read", nullable = false)
    private boolean read = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    // Antes de guardar la notificacion, se asigna automaticanete la fecha de creacion.
    @PrePersist
    public void prePersist() { 
        this.createdAt = LocalDateTime.now(); }

}

