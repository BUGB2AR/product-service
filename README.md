Microservices de Produtos
Este projeto demonstra uma arquitetura de microserviços com dois serviços independentes: um para gerenciamento de produtos e outro para consulta de estoque. A comunicação entre os serviços é feita através de chamadas síncronas HTTP, com a funcionalidade de auditoria implementada usando AOP.

Tecnologias Utilizadas
Java 17+

Spring Boot 3+

Maven

MySQL (bancos de dados separados)

Docker e Docker Compose

Angular (para o front-end, em outro repositório)

Pré-requisitos
Certifique-se de que você tem as seguintes ferramentas instaladas em sua máquina:

Java Development Kit (JDK) 17+

Maven

Docker e Docker Compose

Estrutura do Projeto
O projeto é dividido em dois microsserviços, cada um com sua própria pasta:

product-service/: Microsserviço de gerenciamento de produtos (CRUD).

stock-service/: Microsserviço de consulta de estoque e auditoria.

Como Subir a Aplicação

Passo 1: Subir a Infraestrutura (Bancos de Dados)
Primeiro, você precisa inicializar os bancos de dados MySQL para cada serviço usando o Docker Compose.


1. Navegue até a pasta product-service no terminal e execute o comando para subir o banco de dados do produto:

docker-compose up -d


Passo 2: Construir e Empacotar os Microsserviços

Navegue até a pasta product-service e execute os comandos Maven:

mvn clean install e depois mvn clean package ( importante fazer isso para gerar as classes de mappers/dto do mapstruct)

Passo 3: Executar o Microsserviço

mvn spring-boot:run


Endpoints da API
Serviço de Produtos (product-service)

POST /api/produtos: Cria um novo produto.

GET /api/produtos: Lista todos os produtos.

GET /api/produtos/{id}: Busca um produto por ID.

PUT /api/produtos/{id}: Atualiza um produto.

DELETE /api/produtos/{id}: Deleta um produto.




