# Car Park API 🚗🚙
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/devsuperior/sds1-wmazoni/blob/master/LICENSE) 

## Sobre o projeto

API de Sistema de gerenciamento de saída e entrada de veículos em um estacionamento. O sistema permite o cadastro de usuários e vagas. Além de calcular o preço e se houver um desconto quando o cliente for retirar o veículo.


## Índice
- [Detalhes da Aplicação](#detalhes-da-aplicação)
- [API](#api)
- [Tecnologias Usadas](#tecnologias-usadas)
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


### Dados

- MySQL
- H2 (para os testes da aplicação)


&nbsp;



&nbsp;


## Instalação 
### Pré-requisitos

- Java 17
- Pré-requisitos: Banco de Dados MySQL

&nbsp;



&nbsp;

## Configuração

### Banco de Dados

```
Nessa aplicação foi usado o MySQL, mas você pode usar qualquer outro Banco Dados SQL. Você terá que configurar somente o Drive no Spring e a credencial para acessar o seu Banco de dados.

# Criar o Banco de dados
CREATE DATABASE demo_park;

# Alterar a configuração de conexão no arquivo 'application.yml' para o seu Banco de Dados:
url: jdbc:mysql://localhost:3306/demo_park?useSSL=false&allowPublicKeyRetrieval=true&serverTimeZone=America/Manaus
username: root
password: root@pass
```


### Backend

```
# clonar repositório
git clone https://github.com/erolkss/park-api.git

# executar o projeto
./mvnw spring-boot:run
```


&nbsp;



&nbsp;
## Autor e Contato
Lucas Eduardo Lima -  [LinkedIn](https://www.linkedin.com/in/lucaserolima)

Para enviar feedback ou entrar em contato, por favor, envie um e-mail para `lucaserolima@gmail.com`.
