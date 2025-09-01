package com.omni.cadastro_cnpj.dto;

import com.omni.cadastro_cnpj.enuns.TipoSocio;
import com.omni.cadastro_cnpj.validation.DocumentoValido;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocioDTO {
	
    @NotBlank(message = "O campo tipo é obrigatório")
    private TipoSocio tipo;

    @NotBlank(message = "O campo documento é obrigatório")
    @DocumentoValido(
            tipo = DocumentoValido.TipoDocumento.AMBOS, 
            message = "Documento inválido: informe um CPF ou CNPJ válido"
        )
    private String documento;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "O campo porcentagemParticipacao é obrigatório")
    private Double porcentagemParticipacao;
}
