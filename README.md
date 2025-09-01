# CadastroCNPJ

Projeto Spring Boot para cadastro de CNPJs e sócios, com validação de documentos (CPF/CNPJ) e testes completos (unitários, integração e repositório).

---

## 🛠 Tecnologias Utilizadas

- Java 21  
- Spring Boot  
- Spring Data JPA / H2 Database (desenvolvimento/testes)  
- Mockito / JUnit 5  
- Lombok  
- Swagger para documentação da API  

---

## 🚀 Como Executar o Projeto

1. Clone o repositório:
git clone https://github.com/GustavoMatos13/teste-backend-java.git

cd cadastro-cnpj

2. Build e execução usando Maven:

./mvnw clean install

./mvnw spring-boot:run

## 🌐 Endpoints da API


A documentação interativa está disponível via Swagger UI:
http://localhost:8080/swagger-ui.html

| Método | Endpoint             | Descrição               | Corpo da Requisição (JSON)                                                                                                                                                                                                                                                                          |
| ------ | -------------------- | ----------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| POST   | /omni/criar\_cnpj    | Cria um CNPJ com sócios | `json { "cnpj": "45723174000110", "razaoSocial": "Empresa LTDA", "nomeFantasia": "Fantasia", "socios": [ {"tipo":"PF","documento":"12345678909","nome":"João","porcentagemParticipacao":50.0}, {"tipo":"PJ","documento":"45723174000110","nome":"Empresa ABC","porcentagemParticipacao":50.0} ] } ` |
| GET    | /omni/buscar/{id}    | Busca CNPJ por ID       | —                                                                                                                                                                                                                                                                                                   |
| PUT    | /omni/atualizar/{id} | Atualiza CNPJ existente | `json { "cnpj": "45723174000110", "razaoSocial": "Empresa Atualizada", "nomeFantasia": "Fantasia Atualizada", "socios": [ {"tipo":"PF","documento":"12345678909","nome":"João","porcentagemParticipacao":50.0} ] } `                                                                                |
| DELETE | /omni/deletar/{id}   | Deleta CNPJ             | —                                                                                                                                                                                                                                                                                                   |

observação: o campo TIPO da entidade socio só aceita PJ ou PF como valores

## ✅ Decisões e Justificativas

- **Arquitetura em MVC**: foi desenvolvido em cima de uma arquitetura simples, eficiente e escalonavel.  
- **DTOs separados das entidades**: evita exposição de campos sensíveis e permite validação de entrada.  
- **Validação de documentos**: anotação customizada `@DocumentoValido` valida CPF, CNPJ ou ambos.  
- **Service isolada da Repository**: regras de negócio centralizadas na service, repository apenas persiste.  
- **Testes unitários e de integração**: unitários com Mockito, integração com MockMvc e banco H2 em memória.  
- **Swagger**: documentação interativa da API, facilita teste e validação dos endpoints.  
- **Banco H2 em memória**: usado para desenvolvimento e testes; fácil de trocar para produção.  
- **Sócios PF e PJ**: cada documento é validado conforme tipo, com percentuais de participação.  


## 🧪 Como Rodar os Testes

- **Testes unitários (Service e Repository):
./mvnw test
- **Testes de integração (MockMvc + H2):
./mvnw verify
