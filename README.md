# ZupSchool
Repositório criado para entrega do exercício de criação de API para gerenciamento escolar.

## Exercício proposto
Criar um aplicativo Java usando o Spring Framework que permite
gerenciar alunos, cursos, professores e matrículas em uma escola.

## Endpoints
1. Cadastro de alunos.
2. Cadastro de cursos.
3. Cadastro de professores.
4. Cadastro de matrículas
5. Listar todos os alunos.
6. Listar todos os professores.
7. Listar todos os cursos.
8. Listar todas as matrículas
9. Atualizar curso do aluno.
10. Deletar curso.
11. Deletar aluno.
12. Deletar professor.

## Arquitetura da aplicação
- Pasta controller: Responsável pela requisições HTTP, interage com a camada de serviço (service) encaminhando as solicitações.
- Pasta service: Responsável pela lógica e regras de negócio das solicitações vindas da camada controller.
- Pasta repository: Responsável pela persistência dos dados no banco de dados. 
- Pasta models: Responsável pelas entidades(tabelas), abstração e modelagem dos dados.
- Pasta models/dtos: Responsável pelos DTOS. objetos de transferência entre as camadas.

## API - Postman
- [ZupSchool](https://documenter.getpostman.com/view/20786077/2s9Xy5NrQN)

## Tecnologias, Conteúdos e Ferramentas utilizadas:
- Intellij
- Java
- Spring Boot Framework (Data JPA, WEB, Devtools)
- Conceitos de POO
- Princípios de SOLID
- Arquitetura em camadas
- Banco de dados relacional: Postgresql
- Postman
- DBeaver
- Docker

## Autora
- [Nicoly Barros](https://github.com/NicolyZup)

