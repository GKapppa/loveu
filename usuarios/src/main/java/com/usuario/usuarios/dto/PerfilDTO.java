import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerfilDTO {
    private String nombreVisible;
    private String biografia;
    private Integer alturaCm;
    private Integer usuarioId;
    private String ocupacion;
}
