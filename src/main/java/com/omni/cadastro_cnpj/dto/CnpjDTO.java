package com.omni.cadastro_cnpj.dto;

import java.util.List;

import com.omni.cadastro_cnpj.validation.DocumentoValido;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CnpjDTO {

    @NotBlank(message = "O campo cnpj é obrigatório")
    @DocumentoValido(tipo = DocumentoValido.TipoDocumento.CNPJ, message = "CNPJ inválido")
    private String cnpj;
    
    @NotBlank(message = "O campo razaoSocial é obrigatório")
    private String razaoSocial;

    @NotBlank(message = "O campo nomeFantasia é obrigatório")
    private String nomeFantasia;
    
    private List<SocioDTO> socios;
}
