package com.omni.cadastro_cnpj.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentoValidator implements ConstraintValidator<DocumentoValido, String> {

    private DocumentoValido.TipoDocumento tipo;

    @Override
    public void initialize(DocumentoValido constraintAnnotation) {
        this.tipo = constraintAnnotation.tipo();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        String documento = value.replaceAll("\\D", ""); // remove tudo que não for número

        switch (tipo) {
            case CPF:
                return isValidCPF(documento);
            case CNPJ:
                return isValidCNPJ(documento);
            case AMBOS:
                if (documento.length() == 11) {
                    return isValidCPF(documento);
                } else if (documento.length() == 14) {
                    return isValidCNPJ(documento);
                }
                return false;
            default:
                return false;
        }
    }

    private boolean isValidCPF(String cpf) {
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) soma += (cpf.charAt(i) - '0') * (10 - i);
            int dig1 = 11 - (soma % 11);
            if (dig1 >= 10) dig1 = 0;

            soma = 0;
            for (int i = 0; i < 10; i++) soma += (cpf.charAt(i) - '0') * (11 - i);
            int dig2 = 11 - (soma % 11);
            if (dig2 >= 10) dig2 = 0;

            return dig1 == (cpf.charAt(9) - '0') && dig2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidCNPJ(String cnpj) {
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma = 0;
            for (int i = 0; i < 12; i++) soma += (cnpj.charAt(i) - '0') * peso1[i];
            int dig1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            soma = 0;
            for (int i = 0; i < 13; i++) soma += (cnpj.charAt(i) - '0') * peso2[i];
            int dig2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
            
            return dig1 == (cnpj.charAt(12) - '0') && dig2 == (cnpj.charAt(13) - '0');
        } catch (Exception e) {
            return false;
        }
    }
}
