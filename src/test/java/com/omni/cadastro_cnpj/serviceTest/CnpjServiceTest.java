package com.omni.cadastro_cnpj.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.omni.cadastro_cnpj.dto.CnpjDTO;
import com.omni.cadastro_cnpj.dto.SocioDTO;
import com.omni.cadastro_cnpj.entity.Cnpj;
import com.omni.cadastro_cnpj.enuns.TipoSocio;
import com.omni.cadastro_cnpj.repository.CnpjRepository;
import com.omni.cadastro_cnpj.service.CnpjService;

@SpringBootTest
class CnpjServiceTest {	

    @Mock
    private CnpjRepository cnpjRepository;

    @InjectMocks
    private CnpjService cnpjService;

    @Test
    void deveSalvarCnpj() {
    	// DTO de sócios
        SocioDTO socio1 = new SocioDTO(TipoSocio.PF, "12345678909", "João", 50.0);
        SocioDTO socio2 = new SocioDTO(TipoSocio.PJ, "45723174000110", "Empresa ABC", 50.0);

        // DTO de CNPJ
        CnpjDTO cnpjDTO = new CnpjDTO();
        cnpjDTO.setCnpj("45723174000110");
        cnpjDTO.setRazaoSocial("Empresa Teste");
        cnpjDTO.setNomeFantasia("Fantasia");
        cnpjDTO.setSocios(List.of(socio1, socio2));

        // Entidade que o repository retorna
        Cnpj cnpjEntity = new Cnpj();
        cnpjEntity.setId(1L);
        cnpjEntity.setCnpj("45723174000110");
        cnpjEntity.setRazaoSocial("Empresa Teste");
        cnpjEntity.setNomeFantasia("Fantasia");
        cnpjEntity.setSocios(List.of()); 

        when(cnpjRepository.save(any())).thenReturn(cnpjEntity);

        // Chamada do service
        CnpjDTO salvo = cnpjService.salvar(cnpjDTO);

        // Validações
        assertNotNull(salvo);
        assertEquals("45723174000110", salvo.getCnpj());
        assertEquals("Empresa Teste", salvo.getRazaoSocial());
        verify(cnpjRepository, times(1)).save(any(Cnpj.class));
    }
    
    @Test
    void deveBuscarCnpjPorIdExistente() {
        Cnpj cnpjEntity = new Cnpj();
        cnpjEntity.setId(1L);
        cnpjEntity.setCnpj("45723174000110");
        cnpjEntity.setRazaoSocial("Empresa Teste");
        cnpjEntity.setNomeFantasia("Fantasia");

        when(cnpjRepository.findById(1L)).thenReturn(Optional.of(cnpjEntity));

        CnpjDTO resultado = cnpjService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("45723174000110", resultado.getCnpj());
        verify(cnpjRepository, times(1)).findById(1L);
    }

    @Test
    void deveFalharBuscarCnpjPorIdInexistente() {
        when(cnpjRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cnpjService.buscarPorId(999L));
        verify(cnpjRepository, times(1)).findById(999L);
    }

    // ===================== ATUALIZAR =====================
    @Test
    void deveAtualizarCnpjExistente() {
        Cnpj cnpjEntity = new Cnpj();
        cnpjEntity.setId(1L);
        cnpjEntity.setCnpj("45723174000110");
        cnpjEntity.setRazaoSocial("Empresa Teste");
        cnpjEntity.setNomeFantasia("Fantasia");

        CnpjDTO cnpjDTOAtualizado = new CnpjDTO("45723174000110","Empresa Atualizada","Fantasia Atualizada", List.of());

        when(cnpjRepository.findById(1L)).thenReturn(Optional.of(cnpjEntity));
        when(cnpjRepository.save(any())).thenReturn(cnpjEntity);

        CnpjDTO atualizado = cnpjService.atualizar(1L, cnpjDTOAtualizado);

        assertNotNull(atualizado);
        verify(cnpjRepository, times(1)).save(any(Cnpj.class));
    }

    @Test
    void deveFalharAtualizarCnpjInexistente() {
        CnpjDTO cnpjDTOAtualizado = new CnpjDTO("45723174000110","Empresa Atualizada","Fantasia Atualizada", List.of());
        when(cnpjRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cnpjService.atualizar(999L, cnpjDTOAtualizado));
        verify(cnpjRepository, times(1)).findById(999L);
    }

    // ===================== DELETAR =====================
    @Test
    void deveDeletarCnpjExistente() {
        Cnpj cnpjEntity = new Cnpj();
        cnpjEntity.setId(1L);

        when(cnpjRepository.findById(1L)).thenReturn(Optional.of(cnpjEntity));

        cnpjService.deletar(1L);

        verify(cnpjRepository, times(1)).delete(cnpjEntity);
    }

    @Test
    void deveFalharDeletarCnpjInexistente() {
        when(cnpjRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cnpjService.deletar(999L));
        verify(cnpjRepository, times(1)).findById(999L);
    }
}
