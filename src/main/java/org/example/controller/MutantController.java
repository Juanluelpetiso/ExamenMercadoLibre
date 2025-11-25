package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Tag(name = "Mutant Detector", description = "API para detección de mutantes")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    public MutantController(MutantService mutantService, StatsService statsService) {
        this.mutantService = mutantService;
        this.statsService = statsService;
    }

    @PostMapping("/mutant")
    @Operation(summary = "Verificar si un ADN es mutante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es mutante"),
            @ApiResponse(responseCode = "403", description = "No es mutante"),
            @ApiResponse(responseCode = "400", description = "ADN inválido")
    })
    public ResponseEntity<Void> detectarMutante(@Valid @RequestBody DnaRequest peticionAdn) {
        // Llamamos al servicio para analizar el ADN
        boolean esMutante = mutantService.analizarAdn(peticionAdn.getAdn());

        if (esMutante) {
            // Si es mutante, devolvemos 200 OK
            return ResponseEntity.ok().build();
        } else {
            // Si es humano, devolvemos 403 Forbidden (según requerimiento)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas de verificaciones")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente")
    public ResponseEntity<StatsResponse> obtenerEstadisticas() {
        return ResponseEntity.ok(statsService.obtenerEstadisticas());
    }
}
