<h1 align="center">
  Investment Aggregator API
</h1>


Esta API tem como principal objetivo gerenciar informações sobre ações de empresas, tanto do Brasil quanto do mundo em tempo real. A API facilita o acesso e a manipulação de dados de ações, permitindo a inserção, consulta e atualização de registros no banco de dados.
### Acesse a API pelo link:
[api-inverstment-aggregator.onrender.com](https://api-investment-aggregator.onrender.com/swagger-ui/index.html#)
## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign)
- [Banco de dados H2](https://www.h2database.com/html/main.html)

## Práticas adotadas

- SOLID, DRY, YAGNI, KISS
- API REST
- Consultas com Spring Data JPA
- Injeção de Dependências
- Tratamento de respostas de erro
- Geração automática do Swagger com a OpenAPI 3
- Teste Unitários com o framework JUnit 5, com o suporte do Mockito para simulação de dependências
- Integração com APIs Externas via Spring Cloud OpenFeign

## Integração com API Externa

Para obter os preços das ações em tempo real, a aplicação utiliza a API externa [brapi.dev](https://brapi.dev/dashboard). Siga as etapas abaixo para configurar o acesso:

1. **Crie uma Conta na brapi.dev:**

   Visite o [brapi.dev](https://brapi.dev/dashboard) e crie uma conta para acessar a API.

2. **Obtenha o Token de Acesso:**

   Após criar sua conta, você deve criar um token de acesso no plano grátis. Este token é necessário para autenticar as requisições à API de preços de ações.

3. **Configure o Token no Spring:**

   Adicione o token obtido às variáveis de ambiente do seu projeto Spring Boot. Você pode fazer isso de várias maneiras, como usando um arquivo `.env`, ou configurando diretamente no sistema operacional. Crie uma variável de ambiente com esse nome `TOKEN`.


## Como Executar

- Clonar repositório git
```
$ git clone https://github.com/Felipe-PereiraDev/API-investment-aggregator.git
````
- Construir o projeto:

```
$ ./mvnw clean package
```
- Executar a aplicação:
```
$ java -jar target/agregadorinvestimentos-0.0.1-SNAPSHOT.jar
```
### Versões ultilizadas
- Java: 21.0.4
- Maven: 3.9.8

A API poderá ser acessada em [localhost:8080](http://localhost:8080).

O Swagger poderá ser visualizado em [https://localhost:8080/swagger-ui/index.html#/](https://localhost:8080/swagger-ui/index.html#/)

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [insominia](https://insomnia.rest/download):
## Endpoint /users
- Criar Usuário


```
http POST : /users
[
  {
   "username": "Felipe",
   "email": "Felipe@email.com",
   "password": "senha123"
  }
]
```

- Listar Usuário

```
http GET : /users/{userId}

{
  "user_id": "7e502f53-f43b-4b5c-88f3-930ddb14905b",
  "username": "Felipe",
  "email": "Felipe@email.com"
}
```

- Listar Usuários:
```
http GET : /users

[
  {
    "user_id": "7e502f53-f43b-4b5c-88f3-930ddb14905b",
    "username": "Felipe",
    "email": "Felipe@email.com",
    "creationTimestamp": "2024-07-24T00:54:23.261696Z"
  },
  {
    "user_id": "dcbf5e6b-ca1a-4491-a6bf-69f8988933dd",
    "username": "Mario",
    "email": "mario@email.com",
    "creationTimestamp": "2024-07-24T00:57:15.935457Z"
  },
  {
    "user_id": "eeb2283c-6d37-4144-a6d6-a15442d5af6a",
    "username": "Pedro",
    "email": "pedro@email.com",
    "creationTimestamp": "2024-07-24T00:57:47.750183Z"
  }
]
```

- Remover Usuário
```
http DELETE : /users/{userId}

[ ]
```

## Endpoint /accounts e Criar Conta

- Criar conta de investimento
```
http POST : /users/{userId}/accounts

{
  "description": "Conta de Investimentos",
  "street": "rua 28 de agosto",
  "number": 89
}
```
- Listar contas do usuário
```
http GET : /users/{userId}/accounts

[
  {
    "account_id": "60cea058-7444-4908-9440-82f8780c9882",
    "description": "Conta de Investimentos"
  },
  {
    "account_id": "3cfc38b2-b781-40c3-a3c8-aeb808f86929",
    "description": " Investimentos US"
  }
]
```

- Associar ação a uma conta

```
http POST /accounts/{accountId}/stocks

{
  "stockId": "AAPL",
  "quantity": 100
}
```


- Lista todas as ação de uma conta

```
http GET /accounts/{accountId}/stocks

[
  {
    "stockId": "AAPL",
    "quantity": 100,
    "total": 22501
  },
  {
    "stockId": "AMZN",
    "quantity": 100,
    "total": 18641
  },
  {
    "stockId": "ITUB4",
    "quantity": 100,
    "total": 3419
  }
]
```

## Endpoint /stocks

- Lista todos os stocks disponíveis

```
http GET : /stocks

{
  "listStocks": [
    {
      "stockId": "AAPL",
      "description": "Apple Inc.",
      "price": 225.01
    },
    {
      "stockId": "ABEV3",
      "description": "Ambev",
      "price": 11.78
    },
    {
      "stockId": "AMZN",
      "description": "Amazon.com, Inc.",
      "price": 186.41
    },
    {
      "stockId": "B3SA3",
      "description": "B3 S.A.",
      "price": 11.18
    },
    {
      "stockId": "BBAS3",
      "description": "Banco do Brasil",
      "price": 26.98
    }
  ],
  "page": 0,
  "pageSize": 5,
  "totalElements": 21
}
```

- Adiconar ações

```
http POST : /stocks

{
  "stockId": "MULT3",
  "description": "Multiplan"
}
```

- Remover Ação
```
http DELETE : /stocks/{stockId}

[ ]
```

