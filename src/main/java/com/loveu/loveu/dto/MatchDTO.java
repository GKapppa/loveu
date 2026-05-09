package com.loveu.loveu.dto;

import java.time.LocalDateTime;

import com.loveu.loveu.model.MatchStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchDTO {
    private Integer id;
    private Integer perfilAId;
    private Integer perfilBId;
    private MatchStatus status;
    private LocalDateTime matchedAt;

}
