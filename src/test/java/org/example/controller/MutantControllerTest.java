package org.example.controller;

import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Test
    @DisplayName("POST /mutant - Debe retornar 200 OK si es mutante")
    void testEndpointMutante_RetornaOk() throws Exception {
        when(mutantService.analizarAdn(any())).thenReturn(true);

        String dnaJson = """
                {
                    "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
                }
                """;

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dnaJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant - Debe retornar 403 Forbidden si es humano")
    void testEndpointHumano_RetornaForbidden() throws Exception {
        when(mutantService.analizarAdn(any())).thenReturn(false);

        String dnaJson = """
                {
                    "dna": ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]
                }
                """;

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dnaJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mutant - Debe retornar 400 Bad Request si el ADN es inválido (caracteres)")
    void testAdnInvalido_RetornaBadRequest() throws Exception {
        String dnaJson = """
                {
                    "dna": ["ATGX","CAGT"]
                }
                """;

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dnaJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant - Debe retornar 400 Bad Request si el ADN es nulo")
    void testAdnNulo_RetornaBadRequest() throws Exception {
        String dnaJson = """
                {
                    "dna": null
                }
                """;

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dnaJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant - Debe retornar 400 Bad Request si la matriz no es cuadrada")
    void testAdnNoCuadrado_RetornaBadRequest() throws Exception {
        String dnaJson = """
                {
                    "dna": ["AAA", "BBB"]
                }
                """;

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dnaJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /stats - Debe retornar estadísticas")
    void testEndpointStats_RetornaOk() throws Exception {
        StatsResponse stats = new StatsResponse(40, 100, 0.4);
        when(statsService.obtenerEstadisticas()).thenReturn(stats);

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }
}
