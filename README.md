# Bem-vindo à CeluTech!

## Sobre Nós
A CeluTech é a sua loja de confiança para todas as necessidades relacionadas a celulares. Oferecemos uma ampla seleção de smartphones, acessórios e serviços de reparo.

## Produtos
- **Smartphones**: Últimos modelos das melhores marcas.
  
---

CeluTech - Conectando você ao mundo da tecnologia!

# API CeluTech

## Sobre
Esta API foi desenvolvida para gerenciar uma loja de celular, com operações CRUD para produtos, pedidos e clientes. A estrutura do banco de dados segue o DER fornecido, sem alterações na estrutura ou relacionamento entre as entidades.

## Funcionalidades
- **CRUD para Produtos, Pedidos e Clientes**: Cada recurso da API possui endpoints para criar, ler, atualizar e deletar dados.
- **Relatório de Pedido**: Um relatório detalhado de cada pedido é gerado, incluindo informações como ID do pedido, data, valor total e itens do pedido.
- **Autenticação e Controle de Acesso**: Implementados com o módulo de segurança do Spring e JWT para garantir a segurança dos dados.

## Tecnologias Utilizadas
- Spring Boot
- Spring Security
- JWT


## Endpoints
Aqui você pode listar todos os endpoints disponíveis, como:
- `POST /produtos` para adicionar um novo produto
- `GET /produtos` para listar todos os produtos
- `GET /produtos/{id}` para obter detalhes de um produto específico
- `PUT /produtos/{id}` para atualizar um produto existente
- `DELETE /produtos/{id}` para remover um produto

## Tratamento de Exceções
Todas as exceções, como itens não encontrados, são tratadas com mensagens personalizadas para informar adequadamente o usuário da API.

