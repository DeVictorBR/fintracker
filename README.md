# 🧾 FinTracker - Sistema de Controle Financeiro
O finTracker é uma aplicação web desenvolvida com Java + Spring Boot para gerenciar movimentações financeiras de forma simples, segura e escalável.
O projeto é parte da minha jornada de aprendizado em Spring Security, arquitetura backend e boas práticas de desenvolvimento.


## 🚀 Objetivo do Projeto
Criar uma API completa para:
- Registrar receitas e despesas
- Gerenciar categorias
- Organizar movimentações financeiras
- Associar usuários às suas próprias transações
- Evoluir progressivamente os aspectos de segurança, conforme estudo novas abordagens no Spring Security
---

🔐 Autenticação & Autorização

A primeira versão da segurança foi implementada com:
- ✔ HTTP Basic Authentication
Simples e direto, ótimo para estudo inicial.
- ✔ RBAC (Role-Based Access Control)

Papéis disponíveis:
`ROLE_ADMIN`
Pode gerenciar todos os usuários, categorias e transações.
`ROLE_USER`
Pode gerenciar apenas suas próprias transações e visualizações pessoais.

Trecho simplificado da configuração inicial:
```
.csrf(AbstractHttpConfigurer::disable)
.sessionManagement(AbstractHttpConfigurer::disable)
.httpBasic(Customizer.withDefaults())
.authorizeHttpRequests(auth -> auth
.requestMatchers(HttpMethod.POST, "/auth/signup").permitAll()
.anyRequest().authenticated())
.build();
```
---

🧱 Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL 
- Maven

---

📌 Funcionalidades

👤 Usuários
- Cadastro e autenticação
- Perfis USER / ADMIN
- Controle de acesso por papéis

📂 Categorias
- Criar categorias de transações
- Tipos: INCOME (receita), EXPENSE (despesa)

💸 Movimentações
- Criar, listar, editar e excluir transações
- Vinculação ao usuário autenticado

---

### 📷 Prints / Exemplos dos Endpoints
1. Criar usuário
   <img width="1360" height="768" alt="signup" src="https://github.com/user-attachments/assets/1deaadf3-0d0a-4475-b003-48b73ce68b07" />
2. Criar categoria
   <img width="1360" height="768" alt="create_category" src="https://github.com/user-attachments/assets/2a9bf000-b745-4460-882b-11ed27390ca1" />
3. Buscar todas as categorias do usuário
   <img width="1360" height="768" alt="findall_categories" src="https://github.com/user-attachments/assets/c69114d3-3017-4565-9070-f6e641e8a5ae" />
4. Buscar categoria por ID
   <img width="1360" height="768" alt="find_category_by_id" src="https://github.com/user-attachments/assets/aac9597b-472b-4476-adf4-b906a4642c1c" />
5. Atualizar categoria
   <img width="1360" height="768" alt="update_category" src="https://github.com/user-attachments/assets/02257e41-bd44-4612-a4bd-f77951cf530a" />
6. Deletar categoria
   <img width="1360" height="768" alt="delete_category" src="https://github.com/user-attachments/assets/bf66fe9c-d932-4702-a330-3f489d4d423a" />
7. Criar transação
   <img width="1360" height="768" alt="create_transaction" src="https://github.com/user-attachments/assets/a5e8fce4-8b5a-44b8-bd34-da58510b9cca" />
8. Buscar todas as transações
   <img width="1360" height="768" alt="findall_transactions" src="https://github.com/user-attachments/assets/ba4c2cdb-41a0-468e-b563-490c76679ac6" />
9. Buscar transação por ID
    <img width="1360" height="768" alt="find_transaction_by_id" src="https://github.com/user-attachments/assets/0d63e909-5d86-4897-bf2f-4a04b83cced3" />
---

## 🛠 Próximas melhorias

Planejo evoluir este projeto conforme avanço meus estudos de Spring Security:

- 🔄 Trocar HTTP Basic por JWT Authentication
- 🔐 Revogar tokens e adicionar refresh token
- 🛡 Implementar OAuth2 (Google Login)
- 🔍 Logs mais avançados
- 🔄 Auditoria (quem criou / editou / deletou)
- 📊 Dashboard com relatórios financeiros
