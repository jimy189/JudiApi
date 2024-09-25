# JudiApi - Spring Boot

### Tecnologias utilizadas

* Spring Boot Starter Web
* Lombok
* Spring Boot Starter Test
* Mockito Core
* Spring Boot Starter Data JPA
* Spring Boot Starter Validation
* Liquibase Core
### Como executar a aplicação
* Baixar projeto o projeto no repositório do github.
Copiar código
* git clone https://github.com/jimy189/GerenciamentoTarefaApiCrud.git

### Configure o banco de dados PostgreSQL no arquivo application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/judi
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml

Import para o postman o arquivo:  JudiApi.postman_collection.json

## Endpoints

### 1. Criar um Processo
- **URL**: `/processos`
- **Método HTTP**: `POST`
- **Descrição**: Cria um novo processo judicial com um número único e uma lista de réus.
- **Request Body**:
    ```json
    {
      "numero": "12345-67.2024.8.09.0001",
      "reus": [
        {
          "nome": "José da Silva"
        }
      ]
    }
    ```
- **Respostas**:
    - **201 Created**:
      ```json
      {
        "id": 1,
        "numero": "12345-67.2024.8.09.0001",
        "reus": [
          {
            "id": 1,
            "nome": "José da Silva"
          }
        ]
      }
      ```
    - **400 Bad Request**: Retornado se houver erros de validação, como campos obrigatórios não preenchidos.

---

### 2. Listar Todos os Processos
- **URL**: `/processos`
- **Método HTTP**: `GET`
- **Descrição**: Retorna uma lista de todos os processos cadastrados.
- **Respostas**:
    - **200 OK**:
      ```json
      [
        {
          "id": 1,
          "numero": "12345-67.2024.8.09.0001",
          "reus": [
            {
              "id": 1,
              "nome": "José da Silva"
            }
          ]
        },
        {
          "id": 2,
          "numero": "54321-89.2024.8.09.0001",
          "reus": [
            {
              "id": 2,
              "nome": "Maria Oliveira"
            }
          ]
        }
      ]
      ```

---

### 3. Excluir um Processo por Número
- **URL**: `/processos/numero/{numero}`
- **Método HTTP**: `DELETE`
- **Descrição**: Exclui um processo com base no número fornecido.

---

### 4. Adicionar Réu a um Processo
- **URL**: `/processos/{id}/reus`
- **Método HTTP**: `POST`
- **Descrição**: Adiciona um réu a um processo já existente.
- **Request Body**:
    ```json
    {
      "nome": "João dos Santos"
    }
    ```
