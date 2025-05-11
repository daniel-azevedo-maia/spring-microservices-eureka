
# Projeto Microsserviços com Spring Boot, Spring Cloud, Eureka, Gateway, Feign, Resilience4j e Docker

Este projeto demonstra a implementação de uma arquitetura de microsserviços moderna e escalável com as seguintes tecnologias:
- ✅ Spring Boot
- ✅ Spring Cloud Gateway
- ✅ Eureka Server (Service Discovery)
- ✅ Feign Client
- ✅ Resilience4j (Circuit Breaker, Retry, Timeout)
- ✅ Docker e Docker Compose

---

## 📦 Estrutura do Projeto

```
microsservices/
├── discovery-server/           # Eureka Server (Service Registry)
├── api-gateway-service/        # Gateway central para rotear requisições
├── user-service/               # Microsserviço de usuários
├── product-service/            # Microsserviço de produtos
├── order-service/              # Microsserviço de pedidos
└── docker-compose.yml          # Orquestração local com PostgreSQL e microsserviços
```

---

## 🔄 Como tudo funciona: Explicação Didática

Imagine que João está no navegador e acessa:

```
http://localhost:8084/api/v1/users/123
```

### O que acontece por trás:

1️⃣ **Requisição entra no Gateway** (`api-gateway-service`)  
2️⃣ O Gateway verifica se o path `/api/v1/users/**` corresponde a alguma rota:
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://USER-SERVICE
          predicates:
            - Path=/api/v1/users/**
          filters:
            - StripPrefix=0
```
🔍 Aqui ele encontrou uma rota válida.

3️⃣ O Gateway então consulta o **Eureka Server** para saber onde está o serviço `USER-SERVICE`.

4️⃣ Eureka responde: “O serviço USER-SERVICE está rodando no IP X na porta 8081”.

5️⃣ O **LoadBalancer Client (lb://)** distribui a requisição entre as instâncias disponíveis (balanceamento automático).

6️⃣ A requisição é encaminhada ao **user-service** corretamente.

---

## 📡 Como o Gateway sabe onde está o Eureka?
No arquivo `application.yml` do Gateway:
```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```
👉 Isso conecta o Gateway ao Eureka Server.

---

## 🔌 Comunicação interna entre microsserviços
Exemplo no `order-service`, onde um pedido precisa consultar um produto:
```java
@FeignClient(name = "PRODUCT-SERVICE", path = "/api/products")
public interface ProductClient {
    @GetMapping("/{id}")
    ProductDTO getProductById(@PathVariable("id") Long id);
}
```
➡ Essa chamada vai para o Gateway? NÃO. Vai direto via Eureka, usando o nome `PRODUCT-SERVICE` para buscar a instância correta.

---

## 🔐 Tolerância a Falhas com Resilience4j

No `order-service`, temos:
```yaml
resilience4j:
  circuitbreaker:
    instances:
      productServiceCircuitBreaker:
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
```

Isso permite que falhas no `product-service` não derrubem o sistema inteiro. Se der erro, ativa o fallback:
```java
@Component
public class ProductClientFallback implements ProductClient {
    public ProductDTO getProductById(Long id) {
        return ProductDTO.builder().id(id).name("Indisponível").price(0.0).stock(0).build();
    }
}
```

---

## 🐳 Orquestração com Docker Compose

O arquivo `docker-compose.yml` sobe:
- 3 containers PostgreSQL (user, product, order)
- discovery-server
- user-service, product-service, order-service
- api-gateway-service

Exemplo:
```yaml
services:
  postgres-user:
    image: postgres:15
    ports:
      - "5433:5432"
  discovery-server:
    build: ./discovery-server
    ports:
      - "8761:8761"
```

---

## 💡 Conclusão

Este projeto cobre os principais fundamentos de uma arquitetura moderna de microsserviços:
- Registro e descoberta automática de serviços
- Balanceamento de carga com Eureka + lb://
- Tolerância a falhas com Circuit Breaker
- Encapsulamento REST com FeignClient
- Orquestração local com Docker Compose

Ideal para estudos, projetos reais e entendimento de infraestrutura distribuída.

---

👨‍💻 Projeto desenvolvido com foco didático, limpo e prático.
