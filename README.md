# 🏗️ API - Construção Civil

Este projeto é uma **API desenvolvida em Java com Spring Boot** voltada para a gestão de obras no setor da construção civil.

Com ela, é possível cadastrar e gerenciar obras, equipes, funcionários, fornecedores e materiais utilizados nas construções. O objetivo é facilitar o controle e organização das atividades e recursos envolvidos em projetos de construção.

## ✨ Funcionalidades

- Cadastro e gerenciamento de obras  
- Gerenciamento de equipes e funcionários  
- Registro de materiais utilizados  
- Controle de fornecedores  

## 🛠 Tecnologias utilizadas

- Java com Spring Boot  
- PostgreSQL para o banco de dados  
- JUnit para testes unitários  

## ▶️ Como executar o projeto

1. **Pré-requisitos**:
   - Java 17 ou superior instalado  
   - PostgreSQL em execução  
   - Maven instalado (opcional, se usar IDE já configurada)

2. **Configurar o banco de dados**:
   - Crie um banco no PostgreSQL (ex: `construcao_civil`)
   - Atualize o arquivo `application.properties` com o nome do banco, usuário e senha

3. **Executar a aplicação**:
   - Via terminal:
     ```bash
     mvn spring-boot:run
     ```
   - Ou diretamente pela sua IDE (como IntelliJ ou Eclipse), rodando a classe principal

4. **Acessar a API**:
   - Por padrão, ela estará disponível em:  
     `http://localhost:8080`

5. **Documentação Swagger**:
   - A documentação interativa pode ser acessada em:  
     `http://localhost:8080/swagger-ui.html`
