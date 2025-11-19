package com.BancoFinanceiro.BancoMalvader.util;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/**
 * Utilitário para validação de senhas
 * Verifica força e requisitos de segurança
 */
@Component
public class PasswordValidator {

    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]");

    public boolean isValid(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres");
        }

        if (!UPPER_CASE_PATTERN.matcher(password).find()) {
            throw new IllegalArgumentException("Senha deve conter pelo menos uma letra maiúscula");
        }

        if (!LOWER_CASE_PATTERN.matcher(password).find()) {
            throw new IllegalArgumentException("Senha deve conter pelo menos uma letra minúscula");
        }

        if (!DIGIT_PATTERN.matcher(password).find()) {
            throw new IllegalArgumentException("Senha deve conter pelo menos um dígito");
        }

        if (!SPECIAL_CHAR_PATTERN.matcher(password).find()) {
            throw new IllegalArgumentException("Senha deve conter pelo menos um caractere especial");
        }

        return true;
    }

    public int calculateStrength(String password) {
        if (password == null) return 0;

        int score = 0;

        if (password.length() >= 8) score += 20;
        if (password.length() >= 12) score += 10;
        if (password.length() >= 16) score += 10;

        if (UPPER_CASE_PATTERN.matcher(password).find()) score += 15;
        if (LOWER_CASE_PATTERN.matcher(password).find()) score += 15;
        if (DIGIT_PATTERN.matcher(password).find()) score += 15;
        if (SPECIAL_CHAR_PATTERN.matcher(password).find()) score += 15;

        if (!hasObviousSequences(password)) score += 10;

        return Math.min(score, 100);
    }

    private boolean hasObviousSequences(String password) {
        String lowerPassword = password.toLowerCase();

        String[] obviousSequences = {
                "123", "234", "345", "456", "567", "678", "789", "890",
                "qwe", "wer", "ert", "rty", "tyu", "yui", "uio", "iop",
                "asd", "sdf", "dfg", "fgh", "ghj", "hjk", "jkl",
                "zxc", "xcv", "cvb", "vbn", "bnm"
        };

        for (String sequence : obviousSequences) {
            if (lowerPassword.contains(sequence)) {
                return true;
            }
        }

        return false;
    }

    public String getStrengthMessage(String password) {
        int strength = calculateStrength(password);

        if (strength < 40) {
            return "Senha muito fraca";
        } else if (strength < 60) {
            return "Senha fraca";
        } else if (strength < 80) {
            return "Senha moderada";
        } else if (strength < 90) {
            return "Senha forte";
        } else {
            return "Senha muito forte";
        }
    }
}