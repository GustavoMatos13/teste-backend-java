package com.omni.cadastro_cnpj.integrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CnpjIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private String gerarJsonCnpj(String cnpj, String razao, String fantasia, boolean incluirSocios) {
        String sociosJson = incluirSocios ? """
            ,"socios":[
                {"tipo":"PF","documento":"12345678909","nome":"João","porcentagemParticipacao":50.0},
                {"tipo":"PJ","documento":"45723174000110","nome":"Empresa ABC","porcentagemParticipacao":50.0}
            ]""" : "";
        return String.format("""
            {
              "cnpj":"%s",
              "razaoSocial":"%s",
              "nomeFantasia":"%s"%s
            }
            """, cnpj, razao, fantasia, sociosJson);
    }

    // 1️ POST - criar CNPJ válido com sócios
    @Test
    void deveCriarCnpjComSocios() throws Exception {
        String json = gerarJsonCnpj("45723174000110", "Empresa LTDA", "Fantasia", true);

        mockMvc.perform(post("/omni/criar_cnpj")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.cnpj").value("45723174000110"))
               .andExpect(jsonPath("$.socios.length()").value(2));
    }

    // 2️ POST - criar CNPJ inválido
    @Test
    void deveFalharCriarCnpjInvalido() throws Exception {
        String json = gerarJsonCnpj("11111111111111","Empresa LTDA","Fantasia", true);

        mockMvc.perform(post("/omni/criar_cnpj")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isBadRequest());
    }

    // 3️ POST - criar com sócio inválido
    @Test
    void deveFalharCriarCnpjComSocioInvalido() throws Exception {
        String json = """
            {
              "cnpj":"45723174000110",
              "razaoSocial":"Empresa LTDA",
              "nomeFantasia":"Fantasia",
              "socios":[
                {"tipo":"PF","documento":"12345678900","nome":"João","porcentagemParticipacao":50.0}
              ]
            }
            """;

        mockMvc.perform(post("/omni/criar_cnpj")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isBadRequest());
    }
    
    // 4 POST - criar com sócio inválido
    @Test
    void deveFalharCriarCnpjComSocioTipoInvalido() throws Exception {
        String json = """
            {
              "cnpj":"45723174000110",
              "razaoSocial":"Empresa LTDA",
              "nomeFantasia":"Fantasia",
              "socios":[
                {"tipo":"teste","documento":"12345678909","nome":"João","porcentagemParticipacao":50.0}
              ]
            }
            """;

        mockMvc.perform(post("/omni/criar_cnpj")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isBadRequest());
    }

    // 5️ GET por ID existente
    @Test
    void deveBuscarCnpjPorIdExistente() throws Exception {
        String json = gerarJsonCnpj("45723174000110","Empresa LTDA","Fantasia", true);
        String response = mockMvc.perform(post("/omni/criar_cnpj")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
                
        Long id = mapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/omni/buscar/" + id))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.cnpj").value("45723174000110"));
    }

    // 6 GET por ID inexistente
    @Test
    void deveRetornar404ParaIdInexistente() throws Exception {
        mockMvc.perform(get("/omni/buscar/9999"))
               .andExpect(status().isNotFound());
    }

    // 7 PUT - atualizar ID inexistente
    @Test
    void deveRetornar404AoAtualizarIdInexistente() throws Exception {
        String jsonAtualizar = gerarJsonCnpj("45723174000110","Empresa Atualizada","Fantasia Atualizada", true);
        mockMvc.perform(put("/omni/atualizar/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAtualizar))
               .andExpect(status().isNotFound());
    }

    // 8 PUT - atualizar existente
    @Test
    void deveAtualizarCnpjExistente() throws Exception {
        String jsonCriar = gerarJsonCnpj("45723174000110","Empresa LTDA","Fantasia", true);
        String response = mockMvc.perform(post("/omni/criar_cnpj")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCriar))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        ObjectMapper mapper = new ObjectMapper();
        Long id = mapper.readTree(response).get("id").asLong();

        String jsonAtualizar = gerarJsonCnpj("45723174000110","Empresa Atualizada","Fantasia Atualizada", true);

        mockMvc.perform(put("/omni/atualizar/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAtualizar))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.razaoSocial").value("Empresa Atualizada"))
               .andExpect(jsonPath("$.nomeFantasia").value("Fantasia Atualizada"));
    }

    // 9️ DELETE - remover existente
    @Test
    void deveDeletarCnpjExistente() throws Exception {
        String jsonCriar = gerarJsonCnpj("45723174000110","Empresa LTDA","Fantasia", true);
        String response = mockMvc.perform(post("/omni/criar_cnpj")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCriar))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        Long id = mapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/omni/deletar/" + id))
               .andExpect(status().isNoContent());

        mockMvc.perform(get("/omni/buscar/" + id))
               .andExpect(status().isNotFound());
    }

    // 10 DELETE - ID inexistente
    @Test
    void deveRetornar404AoDeletarIdInexistente() throws Exception {
        mockMvc.perform(delete("/omni/deletar/9999"))
               .andExpect(status().isNotFound());
    }
}
