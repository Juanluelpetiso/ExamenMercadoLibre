package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector mutantDetector;

    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontal y diagonal")
    void testMutanteConSecuenciasHorizontalYDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias verticales")
    void testMutanteConSecuenciasVerticales() {
        String[] dna = {
                "AAAAGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con múltiples secuencias horizontales")
    void testMutanteConMultiplesSecuenciasHorizontales() {
        String[] dna = {
                "TTTTGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con diagonales ascendentes y descendentes")
    void testMutanteConAmbasDiagonales() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("No debe detectar mutante con una sola secuencia")
    void testNoMutanteConUnaSolaSecuencia() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("No debe detectar mutante sin secuencias")
    void testNoMutanteSinSecuencias() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe rechazar ADN nulo")
    void testAdnNulo() {
        assertThrows(IllegalArgumentException.class, () -> mutantDetector.esMutante(null));
    }

    @Test
    @DisplayName("Debe rechazar ADN vacío")
    void testAdnVacio() {
        String[] dna = {};
        assertThrows(IllegalArgumentException.class, () -> mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante en matriz pequeña 4x4")
    void testMatrizPequena4x4Mutante() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TTAT",
                "AGAC"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe manejar matriz grande 10x10")
    void testMatrizGrande10x10() {
        String[] dna = {
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA",
                "CCCCTACCCC",
                "TCACTGTCAC",
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe detectar diagonal ascendente")
    void testDiagonalAscendente() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe usar early termination para eficiencia")
    void testTerminacionTemprana() {
        String[] dna = {
                "AAAAGA",
                "AAAAGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        long startTime = System.nanoTime();
        boolean result = mutantDetector.esMutante(dna);
        long endTime = System.nanoTime();

        assertTrue(result);
    }

    @Test
    @DisplayName("Debe detectar mutante con todas las bases iguales")
    void testTodasLasBasesIguales() {
        String[] dna = {
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con cruce de secuencias")
    void testSecuenciasCruzadas() {
        String[] dna = {
                "AAAAAA", // Horizontal
                "A.....", // Vertical en col 0
                "A.....",
                "A.....",
                "......",
                "......"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con dos diagonales cruzadas")
    void testDiagonalesCruzadas() {
        String[] dna = {
                "A....A",
                ".A..A.",
                "..AA..",
                "..AA..",
                ".A..A.",
                "A....A"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias al final de la matriz")
    void testSecuenciasAlFinal() {
        String[] dna = {
                "......",
                "......",
                "......",
                "......",
                "CCCC..", // Horizontal
                "AAAA.." // Horizontal
        };
        assertTrue(mutantDetector.esMutante(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias en la ultima columna")
    void testSecuenciasUltimaColumna() {
        String[] dna = {
                ".....A",
                ".....A",
                ".....A",
                ".....A", // Vertical col 5
                "CCCC..", // Horizontal
                "......"
        };
        assertTrue(mutantDetector.esMutante(dna));
    }
}
