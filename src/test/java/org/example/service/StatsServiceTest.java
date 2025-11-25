package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("Debe calcular estadísticas con mutantes y humanos")
    void testCalcularEstadisticasConMutantesYHumanos() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.obtenerEstadisticas();

        assertEquals(40L, response.getConteoAdnMutante());
        assertEquals(100L, response.getConteoAdnHumano());
        assertEquals(0.4, response.getProporcion());
    }

    @Test
    @DisplayName("Debe manejar división por cero (0 humanos)")
    void testCalcularEstadisticasConCeroHumanos() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.obtenerEstadisticas();

        assertEquals(10L, response.getConteoAdnMutante());
        assertEquals(0L, response.getConteoAdnHumano());
        assertEquals(0.0, response.getProporcion());
    }

    @Test
    @DisplayName("Debe manejar 0 mutantes y 0 humanos")
    void testCalcularEstadisticasSinDatos() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.obtenerEstadisticas();

        assertEquals(0L, response.getConteoAdnMutante());
        assertEquals(0L, response.getConteoAdnHumano());
        assertEquals(0.0, response.getProporcion());
    }

    @Test
    @DisplayName("Debe calcular ratio correctamente con más mutantes que humanos")
    void testCalcularEstadisticasMasMutantes() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(100L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(50L);

        StatsResponse response = statsService.obtenerEstadisticas();

        assertEquals(100L, response.getConteoAdnMutante());
        assertEquals(50L, response.getConteoAdnHumano());
        assertEquals(2.0, response.getProporcion());
    }
}
