package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Pattern CARACTERES_VALIDOS = Pattern.compile("^[ATCG]+$");

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0) {
            return false;
        }

        int n = dna.length;
        for (String row : dna) {
            if (row == null) {
                return false;
            }
            if (row.length() != n) {
                return false; // Debe ser NxN
            }
            if (!CARACTERES_VALIDOS.matcher(row).matches()) {
                return false; // Solo A, T, C, G
            }
        }
        return true;
    }
}
