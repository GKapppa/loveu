import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "notificaciones")

public class Notificacion {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer perfilDestinatarioId;

    @NotBlank
    @Size(max = 20)
    private String type;

    @NotBlank
    @Size(max = 500)
    private String message;

    @Builder.Default
    private boolean leido = false;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}



