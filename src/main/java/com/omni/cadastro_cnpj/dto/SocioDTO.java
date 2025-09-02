package com.omni.cadastro_cnpj.dto;

import com.omni.cadastro_cnpj.enuns.TipoSocio;
import com.omni.cadastro_cnpj.validation.DocumentoValido;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocioDTO {
	
	@NotNull(message = "O campo tipo é obrigatório")
    private TipoSocio tipo;

    @NotBlank(message = "O campo documento é obrigatório")
    @DocumentoValido(
            tipo = DocumentoValido.TipoDocumento.AMBOS, 
            message = "Documento inválido: informe um CPF ou CNPJ válido"
        )
    private String documento;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;
    
    @NotNull(message = "A porcentagem de participação é obrigatória")
    @DecimalMin(value = "0.0", inclusive = false, message = "A porcentagem deve ser maior que 0")
    @DecimalMax(value = "100.0", message = "A porcentagem não pode ser maior que 100")
    private Double porcentagemParticipacao;
}
