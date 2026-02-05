# Projeto Banco - Simulação em Linha de Comando
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)

Projeto desenvolvido em **Java**, simulando o funcionamento básico de um **aplicativo bancário em linha de comando (CLI)**.  
O foco principal é a prática de **programação orientada a objetos**, **lógica de programação** e **regras de negócio**, sem interface gráfica.

---

## 📌 Visão Geral

A aplicação simula operações bancárias comuns, como criação de contas, depósitos, saques, transferências e consulta de saldo, tudo realizado através do terminal.

O projeto foi desenvolvido com o objetivo de consolidar conceitos fundamentais de **Java**, como encapsulamento, classes, métodos, controle de fluxo e interação com o usuário via console.

---

## Tecnologias Utilizadas


- **Java**
- Programação Orientada a Objetos (POO)
- Aplicação em linha de comando (CLI)

---

## Objetivos do Projeto

- Praticar lógica de programação em Java
- Aplicar conceitos de POO (classes, objetos, métodos)
- Simular regras de negócio reais
- Desenvolver uma aplicação funcional sem interface gráfica
- Trabalhar com entrada e saída de dados no console

---

## Funcionalidades

- Criação de contas bancárias
- Depósito de valores
- Saque com validações
- Consulta de saldo
- Menu interativo no terminal
- Validação das entradas do usuário

---

## Estrutura do Projeto

```bash
projeto-banco/
├── src/
│   ├── application/
│   │   └── representation/
│   │   │   └── BancoController.java
│   │   └── services/
│   │   │   └── ContaService.java
│   │   │   └── SistemaBancoService.java
│   ├── config/
│   │   └── Formatador.java
│   │   └── ManipuladorString.java
│   ├── model/
│   │   └── entities/
│   │   │   └── Banco.java
│   │   │   └── Conta.java
│   │   │   └── SistemaBanco.java
│   │   └── enums/
│   │   │   └── ContaAtiva.java
│   └── Main.java
└── README.md
```
---

## Como Executar o Projeto
### Pré-requisitos:

- Java JDK 17 ou superior (ou versão compatível utilizada no projeto)

### Passo a passo

1. Clone o repositório:
```
git clone https://github.com/dev-emanuelpereira/projeto-banco.git
```

2. Acesse a pasta do projeto:

```
cd projeto-banco
```

3. Compile os arquivos Java:

```
javac src/*.java
```

4. Execute a aplicação:

```
java src.Main
```

> - Utilize o menu exibido no terminal para interagir com o sistema bancário.

---
## Observações

Este projeto possui caráter educacional e não utiliza banco de dados.
Os dados são mantidos apenas em memória durante a execução do programa.

## Possíveis Melhorias Futuras

- Persistência de dados em arquivo ou banco de dados
- Implementação de autenticação por senha
- Histórico de transações
- Tratamento avançado de exceções
- Criação de testes unitários
- Migração para interface gráfica ou API REST

---
## Autor
### Emanuel Pereira
- *GitHub:* https://github.com/dev-emanuelpereira
