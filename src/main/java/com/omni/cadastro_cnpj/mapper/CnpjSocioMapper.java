package com.omni.cadastro_cnpj.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.omni.cadastro_cnpj.dto.CnpjDTO;
import com.omni.cadastro_cnpj.dto.SocioDTO;
import com.omni.cadastro_cnpj.entity.Cnpj;
import com.omni.cadastro_cnpj.entity.Socio;


@Component
public class CnpjSocioMapper {

    public Cnpj converterDTOParaEntidade(CnpjDTO dto) {
        Cnpj cnpj = new Cnpj();
        cnpj.setCnpj(dto.getCnpj());
        cnpj.setRazaoSocial(dto.getRazaoSocial());
        cnpj.setNomeFantasia(dto.getNomeFantasia());

        if (dto.getSocios() != null) {
            List<Socio> socios = dto.getSocios().stream()
                    .map(this::converterDTOParaSocio)
                    .collect(Collectors.toList());
            cnpj.setSocios(socios);
        }

        return cnpj;
    }

    // Converte SocioDTO para Socio
    public Socio converterDTOParaSocio(SocioDTO dto) {
        Socio socio = new Socio();
        socio.setTipo(dto.getTipo());
        socio.setDocumento(dto.getDocumento());
        socio.setNome(dto.getNome());
        socio.setPorcentagemParticipacao(dto.getPorcentagemParticipacao());
        return socio;
    }

    // Converte entidade para DTO
    public CnpjDTO converterEntidadeParaDTO(Cnpj cnpj) {
        CnpjDTO dto = new CnpjDTO();
        dto.setId(cnpj.getId());
        dto.setCnpj(cnpj.getCnpj());
        dto.setRazaoSocial(cnpj.getRazaoSocial());
        dto.setNomeFantasia(cnpj.getNomeFantasia());

        if (cnpj.getSocios() != null) {
            List<SocioDTO> sociosDTO = cnpj.getSocios().stream()
                    .map(s -> new SocioDTO(
                            s.getTipo(),
                            s.getDocumento(),
                            s.getNome(),
                            s.getPorcentagemParticipacao()
                    ))
                    .collect(Collectors.toList());
            dto.setSocios(sociosDTO);
        }

        return dto;
    }


}