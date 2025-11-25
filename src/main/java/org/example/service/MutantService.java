package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    public MutantService(MutantDetector mutantDetector, DnaRecordRepository dnaRecordRepository) {
        this.mutantDetector = mutantDetector;
        this.dnaRecordRepository = dnaRecordRepository;
    }

    public boolean analizarAdn(String[] adn) {
        // 1. Generamos un hash único para este ADN (SHA-256)
        // Esto nos sirve para no analizar dos veces el mismo ADN y ahorrar recursos.
        String hashAdn = generarHash(adn);

        // 2. Buscamos en la base de datos si ya existe
        Optional<DnaRecord> registroExistente = dnaRecordRepository.findByDnaHash(hashAdn);

        if (registroExistente.isPresent()) {
            // Si ya existe, devolvemos el resultado guardado sin volver a procesar
            return registroExistente.get().isMutant();
        }

        // 3. Si es nuevo, usamos el detector para saber si es mutante
        boolean esMutante = mutantDetector.esMutante(adn);

        // 4. Guardamos el resultado en la base de datos para el futuro
        DnaRecord registro = DnaRecord.builder()
                .dnaHash(hashAdn)
                .dnaSequence(String.join(",", adn))
                .isMutant(esMutante)
                .build();

        dnaRecordRepository.save(registro);

        return esMutante;
    }

    private String generarHash(String[] adn) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            String secuenciaCompleta = String.join("", adn);
            byte[] hash = digest.digest(secuenciaCompleta.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular hash SHA-256", e);
        }
    }
}
