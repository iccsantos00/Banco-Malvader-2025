# Banco Malvader

**Projeto Acadêmico - Laboratório de Banco de Dados (2025/2)**

Este projeto consiste em uma aplicação bancária completa, desenvolvida com o objetivo de demonstrar uma arquitetura "Database-Centric". Isso significa que as regras de negócio mais críticas (como cálculos financeiros e validações de segurança) foram implementadas diretamente no banco de dados para garantir máxima integridade.

---

## Tecnologias Utilizadas

* **Backend:** Java 25 (Spring Boot 3)
* **Frontend:** React.js
* **Banco de Dados:** MySQL 8.0
* **Segurança:** Autenticação via Token JWT e Google Authenticator (MFA)

---

## Destaques do Banco de Dados

O diferencial deste sistema é o uso avançado de recursos do MySQL para gerenciar a lógica bancária:

* **Sincronização de Saldo (Triggers):** O saldo das contas é atualizado automaticamente pelo banco de dados a cada transação registrada, evitando erros de cálculo na aplicação.
* **Segurança de Limites:** O banco bloqueia automaticamente qualquer tentativa de depósito ou saque que exceda os limites diários configurados.
* **Cálculo de Score Automático (Procedures):** Uma rotina interna analisa o histórico do cliente e define uma nota de crédito de 0 a 100 automaticamente.
* **Automação de Rendimentos:** Contas poupança recebem os juros automaticamente no dia do aniversário da conta.

---

## Modelo de Dados

Abaixo, a representação visual das entidades e relacionamentos gerada automaticamente:

```mermaid
erDiagram
    USUARIO ||--o| CLIENTE : "é"
    USUARIO ||--o| FUNCIONARIO : "é"
    CLIENTE ||--|{ CONTA : "possui"
    AGENCIA ||--|{ CONTA : "administra"
    CONTA ||--|| CONTA_CORRENTE : "tipo"
    CONTA ||--|| CONTA_POUPANCA : "tipo"
    CONTA ||--|| CONTA_INVESTIMENTO : "tipo"
    CONTA ||--o{ TRANSACAO : "movimenta"
