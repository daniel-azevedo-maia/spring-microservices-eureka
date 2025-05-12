# Microsserviços Spring Boot com Docker Compose

Este repositório apresenta um **exemplo completo de arquitetura em microsserviços** usando **Java 17** e **Spring Boot 3**.  
Ele foi construído para fins de estudo e referência, mostrando de forma prática como combinar:

* **Configuração centralizada** (`Config‑Server`)
* **Descoberta de serviços** (`Eureka`)
* **Gateway de entrada** (`Spring Cloud Gateway`)
* **Comunicação resiliente** (OpenFeign + Resilience4j)
* **Persistência isolada** (um banco PostgreSQL por domínio)
* **Orquestração de containers** (Docker Compose)

> **Autor:** Daniel Azevedo de Oliveira Maia  
> 📧 daniel.azevedo.maia@hotmail.com · [LinkedIn](https://www.linkedin.com/in/daniel-azevedo-maia/)

---

## 1. Visão geral funcional

| Camada | Serviço | Porta | Descrição rápida |
|--------|---------|-------|------------------|
| **Configuração** | `config-server` | 8888 | Distribui arquivos `application-*.yml` guardados num repositório Git. |
| **Descoberta** | `discovery-server` | 8761 | Registry Eureka; UI disponível na raiz. |
| **Gateway** | `api-gateway-service` | 8084 | Porta única exposta ao cliente; roteia para os demais serviços. |
| **Domínio** | `user-service` | 8081 | CRUD de usuários. |
| | `product-service` | 8082 | CRUD de produtos. |
| | `order-service` | 8083 | CRUD de pedidos; consome Users e Products. |
| **Banco** | `postgres-user` | 5433 | Base de usuários. |
| | `postgres-product` | 5434 | Base de produtos. |
| | `postgres-order` | 5435 | Base de pedidos. |

Fluxo em alto nível:

1. **Inicialização**  
   Docker Compose sobe primeiro os bancos, em seguida `discovery-server` e `config-server`.  
   Cada microsserviço carrega suas configurações do Git, cadastra‑se no Eureka e fica pronto para receber chamadas.
2. **Requisições externas**  
   O cliente faz chamadas HTTP no Gateway (porta 8084).  
   O Gateway consulta o Eureka para descobrir o endereço atual do serviço alvo e encaminha a requisição.
3. **Chamadas internas**  
   Serviços usam **OpenFeign** (`lb://SERVICE-NAME`) para falar entre si.  
   Resilience4j adiciona *retry*, *circuit‑breaker* e *timeout* padrão.

---

## 2. Organização Maven

### POM **pai** (`microsservices`)

* Define versões de **Spring Boot 3.2.4** e **Spring Cloud 2023.0.0**.
* Importa BOMs oficiais, evitando conflitos de dependência.
* Centraliza plugins (`maven-compiler-plugin`, `spring-boot-maven-plugin`).
* Inclui `spring-cloud-starter-config`; todos os módulos filhos já sabem falar com o Config‑Server.

Cada módulo (`user-service`, `product-service`, `order-service` …) herda essas definições e declara apenas dependências específicas (JPA, Feign, Resilience4j etc.).

---

## 3. Docker Compose resumido

* **Healthchecks** garantem que os bancos estejam prontos antes de liberar os serviços dependentes.
* Variável `SPRING_CONFIG_IMPORT=optional:configserver:http://config-server:8888` aponta cada aplicação para o Config‑Server.
* Rede dedicada `micros_net` permite que containers se resolvam pelo nome.

Para detalhes completos, consulte o arquivo [`docker-compose.yml`](docker-compose.yml).

---

## 4. Pré‑requisitos

* Docker 20 ou superior + Docker Compose v2
* Git (para clonar o projeto)  
  *(Java e PostgreSQL são providos pelos containers, não precisam ser instalados localmente.)*

---

## 5. Como executar

```bash
# 1. Clone o repositório
git clone https://github.com/daniel-azevedo-maia/spring-microservices-eureka.git
cd spring-microservices-eureka

# 2. Construa os JARs (primeira vez)
./mvnw clean package -DskipTests

# 3. Construa as imagens e suba a stack
docker compose up --build -d
```

Para verificar se tudo está de pé:

```bash
# Logs gerais
docker compose logs -f

# Logs específicos (exemplos)
docker compose logs -f config-server
docker compose logs -f discovery-server
docker compose logs -f user-service
```

Serviços úteis:

* Eureka Dashboard: <http://localhost:8761>
* Swagger do Product Service: <http://localhost:8082/swagger-ui.html>

---

## 6. Endpoints de teste rápido

### Produtos

```bash
# Listar produtos
curl http://localhost:8084/api/v1/products

# Criar novo produto
curl -X POST http://localhost:8084/api/v1/products      -H "Content-Type: application/json"      -d '{"name":"Notebook Gamer","price":4999.90,"stock":15}'
```

### Usuários

```bash
curl -X POST http://localhost:8084/api/v1/users      -H "Content-Type: application/json"      -d '{"name":"Ana Silva","email":"ana@demo.dev","phone":"11999998888"}'
```

### Pedidos

```bash
curl -X POST http://localhost:8084/api/v1/orders      -H "Content-Type: application/json"      -d '{"userId":1,"productId":1,"quantity":2}'
```

---

## 7. Tecnologias-chave

| Categoria | Stack |
|-----------|-------|
| Linguagem / Runtime | Java 17, Spring Boot 3 |
| Configuração | Spring Cloud Config Server / Client |
| Descoberta | Spring Cloud Netflix Eureka |
| Roteamento | Spring Cloud Gateway |
| Comunicação | OpenFeign + Spring Cloud LoadBalancer |
| Persistência | Spring Data JPA + PostgreSQL |
| Resiliência | Resilience4j (CircuitBreaker, Retry, TimeLimiter) |
| Containers | Docker, Docker Compose |
| Build | Maven multi‑module |

---

## 8. Roadmap

- [ ] Autenticação **JWT** no Gateway
- [ ] Observabilidade com **Prometheus, Grafana, Zipkin**
- [ ] Mensageria assíncrona (**RabbitMQ** ou **Kafka**)
- [ ] Pipeline **CI/CD** em GitHub Actions publicando imagens no Docker Hub
- [ ] Deploy em ambiente gratuito (**Fly.io**, **Railway**)
- [ ] Front‑end **Angular** consumindo o Gateway
- [ ] Testes de integração com **Testcontainers** e contratos com **Spring Cloud Contract**

---

## 9. Suporte

Abra uma *issue* ou entre em contacto:

* daniel.azevedo.maia@hotmail.com
* <https://www.linkedin.com/in/daniel-azevedo-maia/>

Obrigado por conferir este projeto!
