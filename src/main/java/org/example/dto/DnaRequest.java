package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.validator.ValidDnaSequence;

@Data
@Schema(description = "Request para verificar ADN")
public class DnaRequest {
    @NotNull(message = "La secuencia de ADN no puede ser nula")
    @NotEmpty(message = "La secuencia de ADN no puede estar vacía")
    @ValidDnaSequence
    @JsonProperty("dna")
    @Schema(description = "Secuencia de ADN representada como matriz NxN", example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]", requiredMode = Schema.RequiredMode.REQUIRED)
    private String[] adn;
}
