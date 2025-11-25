package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetector {

    // Longitud de la secuencia necesaria para considerar un gen mutante
    private static final int LONGITUD_SECUENCIA = 4;

    /**
     * Analiza si un humano es mutante basándose en su secuencia de ADN.
     * 
     * @param adn Array de Strings que representa la matriz de ADN (NxN)
     * @return true si es mutante (más de 1 secuencia encontrada), false si no.
     */
    public boolean esMutante(String[] adn) {
        if (adn == null || adn.length == 0) {
            throw new IllegalArgumentException("La secuencia de ADN no puede ser nula o vacía");
        }

        int n = adn.length;
        int contadorSecuencias = 0;

        // Optimización 1: Convertimos el array de Strings a una matriz de char[][]
        // Esto hace que el acceso a cada letra sea mucho más rápido (O(1)) que usar
        // charAt() repetidamente.
        char[][] matriz = new char[n][];
        for (int i = 0; i < n; i++) {
            matriz[i] = adn[i].toCharArray();
        }

        // Recorremos la matriz una sola vez buscando secuencias
        for (int fila = 0; fila < n; fila++) {
            for (int col = 0; col < n; col++) {

                // --- Verificación Horizontal ---
                // Solo revisamos si hay espacio suficiente a la derecha para 4 letras
                if (col <= n - LONGITUD_SECUENCIA) {
                    if (verificarHorizontal(matriz, fila, col)) {
                        contadorSecuencias++;
                        // Optimización 2: Early Termination (Terminación Temprana)
                        // Si ya encontramos más de una secuencia, no tiene sentido seguir buscando.
                        // Ya sabemos que es mutante.
                        if (contadorSecuencias > 1)
                            return true;
                    }
                }

                // --- Verificación Vertical ---
                // Solo revisamos si hay espacio suficiente hacia abajo
                if (fila <= n - LONGITUD_SECUENCIA) {
                    if (verificarVertical(matriz, fila, col)) {
                        contadorSecuencias++;
                        if (contadorSecuencias > 1)
                            return true;
                    }
                }

                // --- Verificación Diagonal (Descendente ↘) ---
                if (fila <= n - LONGITUD_SECUENCIA && col <= n - LONGITUD_SECUENCIA) {
                    if (verificarDiagonal(matriz, fila, col)) {
                        contadorSecuencias++;
                        if (contadorSecuencias > 1)
                            return true;
                    }
                }

                // --- Verificación Anti-Diagonal (Ascendente ↗) ---
                if (fila <= n - LONGITUD_SECUENCIA && col >= LONGITUD_SECUENCIA - 1) {
                    if (verificarAntiDiagonal(matriz, fila, col)) {
                        contadorSecuencias++;
                        if (contadorSecuencias > 1)
                            return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean verificarHorizontal(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return matriz[fila][col + 1] == base &&
                matriz[fila][col + 2] == base &&
                matriz[fila][col + 3] == base;
    }

    private boolean verificarVertical(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return matriz[fila + 1][col] == base &&
                matriz[fila + 2][col] == base &&
                matriz[fila + 3][col] == base;
    }

    private boolean verificarDiagonal(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return matriz[fila + 1][col + 1] == base &&
                matriz[fila + 2][col + 2] == base &&
                matriz[fila + 3][col + 3] == base;
    }

    private boolean verificarAntiDiagonal(char[][] matriz, int fila, int col) {
        char base = matriz[fila][col];
        return matriz[fila + 1][col - 1] == base &&
                matriz[fila + 2][col - 2] == base &&
                matriz[fila + 3][col - 3] == base;
    }
}
