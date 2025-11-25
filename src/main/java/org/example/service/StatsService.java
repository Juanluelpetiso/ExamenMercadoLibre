package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    public StatsService(DnaRecordRepository dnaRecordRepository) {
        this.dnaRecordRepository = dnaRecordRepository;
    }

    public StatsResponse obtenerEstadisticas() {
        long conteoAdnMutante = dnaRecordRepository.countByIsMutant(true);
        long conteoAdnHumano = dnaRecordRepository.countByIsMutant(false);
        double proporcion = conteoAdnHumano == 0 ? 0 : (double) conteoAdnMutante / conteoAdnHumano;

        return new StatsResponse(conteoAdnMutante, conteoAdnHumano, proporcion);
    }
}
