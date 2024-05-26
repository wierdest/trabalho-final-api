CREATE TABLE IF NOT EXISTS public.clientes
(
    id serial PRIMARY KEY,
    nome varchar(120),
    telefone varchar(14),
    cpf varchar(11),
    data_nascimento date,

    email varchar(80),
    nome varchar(120),
    cpf varchar(11),
    telefone varchar(14),
    data_nascimento date,   
    bairro varchar(120),
    cep varchar(8),
    complemento varchar(120),
    ddd varchar(2),
    localidade varchar(120),
    logradouro varchar(120),
    uf varchar(2)

  
);

CREATE TABLE IF NOT EXISTS public.categorias
(
    id serial PRIMARY KEY,
    descricao varchar(255),
    nome varchar(255)
);

CREATE TABLE IF NOT EXISTS public.produtos
(
    id serial PRIMARY KEY,
    data_cadastro date,
    descricao varchar(120),
    imagem varchar(255),
    nome varchar(120),
    qtd_estoque numeric,
    valor_unitario numeric(38,2),
    categoria_id bigint,
    CONSTRAINT fk_categoria FOREIGN KEY(categoria_id) REFERENCES public.categorias(id)
);

CREATE TABLE IF NOT EXISTS public.pedidos
(
    id serial PRIMARY KEY,
    data_entrega date,
    data_pedido date,
    descricao varchar(255),
    status varchar(255),
    valor_total numeric(38,2),
    cliente_id bigint,
    CONSTRAINT fk_cliente FOREIGN KEY(cliente_id) REFERENCES public.clientes(id)
);

CREATE TABLE IF NOT EXISTS public.itens_pedidos
(
    id serial PRIMARY KEY,
    percentual_desconto numeric(5, 2),
    preco_venda numeric(38, 2),
    quantidade integer,
    valor_bruto numeric(38, 2),
    valor_liquido numeric(38, 2),
    pedido_id bigint,
    produto_id bigint,
    CONSTRAINT fk_pedido FOREIGN KEY(pedido_id) REFERENCES public.pedidos(id),
    CONSTRAINT fk_produto FOREIGN KEY(produto_id) REFERENCES public.produtos(id)
);

INSERT INTO public.categorias (descricao, nome)
VALUES
    ('Roupas', 'Categoria de Roupas'),
    ('Calçados', 'Categoria de Calçados'),
    ('Esportes', 'Categoria de Esportes'),
    ('Acessórios', 'Categoria de Acessórios'),
    ('Bolsas', 'Categoria de Bolsas'),
    ('Óculos', 'Categoria de Óculos'),
    ('Perfumes', 'Categoria de Perfumes'),
    ('Gravatas', 'Categoria de Gravatas'),
    ('Mochilas', 'Categoria de Mochilas'),
    ('Chapéus', 'Categoria de Chapéus');

INSERT INTO public.produtos (data_cadastro, descricao, imagem, nome, qtd_estoque, valor_unitario, categoria_id)
VALUES
('2024-05-25', 'Camiseta branca', 'imagem_caminho1.jpg', 'Camiseta branca', 50, 29.99, 1),
('2024-05-25', 'Calça jeans', 'imagem_caminho2.jpg', 'Calça jeans', 30, 59.99, 2),
('2024-05-25', 'Tênis esportivo', 'imagem_caminho3.jpg', 'Tênis esportivo', 20, 99.99, 3),
('2024-05-25', 'Relógio de pulso', 'imagem_caminho4.jpg', 'Relógio de pulso', 15, 149.99, 4),
('2024-05-25', 'Bolsa de couro', 'imagem_caminho5.jpg', 'Bolsa de couro', 10, 79.99, 5),
('2024-05-25', 'Óculos de sol', 'imagem_caminho6.jpg', 'Óculos de sol', 25, 39.99, 6),
('2024-05-25', 'Perfume feminino', 'imagem_caminho7.jpg', 'Perfume feminino', 35, 89.99, 7),
('2024-05-25', 'Gravata clássica', 'imagem_caminho8.jpg', 'Gravata clássica', 40, 19.99, 8),
('2024-05-25', 'Mochila escolar', 'imagem_caminho9.jpg', 'Mochila escolar', 20, 49.99, 9),
('2024-05-25', 'Chapéu de praia', 'imagem_caminho10.jpg', 'Chapéu de praia', 30, 14.99, 10);



INSERT INTO public.clientes (cpf, data_nascimento, email, bairro, cep, complemento, ddd, localidade, logradouro, uf, nome, telefone)
VALUES
('12345678901', '1990-01-15', 'cliente1@example.com', 'Centro', '12345678', 'Apartamento 101', '11', 'São Paulo', 'Rua A', 'SP', 'Fulano da Silva', '11223344'),
('98765432109', '1985-05-20', 'cliente2@example.com', 'Jardim', '87654321', 'Casa 202', '11', 'Rio de Janeiro', 'Rua B', 'RJ', 'Ciclano Santos', '55443322'),
('23456789012', '1978-10-30', 'cliente3@example.com', 'Copacabana', '23456789', 'Casa 303', '21', 'Rio de Janeiro', 'Rua C', 'RJ', 'Beltrano Oliveira', '33445566'),
('34567890123', '1995-03-05', 'cliente4@example.com', 'Boa Vista', '34567890', 'Casa 404', '92', 'Manaus', 'Rua D', 'AM', 'Maria Souza', '66778899'),
('45678901234', '1980-07-10', 'cliente5@example.com', 'Botafogo', '45678901', 'Apartamento 505', '21', 'Rio de Janeiro', 'Rua E', 'RJ', 'José Pereira', '99887766'),
('56789012345', '1992-12-20', 'cliente6@example.com', 'Barra da Tijuca', '56789012', 'Casa 606', '21', 'Rio de Janeiro', 'Rua F', 'RJ', 'Ana Oliveira', '55443322'),
('67890123456', '1987-08-25', 'cliente7@example.com', 'Leblon', '67890123', 'Casa 707', '21', 'Rio de Janeiro', 'Rua G', 'RJ', 'Paulo Silva', '99887766'),
('78901234567', '1975-04-15', 'cliente8@example.com', 'Ipanema', '78901234', 'Apartamento 808', '21', 'Rio de Janeiro', 'Rua H', 'RJ', 'Carla Santos', '66778899'),
('89012345678', '1998-06-10', 'cliente9@example.com', 'Flamengo', '89012345', 'Casa 909', '21', 'Rio de Janeiro', 'Rua I', 'RJ', 'Felipe Pereira', '99887766');

