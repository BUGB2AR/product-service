<h1>Microservices de Produtos</h1>

<p>Este projeto demonstra uma arquitetura de microsserviços com dois serviços independentes: um para gerenciamento de produtos e outro para consulta de estoque. A comunicação entre os serviços é feita através de chamadas síncronas HTTP, com a funcionalidade de auditoria implementada usando AOP.</p>

<h2>Tecnologias Utilizadas</h2>

<ul>
  <li>Java 17+</li>
  <li>Spring Boot 3+</li>
  <li>Maven</li>
  <li>MySQL (bancos de dados separados)</li>
  <li>Docker e Docker Compose</li>
  <li>Angular (para o front-end, em outro repositório)</li>
</ul>

<h2>Pré-requisitos</h2>

<p>Certifique-se de que você tem as seguintes ferramentas instaladas em sua máquina:</p>

<ul>
  <li>Java Development Kit (JDK) 17+</li>
  <li>Maven</li>
  <li>Docker e Docker Compose</li>
</ul>

<h2>Estrutura do Projeto</h2>

<p>O projeto é dividido em dois microsserviços, cada um com sua própria pasta:</p>

<ul>
  <li><code>product-service/</code>: Microsserviço de gerenciamento de produtos (CRUD)</li>
  <li><code>stock-service/</code>: Microsserviço de consulta de estoque e auditoria</li>
</ul>

<h2>Como Subir a Aplicação</h2>

<h3>Passo 1: Subir a Infraestrutura (Bancos de Dados)</h3>

<p>Primeiro, você precisa inicializar os bancos de dados MySQL para cada serviço usando o Docker Compose.</p>

<ol>
  <li>
    <p>Navegue até a pasta <code>product-service</code> no terminal e execute:</p>
    <pre><code>docker-compose up -d</code></pre>
  </li>
  <li>
    <p>Repita o processo para a pasta <code>stock-service</code></p>
  </li>
</ol>

<h3>Passo 2: Construir e Empacotar os Microsserviços</h3>

<p>Navegue até a pasta <code>product-service</code> e execute:</p>
<pre><code>mvn clean install
mvn clean package</code></pre>
<p><em>(importante para gerar as classes de mappers/dto do mapstruct)</em></p>

<h3>Passo 3: Executar o Microsserviço</h3>
<pre><code>mvn spring-boot:run</code></pre>


<h3>Passo 4: Executar testes Junit</h3>
<pre><code>basta rodar na ide do Intellij o Runner de testes em Run tests Java</code></pre>

<h2>Endpoints da API</h2>

<h3>Serviço de Produtos (<code>product-service</code>)</h3>

<ul>
  <li><code>POST /api/produtos</code>: Cria um novo produto</li>
  <li><code>GET /api/produtos</code>: Lista todos os produtos</li>
  <li><code>GET /api/produtos/{id}</code>: Busca um produto por ID</li>
  <li><code>PUT /api/produtos/{id}</code>: Atualiza um produto</li>
  <li><code>DELETE /api/produtos/{id}</code>: Deleta um produto</li>
</ul>


<h3>Serviço de Estoque (<code>product-estoque</code>)</h3>

<ul>
  <li><code>GET /api/estoque{id}</code>: Consulta o estoque de um produto especifico.</li>
</ul>


