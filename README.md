# Projeto de Banco de Dados

> ## Introdução

Este trabalho apresenta a modelagem de um banco de dados para um sistema de pizzaria. O objetivo é organizar e estruturar as informações relacionadas ao funcionamento do sistema, como clientes, pedidos, pizzas, ingredientes e pagamentos. 

O sistema de pizzaria foi escolhido por possuir diversas entidades e relacionamentos, per­mitindo uma modelagem completa e coerente com os conceitos estudados em sala. 

> ## Entidades

As entidades representam os principais elementos do sistema e permitem a organi­zação das informações de forma estruturada. 

Estão presentes as seguintes entidades: 
- Cliente 
- Pedido 
- Pizza 
- Ingrediente 
- Pagamento 
- Item_Pedido 
- Pizza_Ingrediente

> ## Relacionamentos

Os relacionamentos presentes no sistema são: 

- Relacionamento 1:1 (**Pedido e Pagamento**)
 
Cada pedido possui um único pagamento associado, e cada pagamento corresponde a apenas um pedido. 

- Relacionamento 1:N (**Cliente e Pedido**) 

Um cliente pode realizar vários pedidos, mas cada pedido pertence a apenas um cliente. 

- Relacionamento N:M (**Pedido e Pizza**) 

Um pedido pode conter várias pizzas e uma pizza pode estar presente em vários pedi­dos. Para representar esse relacionamento, foi criada a entidade intermediária Item_Pedido. 

- Relacionamento N:M (**Pizza e Ingrediente**) 

Uma pizza pode possuir vários ingredientes, e um ingrediente pode ser utilizado em várias pizzas. Para isso, foi criada a entidade intermediária Pizza_Ingrediente. 

> ## Modelo Relacional

<img width="837" height="446" alt="Image" src="https://github.com/user-attachments/assets/770d0f4d-30ec-4b71-9ce2-1e296d49e905" />
