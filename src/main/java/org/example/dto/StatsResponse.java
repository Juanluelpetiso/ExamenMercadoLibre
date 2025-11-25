package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estadísticas de verificaciones de ADN")
public class StatsResponse {
    @JsonProperty("count_mutant_dna")
    @Schema(description = "Cantidad de ADN mutante verificado")
    private long conteoAdnMutante;

    @JsonProperty("count_human_dna")
    @Schema(description = "Cantidad de ADN humano verificado")
    private long conteoAdnHumano;

    @JsonProperty("ratio")
    @Schema(description = "Proporción de mutantes respecto a humanos")
    private double proporcion;
}
