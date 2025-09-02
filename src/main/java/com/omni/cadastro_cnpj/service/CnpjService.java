package com.omni.cadastro_cnpj.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.omni.cadastro_cnpj.dto.CnpjDTO;
import com.omni.cadastro_cnpj.entity.Cnpj;
import com.omni.cadastro_cnpj.entity.Socio;
import com.omni.cadastro_cnpj.exception.CnpjAlreadyExistsException;
import com.omni.cadastro_cnpj.exception.CnpjNotFoundException;
import com.omni.cadastro_cnpj.mapper.CnpjSocioMapper;
import com.omni.cadastro_cnpj.repository.CnpjRepository;
import com.omni.cadastro_cnpj.service.interfaces.ICnpjService;

@Service
public class CnpjService implements ICnpjService {

    private final CnpjRepository cnpjRepository;
    private CnpjSocioMapper cnpjSocioMapper;

    public CnpjService(CnpjRepository cnpjRepository, CnpjSocioMapper cnpjSocioMapper) {
        this.cnpjRepository = cnpjRepository;
        this.cnpjSocioMapper = cnpjSocioMapper;
    }

    // Salvar CNPJ a partir do DTO
    @Override
    public CnpjDTO salvar(CnpjDTO cnpjDTO) {
        // Verifica se já existe outro CNPJ igual
        if (cnpjRepository.existsByCnpj(cnpjDTO.getCnpj())) {
            throw new CnpjAlreadyExistsException(cnpjDTO.getCnpj());
        }

        // Converte DTO para entidade
        Cnpj cnpj = cnpjSocioMapper.converterDTOParaEntidade(cnpjDTO);
        
        vincularSociosCnpjExistente(cnpj);

        // Salva no banco
        Cnpj salvo = cnpjRepository.save(cnpj);

        // Converte de volta para DTO e retorna
        return cnpjSocioMapper.converterEntidadeParaDTO(salvo);
    }

    // Listar todos
    @Override
    public List<CnpjDTO> listarTodos() {
        return cnpjRepository.findAll().stream()
                .map(cnpjSocioMapper::converterEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    @Override
    public CnpjDTO buscarPorId(Long id) {
        Cnpj cnpj = cnpjRepository.findById(id)
                .orElseThrow(() -> new CnpjNotFoundException(id));
        return cnpjSocioMapper.converterEntidadeParaDTO(cnpj);
    }

    // Atualizar
    @Override
    public CnpjDTO atualizar(Long id, CnpjDTO cnpjDTO) {
        Cnpj cnpjExistente = cnpjRepository.findById(id)
                .orElseThrow(() -> new CnpjNotFoundException(id));

        // Atualiza campos
        cnpjExistente.setCnpj(cnpjDTO.getCnpj());
        cnpjExistente.setRazaoSocial(cnpjDTO.getRazaoSocial());
        cnpjExistente.setNomeFantasia(cnpjDTO.getNomeFantasia());

        // Atualiza sócios
        cnpjExistente.setSocios(
            cnpjDTO.getSocios().stream()
                    .map(cnpjSocioMapper::converterDTOParaSocio)
                    .collect(Collectors.toList())
        );
        
        vincularSociosCnpjExistente(cnpjExistente);
        
        Cnpj atualizado = cnpjRepository.save(cnpjExistente);
        return cnpjSocioMapper.converterEntidadeParaDTO(atualizado);
    }

    // Deletar
    @Override
    public void deletar(Long id) {
        Cnpj cnpjExistente = cnpjRepository.findById(id)
                .orElseThrow(() -> new CnpjNotFoundException(id));
        cnpjRepository.delete(cnpjExistente);
    }
    
    // Vincula cada sócio ao CNPJ
    private void vincularSociosCnpjExistente(Cnpj cnpj) {
        if (cnpj.getSocios() != null) {
            for (Socio socio : cnpj.getSocios()) {
                // Se o sócio é pessoa física, ou juridica
                if(cnpjRepository.findByCnpj(socio.getDocumento()) != null) socio.setCnpjSocio(cnpj);
                else socio.setCnpjSocio(null);
            }
        }
    }

}
