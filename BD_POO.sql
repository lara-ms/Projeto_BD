DROP DATABASE IF EXISTS pizzaria;
CREATE DATABASE pizzaria;
USE pizzaria;
 
-- create
CREATE TABLE cliente (
  idCliente INT PRIMARY KEY AUTO_INCREMENT,
  nome varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  telefone varchar(100) NOT NULL,
  endereco varchar(255) NOT NULL
);
 
CREATE TABLE pedido (
  idPedido INT PRIMARY KEY AUTO_INCREMENT,
  data_pedido datetime,
  valor_total float,
  status varchar(30),
  id_cliente INT,

  constraint fk_pedido_cliente
  foreign KEY (id_cliente) references cliente(idCliente) ON DELETE CASCADE
);
 
CREATE TABLE pizza(
  idPizza INT PRIMARY KEY AUTO_INCREMENT,
  nome varchar (30),
  descricao varchar(255),
  preco float,
  tamanho varchar(15)
);
 
CREATE TABLE item_pedido(
  idItemPedido INT PRIMARY KEY AUTO_INCREMENT,
  quantidade int,
  sub_total float,
  id_pedido INT,
  id_pizza INT,

  constraint fk_itempedido_pedido
  foreign KEY (id_pedido) references pedido(idPedido) ON DELETE CASCADE,

  constraint fk_itempedido_pizza
  foreign KEY (id_pizza) references pizza(idPizza) ON DELETE CASCADE
);
 
CREATE TABLE pagamento(
  idPagamento INT PRIMARY KEY AUTO_INCREMENT,
  tipo varchar(30),
  valor_pagamento float,
  data_pagamento datetime,
  id_pedido INT,
  
  constraint fk_pagamento_pedido
  foreign KEY (id_pedido) references Pedido(idPedido) ON DELETE CASCADE
);
 
 
CREATE TABLE ingrediente(
  idIngrediente INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(30),
  quantidade_estoque INT,
  unidade VARCHAR(20)
);

CREATE TABLE pizza_ingrediente (
  idPizza INT,
  idIngrediente INT,
  PRIMARY KEY (idPizza, idIngrediente),
  FOREIGN KEY (idPizza) REFERENCES pizza(idPizza),
  FOREIGN KEY (idIngrediente) REFERENCES ingrediente(idIngrediente)
);

SELECT * FROM cliente;
SELECT * FROM pedido;
SELECT * FROM pizza;
SELECT * FROM pagamento;
SELECT * FROM ingrediente;
