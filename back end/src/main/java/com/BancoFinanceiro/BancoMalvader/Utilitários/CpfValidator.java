package com.BancoFinanceiro.BancoMalvader.util;

import java.util.InputMismatchException;
import org.springframework.stereotype.Component;

/**
 * Utilitário para validação de CPF
 * Verifica formato e dígitos verificadores
 */
@Component
public class CpfValidator {

    public boolean isValid(String cpf) {
        if (cpf == null) {
            return false;
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            return validarDigitosVerificadores(cpf);
        } catch (InputMismatchException e) {
            return false;
        }
    }

    private boolean validarDigitosVerificadores(String cpf) {
        int digito1 = calcularDigito(cpf.substring(0, 9));
        int digito2 = calcularDigito(cpf.substring(0, 9) + digito1);

        return cpf.equals(cpf.substring(0, 9) + digito1 + digito2);
    }

    private int calcularDigito(String str) {
        int soma = 0;
        int peso = str.length() + 1;

        for (int i = 0; i < str.length(); i++) {
            soma += Integer.parseInt(str.substring(i, i + 1)) * peso;
            peso--;
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    public String formatarCpf(String cpf) {
        if (!isValid(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }
}