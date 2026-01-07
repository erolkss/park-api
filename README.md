# Car Park API 🚗🚙
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/devsuperior/sds1-wmazoni/blob/master/LICENSE) 

## Sobre o projeto

API construída para o gerenciamento de entrada e saída de veículos em um estacionamento. Essa API oferece funcionalidades completas para registrar a entrada de veículos, monitorar o tempo de permanência, calcular tarifas de estacionamento, e registrar a saída de veículos. 
A API permite aos administradores de estacionamento manter um controle sobre a ocupação e a rotatividade dos veículos. Além disso, a API suporta relatórios detalhados e históricos de movimentação.
	

API robusta e completa desenvolvida com as seguintes características:

1. **Segurança com Spring Security e JWT (JSON Web Token)**:

   Garantindo que apenas usuários autenticados e autorizados possam acessar os recursos da API. Regras e permissões são configuradas com base nos perfis dos usuários.

2. **Validação de Dados**:

   Uso de anotações de validação para garantir a integridade e a consistência dos dados recebidos pela API.

3. **Documentação com OpenAPI/Swagger**:

   A API é documentada utilizando a especificação OpenAPI, também conhecida como Swagger, facilitando a geração de documentação interativa.

4. **Internacionalização (i18n)**:

   Suporte a múltiplos idiomas, permitindo que mensagens de erro, respostas e outros textos sejam traduzidos de acordo com a preferência do usuário ou a localização geográfica.

5. **Tratamento de Exceções**:

   Mecanismo de tratamento de exceções centralizado para capturar e responder adequadamente a erros que possam ocorrer durante o processamento das requisições.

6. **Testes de Integração**:

   Conjunto de testes de integração para assegurar que todas as partes da aplicação funcionam corretamente quando combinadas. Esses testes simulam cenários reais de uso e validam a interação entre diferentes componentes do sistema.

Essa API é projetada para ser segura, eficiente e de fácil manutenção.

## Índice
- [Detalhes da Aplicação](#detalhes-da-aplicação)
- [API](#api)
- [Tecnologias Usadas](#tecnologias-usadas)
- [Docker](#docker)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Autor e Contato](#autor-e-contato)




## Detalhes da Aplicação
- A aplicação possui dois tipos de usuários **ADMIN** e **CLIENT**



## API

### Autenticar Usuário

```http
  POST /api/v1/auth
```  
Request body - <i style="color: #EE4B2B;">obrigatório</i>

```
{
  "username": "string",
  "password": "string"
}
```
---

<h2 style="color: yellow">Todas as operações relacionadas aos recursos de cadastro, edição e leitura de um usuário

### Criar Novo usuário

```http
  POST /api/v1/users
```  
Request body - <i style="color: #EE4B2B;">obrigatório</i>

```
{
  "username": "string",
  "password": "string"
}
```
---



### Retornar todos os usuários

```http
  GET /api/v1/users
```  
<i style="color: #EE4B2B;">Acesso restrito para perfis que são ADMIN</i><br>
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>


---

### Retornar Usuário por Id

```http
  GET /api/v1/users/{id}
```  
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Integer` | **Required**. ID of the item you're requesting |
---


### Atualizar Senha de Usuário

```http
  PATCH /api/v1/users/{id}
```  
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Integer` | **Required**. ID of the item you're requesting |

Request body - <i style="color: #EE4B2B;">obrigatório</i>

```
{
  "currentPassword": "string",
  "newPassword": "string",
  "confirmPassword": "string"
}

```
---

&nbsp;



&nbsp;

<h2 style="color: yellow">Todas as operações relacionadas ao recurso de Clientes do estacionamento


### Criar Novo Cliente

```http
  POST /api/v1/clients
```  
Request body - <i style="color: #EE4B2B;">obrigatório</i>

```
{
  "name": "string",
  "cpf": "stringstrin"
}
```
---

### Retornar todos os Clientes

```http
  GET /api/v1/clients
```  
<i style="color: #EE4B2B;">Acesso restrito para perfis que são ADMIN</i><br>
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>

| Parameter | Type     | Default Value |Description                |
| :-------- | :------- | :-----------| :------------------------- |
| `page` | `Integer` | 0 |**Optional**. ID of the item you're requesting |
| `size` | `Integer` | 20  |**Optional**. ID of the item you're requesting |
| `sort` | `String` | id, asc  |**Optional**. ID of the item you're requesting |

---

### Retornar Client por Id


```http
  GET /api/v1/clients/{id}
```  
<i style="color: #EE4B2B;">Acesso restrito para perfis que são ADMIN</i><br>
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Integer` | **Required**. ID of the item you're requesting |
---

### Retornar Dados do próprio Cliente


```http
  GET /api/v1/clients/details
```  
<i style="color: #EE4B2B;">Acesso restrito para perfis que são CLIENT</i><br>
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>

---

&nbsp;



&nbsp;

<h2 style="color: yellow">Todas as operações relacionadas ao recurso de vaga de estacionamento

### Criar Nova Vaga no Estacionamento

```http
  POST /api/v1/parkingSpot
```  
<i style="color: #EE4B2B;">Acesso restrito para perfis que são ADMIN</i><br>
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>

```
{
  "codeParkingSpot": "string",
  "status": "FREE"
}
```
---

### Encontrar/Procurar uma vaga

```http
  GET /api/v1/parkingSpot/{codeParkingSpot}
```  
<i style="color: #EE4B2B;">Acesso restrito para perfis que são ADMIN</i><br>
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `codeParkingSpot` | `String` | **Required**. ID of the item you're requesting |
---

&nbsp;



&nbsp;


<h2 style="color:yellow;"> Operações de registro de entrada e saída de veículo do estacionamento e gera relatório.


### CheckIn Vaga

```http
  POST /api/v1/parking/check-in
```  
<i style="color: #EE4B2B;">Acesso restrito para perfis que são ADMIN</i><br>
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i><br><br>
Request body - <i style="color: #EE4B2B;">obrigatório</i>


```
{
  "plate": "string",
  "brand": "string",
  "model": "string",
  "color": "string",
  "clientCpf": "stringstrin"
}
```
---
### Retornar checkIn pelo número do recibo

```http
  GET /api/v1/parking/check-in/{receipt}
```  
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `receipt` | `String` | **Required**. ID of the item you're requesting |
---

### CheckOut Vaga

```http
  POST /api/v1/parking/check-out/{receipt}
```  
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `receipt` | `String` | **Required**. ID of the item you're requesting |

---

### Retornar registros de estacionamento do próprio Cliente

```http
  GET /api/v1/parking
```  
<i style="color: #EE4B2B;">Acesso restrito para perfis que são CLIENT</i><br>
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i>

| Parameter | Type     | Default Value |Description                |
| :-------- | :------- | :-----------| :------------------------- |
| `page` | `Integer` | 0 |**Optional**. ID of the item you're requesting |
| `size` | `Integer` | 5  |**Optional**. ID of the item you're requesting |
| `sort` | `String` | entryDate, asc  |**Optional**. ID of the item you're requesting |

Pageable - <i style="color: #EE4B2B;">obrigatório</i>
```
{
  "page": 0,
  "size": 1,
  "sort": [
    "string"
  ]
}
```
---
### Retornar registro de client pelo CPF

```http
  GET /api/v1/parking/cpf/{cpf}
```  
<i style="color: #EE4B2B;">Acesso restrito para perfis que são ADMIN</i><br>
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i><br><br>


| Parameter | Type     | Default Value |Description                |
| :-------- | :------- | :-----------| :------------------------- |
| `cpf` | `String` | ''|**Required**. ID of the item you're requesting |
| `page` | `Integer` | 0 |**Optional**. ID of the item you're requesting |
| `size` | `Integer` | 5 |**Optional**. ID of the item you're requesting |
| `sort` | `String` | entryDate, asc  |**Optional**. ID of the item you're requesting |

Pageable - <i style="color: #EE4B2B;">obrigatório</i>
```
{
  "page": 0,
  "size": 1,
  "sort": [
    "string"
  ]
}
```
---

### Gerar Relatório

```http
  GET /api/v1/parking/report
```  
<i style="color: #EE4B2B;">Acesso restrito para perfis que são CLIENT</i><br>
<i style="color: #EE4B2B;">Autenticação obrigatória (TOKEN)</i><br><br>

&nbsp;



&nbsp;


## Tecnologias Usadas

### Backend: 
- Java 17
- Spring Boot (3.2.1)
- Spring Web
- DevTools
- Swagger (REST API - Spring Parker)
- JPA/Hibernate
- Spring Security 
- Spring Validation
- JSON Web Token
- Lombok
- JasperReports 
- ModelMapper (to DTO)
- i18n
- JUnit 

### Infraestrutura
- Docker
- Docker Compose



### Dados

- MySQL
- H2 (para os testes da aplicação)


&nbsp;



&nbsp;

## Docker

A aplicação utiliza **Docker Compose** para subir o banco de dados MySQL de forma rápida e padronizada, evitando configurações manuais no ambiente local.

### Pré-requisitos
- Docker
- Docker Compose (v2 ou superior)

### Subindo o banco de dados com Docker

Na raiz do projeto, execute:

```bash
docker compose up -d
```

Isso irá subir um container MySQL com as seguintes configurações:

- Banco: demo_park
- Porta: 3306
- Usuário: Teste
- Senha: Teste@123

O banco ficará disponível em:
```bash
jdbc:mysql://localhost:3306/demo_park
```

&nbsp;



## Instalação 
### Pré-requisitos

- Java 17
- Pré-requisitos: Docker Compose 





&nbsp;

## Clonar repositório
git clone https://github.com/erolkss/park-api.git

## Executar o projeto

```
./mvnw spring-boot:run
```


&nbsp;
## Autor e Contato
Lucas Eduardo Lima -  [LinkedIn](https://www.linkedin.com/in/lucaserolima)

Para enviar feedback ou entrar em contato, por favor, envie um e-mail para `lucaserolima@gmail.com`.
