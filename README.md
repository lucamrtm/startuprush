[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7-green)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue)](https://react.dev/)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-brightgreen)](https://maven.apache.org/)

Startup Rush 🚀





📋 Sumário
- [Sobre o Projeto](#📖sobre-o-projeto)
- [Demonstração](#📸Demonstração)
- [Tecnologias Utilizadas](#📚tecnologias-utilizadas)
- [Como Rodar o Projeto](#🚀como-rodar-o-projeto)
- [Funcionalidades](#🖥️funcionalidades)
- [Configurações Extras](#⚙️configurações-extras)
- [Autor](#⚙️autor)


Autor

## 📖Sobre o Projeto
Startup Rush é um sistema de gerenciamento de torneios de startups, combinando um frontend em React com um backend com Java Spring Boot.
O objetivo é permitir o cadastro de startups, organização de batalhas entre elas e exibição de rankings de forma prática.

## 📸Demonstração
![image](https://github.com/user-attachments/assets/67eb37f9-3c7b-41fe-b697-e379c1a7dbe9)


## 📚Tecnologias Utilizadas
Backend: Java 17+, Spring Boot

Frontend: React.js

Banco de Dados: H2 (em memória)

Build e Dependências: Maven

Utilitários: Spring Data JPA, Lombok, DevTools

## 🚀Como Rodar o Projeto
1. Clone o repositório
No terminal:
git clone https://github.com/lucamrtm/startuprush.git
cd startuprush

2. Rode o Projeto
Pré-requisitos:
Java 17 ou superior
Maven 3.8+

Comando para rodar:

./mvnw spring-boot:run

ou rodar a classe StartuprushApplication.java pela sua IDE.

✨ Ao iniciar, o acesse pelo navegador navegador em:

http://localhost:8080/

## 🖥️Funcionalidades
Cadastro de startups

Organização automática de batalhas

Sistema de pontuação

Ranking individual do torneio

Ranking geral atualizado



## ⚙️Configurações Extras
O React foi buildado usando:

npm run build

e os arquivos foram copiados para src/main/resources/static/.

Para alterar a porta padrão do servidor Spring Boot:

No arquivo src/main/resources/application.properties:
 exemplo: server.port=8081

## 👨‍💻 Autor
Feito por Luca Manfroi
