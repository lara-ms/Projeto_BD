DROP DATABASE IF EXISTS pizzaria;
CREATE DATABASE pizzaria;
USE pizzaria;
 
-- create
CREATE TABLE Cliente (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  telefone varchar(100) NOT NULL,
  endereco varchar(255) NOT NULL
);
 
CREATE TABLE Pedido (
  id INT PRIMARY KEY AUTO_INCREMENT,
  data_pedido date,
  valor_total float,
  status varchar(30),
  id_cliente INT,

  constraint fk_pedido_cliente
  foreign KEY (id_cliente) references Cliente(id)
);
 
CREATE TABLE Pizza(
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome varchar (30),
  descricao varchar(255),
  preco float,
  tamanho varchar(15)
);
 
CREATE TABLE Item_pedido(
  id INT PRIMARY KEY AUTO_INCREMENT,
  quantidade int,
  sub_total float,
  id_pedido INT,
  id_pizza INT,

  constraint fk_itempedido_pedido
  foreign KEY (id_pedido) references Pedido(id),

  constraint fk_itempedido_pizza
  foreign KEY (id_pizza) references Pizza(id)
);
 
CREATE TABLE Pagamento(
  id INT PRIMARY KEY AUTO_INCREMENT,
  tipo varchar(30),
  valor_pagamento float,
  data_pagamento date,
  id_pedido INT,
  
  constraint fk_pagamento_pedido
  foreign KEY (id_pedido) references Pedido(id)
);
 
 
CREATE TABLE Ingrediente(
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(30),
  quantidade_estoque INT,
  unidade VARCHAR(20)
);
 
-- criando usuários
DROP USER IF EXISTS 'Lara';
DROP USER IF EXISTS 'Pedro';
CREATE USER 'Lara'@'%' IDENTIFIED BY 'senha1602';
CREATE USER 'Pedro'@'%' IDENTIFIED BY 'senha1608';
 
-- criando role
DROP ROLE IF EXISTS 'role_pedidos';
CREATE ROLE 'role_pedidos';
GRANT SELECT ON pizzaria.Pizza TO 'role_pedidos';
GRANT SELECT (valor_total, status) ON pizzaria.Pedido TO 'role_pedidos';
GRANT INSERT ON pizzaria.Cliente TO 'role_pedidos';
GRANT INSERT ON pizzaria.Pagamento TO 'role_pedidos';

GRANT 'role_pedidos' TO 'Pedro'@'%';
GRANT 'role_pedidos' TO 'Lara'@'%';
 
SET DEFAULT ROLE 'role_pedidos' TO 'Pedro'@'%';
SET DEFAULT ROLE 'role_pedidos' TO 'Lara'@'%';

-- objetos programaveis
-- PROCEDURE --
DELIMITER $$

DROP PROCEDURE IF EXISTS CadastrarPedido$$
 
CREATE PROCEDURE CadastrarPedido(IN p_data DATE,IN p_valor FLOAT,IN p_status VARCHAR(30),IN p_cliente INT)
 
BEGIN
    INSERT INTO Pedido(data_pedido,valor_total,status,id_cliente) VALUES
    (p_data,p_valor,p_status,p_cliente);
END $$
 
DELIMITER ;
 
-- TRIGGER -- 
DELIMITER $$
 
CREATE TRIGGER calcular_subtotal BEFORE INSERT
ON Item_Pedido
FOR EACH ROW
BEGIN
 
    DECLARE preco_pizza FLOAT;
    SELECT preco
    INTO preco_pizza
    FROM Pizza
    WHERE id = NEW.id_pizza;
    SET NEW.sub_total = preco_pizza * NEW.quantidade;
 
END $$
DELIMITER ;
 
-- VIEW -- 
CREATE VIEW view_pedidos AS
SELECT 
    Pedido.id AS id_pedido,
    Cliente.nome AS cliente,
    Pizza.nome AS pizza,
    Item_Pedido.quantidade,
    Item_Pedido.sub_total,
    Pedido.valor_total,
    Pedido.status 
FROM Pedido
JOIN Cliente
ON Pedido.id_cliente = Cliente.id
JOIN Item_Pedido
ON Pedido.id = Item_Pedido.id_pedido 
JOIN Pizza
ON Item_Pedido.id_pizza = Pizza.id;
 
-- insert
INSERT INTO Cliente(nome, email, telefone, endereco) VALUES
('Pedro', 'pedro@gmail.com', '35999990001', 'Rua A'),
('Lara', 'lara@gmail.com', '35999990002', 'Rua B'),
('Carlos', 'carlos@gmail.com', '35999990003', 'Rua C'),
('Julia', 'julia@gmail.com', '35999990004', 'Rua D'),
('Ana', 'ana@gmail.com', '35999990005', 'Rua E');
 
INSERT INTO Pizza(nome, descricao, preco, tamanho)VALUES
('Calabresa', 'Pizza de calabresa', 40, 'Grande'),
('Frango', 'Frango com catupiry', 45, 'Grande'),
('Portuguesa', 'Pizza portuguesa', 50, 'Grande'),
('Mussarela', 'Pizza de mussarela', 35, 'Media'),
('Chocolate', 'Pizza doce', 55, 'Grande');
 
CALL CadastrarPedido('2026-05-10',80,'Em preparo',1);
CALL CadastrarPedido('2026-05-10',45,'Entregue',2);
CALL CadastrarPedido('2026-05-11',90,'Saiu para entrega',3);
CALL CadastrarPedido('2026-05-11',35,'Em preparo',4);
CALL CadastrarPedido('2026-05-12',55,'Entregue',5);
 
INSERT INTO Item_Pedido(quantidade,id_pedido,id_pizza)VALUES
(2, 1, 1),
(1, 2, 2),
(2, 3, 2),
(1, 4, 4), 
(1, 5, 5);
 
INSERT INTO Pagamento(tipo,valor_pagamento,data_pagamento,id_pedido) VALUES
('Pix', 80, '2026-05-10', 1),
('Cartao', 45, '2026-05-10', 2),
('Dinheiro', 90, '2026-05-11', 3), 
('Pix', 35, '2026-05-11', 4),
('Cartao', 55, '2026-05-12', 5);
 
INSERT INTO Ingrediente(nome,quantidade_estoque,unidade) VALUES
('Calabresa', 10, 'kg'),
('Queijo', 20, 'kg'),
('Molho', 15, 'litros'),
('Frango', 12, 'kg'),
('Chocolate', 8, 'kg');


SELECT * FROM view_pedidos;
