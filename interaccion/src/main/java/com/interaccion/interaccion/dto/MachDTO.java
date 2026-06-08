import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchDTO {
    private Integer perfilAId;
    private Integer perfilBId;
    private LocalDateTime matchedAt;

}
