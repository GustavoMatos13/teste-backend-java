package com.omni.cadastro_cnpj.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = DocumentoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentoValido {

    String message() default "Documento inválido (CPF ou CNPJ)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    TipoDocumento tipo() default TipoDocumento.AMBOS;

    enum TipoDocumento {
        CPF,
        CNPJ,
        AMBOS
    }
}
