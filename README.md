# Bem-vindos à CeluTech! <img src="https://github.com/TheDudeThatCode/TheDudeThatCode/blob/master/Assets/Hi.gif" width="29px"> 

## Sobre Nós
A CeluTech é a sua loja de confiança para todas as necessidades relacionadas a celulares. Oferecemos uma ampla seleção dos melhores smartphones.

## Produtos
- **Smartphones**: Últimos modelos das melhores marcas.

## API

Esta API foi desenvolvida para gerenciar uma loja de celular, com operações CRUD para produtos, pedidos e usuários. A estrutura do banco de dados segue o DER fornecido, sem alterações na estrutura ou relacionamento entre as entidades.

## Funcionalidades
- **CRUD para Produtos, Pedidos e Usuários**: Cada recurso da API possui endpoints para criar, ler, atualizar e deletar dados.
- **Relatório de Pedido**: Um relatório detalhado de cada pedido é gerado, incluindo informações como ID do pedido, data, valor total e itens do pedido.
- **Autenticação e Controle de Acesso**: Implementados com o módulo de segurança do Spring e JWT para garantir a segurança dos dados.

## Tecnologias Utilizadas
- Spring Boot
- Spring Security
- JWT
- Banco de dados relacional com suporte a blob/bytea

## Endpoints
Aqui você pode listar todos os endpoints disponíveis, como:
- `POST /produtos` para adicionar um novo produto
- `GET /produtos` para listar todos os produtos
- `GET /produtos/{id}` para obter detalhes de um produto específico
- `PUT /produtos/{id}` para atualizar um produto existente
- `DELETE /produtos/{id}` para remover um produto

## Tratamento de Exceções
Todas as exceções, como itens não encontrados, são tratadas com mensagens personalizadas para informar o usuário.

## Armazenamento de Imagens
As imagens dos produtos são armazenadas no banco de dados utilizando o tipo de dados blob/bytea.
