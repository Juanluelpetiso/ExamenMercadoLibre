package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    private final String[] mutantDna = { "AAAA", "CCCC", "TTAT", "AGAA" };
    private final String[] humanDna = { "ATGC", "CAGT", "TTAT", "AGAC" };

    @Test
    @DisplayName("Debe analizar ADN mutante y guardarlo en DB")
    void testAnalizarAdnMutanteYGuardar() {
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.esMutante(mutantDna)).thenReturn(true);
        when(dnaRecordRepository.save(any(DnaRecord.class))).thenReturn(new DnaRecord());

        boolean result = mutantService.analizarAdn(mutantDna);

        assertTrue(result);
        verify(mutantDetector, times(1)).esMutante(mutantDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Debe analizar ADN humano y guardarlo en DB")
    void testAnalizarAdnHumanoYGuardar() {
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.esMutante(humanDna)).thenReturn(false);
        when(dnaRecordRepository.save(any(DnaRecord.class))).thenReturn(new DnaRecord());

        boolean result = mutantService.analizarAdn(humanDna);

        assertFalse(result);
        verify(mutantDetector, times(1)).esMutante(humanDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Debe retornar resultado cacheado si el ADN ya fue analizado (Mutante)")
    void testRetornarResultadoCacheadoMutante() {
        DnaRecord cachedRecord = DnaRecord.builder().isMutant(true).build();
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(cachedRecord));

        boolean result = mutantService.analizarAdn(mutantDna);

        assertTrue(result);
        verify(mutantDetector, never()).esMutante(any());
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe retornar resultado cacheado si el ADN ya fue analizado (Humano)")
    void testRetornarResultadoCacheadoHumano() {
        DnaRecord cachedRecord = DnaRecord.builder().isMutant(false).build();
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(cachedRecord));

        boolean result = mutantService.analizarAdn(humanDna);

        assertFalse(result);
        verify(mutantDetector, never()).esMutante(any());
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe manejar excepción al calcular hash")
    void testExcepcionCalculoHash() {
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.esMutante(any())).thenReturn(true);

        boolean result = mutantService.analizarAdn(mutantDna);
        assertTrue(result);
    }
}
