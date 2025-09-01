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
```bash
git clone <URL_DO_REPOSITORIO>
cd cadastro-cnpj

2. Build e execução usando Maven:
./mvnw clean install
./mvnw spring-boot:run

## 🌐 ** Endpoints da API**

```markdown

A documentação interativa está disponível via Swagger UI:
http://localhost:8080/swagger-ui.html

| Método | Endpoint             | Descrição               |
| ------ | -------------------- | ----------------------- |
| POST   | /omni/criar\_cnpj    | Cria um CNPJ com sócios |
| GET    | /omni/buscar/{id}    | Busca CNPJ por ID       |
| PUT    | /omni/atualizar/{id} | Atualiza CNPJ existente |
| DELETE | /omni/deletar/{id}   | Deleta CNPJ             |
```

##✅ Decisões de Arquitetura e Justificativas

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
```bash
./mvnw test
- **Testes de integração (MockMvc + H2):
./mvnw verify
