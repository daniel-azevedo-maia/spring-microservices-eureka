# Microsserviços com Spring Boot, Spring Cloud, Eureka, API Gateway, Feign e Resilience4j

Este projeto é uma arquitetura completa baseada em microsserviços, desenvolvida com **Java 17**, **Spring Boot 3.2.4** e **Spring Cloud 2023.0.0**, com foco em escalabilidade, resiliência, desacoplamento e boas práticas de engenharia de software.

## 📦 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.4
- Spring Cloud (Eureka, Gateway, LoadBalancer, OpenFeign)
- Resilience4j (Circuit Breaker, Retry, Timeout)
- PostgreSQL
- Docker e Docker Compose
- Maven Multi-Módulo
- Lombok
- JPA / Hibernate Validator
- REST APIs com validação
- DTOs e Mappers
- Logs com SLF4J

## 🧩 Estrutura de Microsserviços

```
microsservices/
│
├── discovery-server         -> Service Discovery (Eureka)
├── api-gateway-service      -> Gateway de entrada para os serviços
├── user-service             -> Gerenciamento de Usuários
├── product-service          -> Gerenciamento de Produtos
└── order-service            -> Gerenciamento de Pedidos com integração entre serviços
```

## ⚙️ Casos de Uso Implementados

- CRUD completo de Usuários (User Service)
- CRUD completo de Produtos (Product Service)
- Cadastro e listagem de Pedidos (Order Service)
- Integração entre serviços com Feign Client
- Tolerância a falhas com Resilience4j (CircuitBreaker, Retry, Timeout)
- API Gateway com roteamento inteligente via Eureka
- Separação de ambiente local e docker (application-docker.yml)

## 🧠 Boas Práticas Aplicadas

- Comunicação entre serviços via Feign Client com Fallback
- Validações robustas em DTOs e Entidades
- Logs detalhados para rastreabilidade
- Tratamento de exceções customizadas
- Separação clara entre camadas: Controller, Service, Repository, Mapper
- Padrão DTO <-> Entidade
- Maven Multi-Módulo com `dependencyManagement` centralizado

## 🐳 Docker Compose

O projeto inclui um `docker-compose.yml` com:

- Containers para PostgreSQL de cada serviço
- Inicialização automática com `depends_on` e `healthcheck`
- Serviços construídos via Dockerfile de cada módulo
- Roteamento e descoberta de serviços automatizados

### Executar via Docker Compose:

```bash
docker-compose up --build
```

Acesse os serviços:

- Discovery Server (Eureka): [http://localhost:8761](http://localhost:8761)
- API Gateway: [http://localhost:8084](http://localhost:8084)
- Endpoints disponíveis via Gateway:
    - `/api/v1/users`
    - `/api/v1/products`
    - `/api/v1/orders`

## 🛠️ Build e Execução Manual (sem Docker)

```bash
# Na raiz do projeto
mvn clean install

# Subir Discovery Server
cd discovery-server && mvn spring-boot:run

# Subir os demais serviços
cd ../user-service && mvn spring-boot:run
cd ../product-service && mvn spring-boot:run
cd ../order-service && mvn spring-boot:run
cd ../api-gateway-service && mvn spring-boot:run
```

## 🔒 Próximos Passos (Sugestões de Evolução)

- Autenticação e autorização com Spring Security + JWT
- Observabilidade com Prometheus + Grafana + Micrometer
- CI/CD com GitHub Actions ou GitLab CI
- Testes automatizados com MockMvc, Testcontainers e REST Assured
- Monitoramento com Spring Boot Actuator + Zipkin ou Jaeger

## 📁 Organização por Padrões Profissionais

- Código limpo, desacoplado e escalável
- Separação de banco de dados por contexto de negócio
- Resiliência e balanceamento integrados
- Documentação e estrutura pronta para produção

---

Desenvolvido por 💻 por Daniel Azevedo de Oliveira Maia.