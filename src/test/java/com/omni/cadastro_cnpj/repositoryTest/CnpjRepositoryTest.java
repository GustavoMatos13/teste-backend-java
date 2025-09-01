package com.omni.cadastro_cnpj.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.omni.cadastro_cnpj.entity.Cnpj;
import com.omni.cadastro_cnpj.repository.CnpjRepository;

@DataJpaTest
class CnpjRepositoryTest {

    @Autowired
    private CnpjRepository cnpjRepository;

    @Test
    void deveSalvarCnpj() {
        Cnpj cnpj = new Cnpj();
        cnpj.setCnpj("45723174000110");
        cnpj.setRazaoSocial("Empresa Teste");
        cnpj.setNomeFantasia("Fantasia");

        Cnpj salvo = cnpjRepository.save(cnpj);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getCnpj()).isEqualTo("45723174000110");
    }

    @Test
    void deveBuscarCnpjPorId() {
        Cnpj cnpj = new Cnpj();
        cnpj.setCnpj("45723174000110");
        cnpj.setRazaoSocial("Empresa Teste");
        cnpj.setNomeFantasia("Fantasia");

        Cnpj salvo = cnpjRepository.save(cnpj);

        Optional<Cnpj> encontrado = cnpjRepository.findById(salvo.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getCnpj()).isEqualTo("45723174000110");
    }

    @Test
    void deveListarTodos() {
        Cnpj cnpj1 = new Cnpj();
        cnpj1.setCnpj("45723174000110");
        cnpj1.setRazaoSocial("Empresa 1");
        cnpj1.setNomeFantasia("Fantasia 1");

        Cnpj cnpj2 = new Cnpj();
        cnpj2.setCnpj("12345678000199");
        cnpj2.setRazaoSocial("Empresa 2");
        cnpj2.setNomeFantasia("Fantasia 2");

        cnpjRepository.saveAll(List.of(cnpj1, cnpj2));

        List<Cnpj> todos = cnpjRepository.findAll();

        assertThat(todos).hasSize(2);
    }

    @Test
    void deveDeletarCnpj() {
        Cnpj cnpj = new Cnpj();
        cnpj.setCnpj("45723174000110");
        cnpj.setRazaoSocial("Empresa Teste");
        cnpj.setNomeFantasia("Fantasia");

        Cnpj salvo = cnpjRepository.save(cnpj);

        cnpjRepository.deleteById(salvo.getId());

        Optional<Cnpj> encontrado = cnpjRepository.findById(salvo.getId());
        assertThat(encontrado).isEmpty();
    }
}
