# Desafio de Programação em Java Quarkus - Microsserviço de Recebimento e Armazenamento de Pagamento com Cartão de Crédito

## Descrição
Este microsserviço foi desenvolvido para receber dados de pagamentos fictícios realizados com cartão de crédito, validar esses dados e armazená-los em um banco de dados relacional. Além disso, o microsserviço fornece endpoints HTTP para receber, consultar e deletar os dados de pagamento armazenados.

## Requisitos Atendidos
- Utilização do framework Quarkus para criar o microsserviço Java.
- Função para receber e validar os dados do pagamento em formato JSON.
- Armazenamento dos dados de pagamento em um banco de dados relacional (MySQL).
- Criação de uma tabela adequada para armazenar as informações de pagamento.
- Implementação de endpoints HTTP utilizando JAX-RS para envio, consulta e deleção dos dados de pagamento.
- Configuração para rodar o microsserviço em container (Dockerfile, docker-compose).
- Geração de métricas Prometheus através do endpoint `/metrics`.

## Tecnologias Utilizadas
- Java 21
- Quarkus 3.15
- MySQL
- Docker
- Maven 3.9.5

## Como Executar o Programa
1. Clone o repositório:
   ```sh
   git clone https://github.com/dtdanielteles/desafio-quarkus.git
   cd desafio-quarkus
    ```
2. Configure o banco de dados MySQL e ajuste as configurações no arquivo `application.properties` .

3. Compile e execute o microsserviço
   ```sh
   mvn compile quarkus:dev
   ```
4. O microsserviço estará disponível em `http://localhost:8080`.

## Endpoints
- `POST /pagamento`: Recebe os dados de pagamento em formato JSON e armazena no banco de dados.
- `GET /pagamento`: Retorna todos os dados de pagamentos armazenados.
- `GET /pagamento/{id}`: Consulta os dados de pagamento armazenados com o ID especificado.
- `DELETE /pagamento/{id}`: Deleta os dados de pagamento armazenados com o ID especificado.
- `GET /metrics`: Exibe as métricas Prometheus do microsserviço.
- `/swagger-ui`: Interface Swagger para visualizar e testar os endpoints.
- `/health`: Disponibiliza o status da aplicação


