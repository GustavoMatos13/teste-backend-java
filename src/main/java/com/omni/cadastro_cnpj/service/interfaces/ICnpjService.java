package com.omni.cadastro_cnpj.service.interfaces;

import java.util.List;

import com.omni.cadastro_cnpj.dto.CnpjDTO;

public interface ICnpjService {
    CnpjDTO salvar(CnpjDTO cnpj);
    List<CnpjDTO> listarTodos();
    CnpjDTO buscarPorId(Long id);
    CnpjDTO atualizar(Long id, CnpjDTO cnpjAtualizado);
    void deletar(Long id);
}
