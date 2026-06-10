package br.pizzaria.sistemacentral;

import br.pizzaria.sistemas.dao.*;
import br.pizzaria.sistemas.model.pizzaria.*;
import br.pizzaria.sistemas.model.threads.AtualizaPedido;

import java.util.List;
import java.util.Scanner;

public class Sistema {

    private final Scanner sc = new Scanner(System.in);
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final PizzaDAO pizzaDAO = new PizzaDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final IngredienteDAO ingredienteDAO = new IngredienteDAO();

    // MENU

    public void menu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n===== PIZZARIA =====");
            System.out.println("[ 1 ] Clientes");
            System.out.println("[ 2 ] Pizzas");
            System.out.println("[ 3 ] Pedidos");
            System.out.println("[ 4 ] Ingredientes");
            System.out.println("[ 5 ] Relatorio");
            System.out.println("[ 0 ] Sair");
            System.out.print("Opcao: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuPizzas();
                    break;
                case 3:
                    menuPedidos();
                    break;
                case 4:
                    menuIngredientes();
                    break;
                case 5:
                    menuRelatorios();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        }
    }

    // CLIENTE

    private void menuClientes() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("[ 1 ] Cadastrar cliente");
            System.out.println("[ 2 ] Listar clientes");
            System.out.println("[ 3 ] Atualizar telefone/endereco");
            System.out.println("[ 4 ] Deletar cliente");
            System.out.println("[ 5 ] Buscar cliente por nome");
            System.out.println("[ 6 ] Buscar cliente por id");
            System.out.println("[ 0 ] Voltar");
            System.out.print("Opcao: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    deletarCliente();
                    break;
                case 5:
                    buscarClienteNome();
                    break;
                case 6:
                    buscarClienteId();
                    break;
                case 0:
                    {}
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        }
    }

    private void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        System.out.print("Endereco: ");
        String endereco = sc.nextLine();

        Cliente cliente = new Cliente(0, nome, email, telefone, endereco);
        int idClienteCriada = clienteDAO.inserirCliente(cliente);
        if (idClienteCriada != -1) {
            System.out.println("Cliente cadastrado com sucesso! ID gerado: " + idClienteCriada);
        } else {
            System.out.println("Erro ao cadastrar cliente.");
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteDAO.selectCliente();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println();
        System.out.println("--- Lista de Clientes ---");
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    private void atualizarCliente() {
        System.out.print("Digite o ID do cliente que deseja atualizar: ");
        int id = lerOpcao();
        sc.nextLine();

        Cliente clienteExistente = clienteDAO.buscarClientePorId(id);
        if (clienteExistente == null) {
            System.out.println("Erro: Cliente com ID " + id + " não encontrado!");
            return;
        }

        System.out.print("Novo telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Novo endereco: ");
        String endereco = sc.nextLine();

        Cliente cliente = new Cliente(id, "", "", telefone, endereco);
        if (clienteDAO.updateCliente(cliente)) {
            System.out.println("Cliente atualizado com sucesso!");
        }
    }

    private void deletarCliente() {
        System.out.print("ID do cliente a deletar: ");
        int idDel = lerOpcao();
        Cliente c = clienteDAO.buscarClientePorId(idDel);
        if(c != null) {
            if(clienteDAO.deleteCliente(c)) System.out.println("Removido!");
        } else {
            System.out.println("ID não encontrado.");
        }
    }

    private void buscarClienteNome() {
        System.out.print("Digite o nome do cliente: ");
        String nome = sc.nextLine();

        Cliente c = clienteDAO.buscarClientePorNome(nome);
        if (c != null) {
            System.out.println("Cliente encontrado: " + c);
        } else {
            System.out.println("Nenhum cliente encontrado com o nome: " + nome);
        }
    }

    private void buscarClienteId() {
        System.out.print("Digite o ID do cliente: ");
        int id = lerOpcao();

        Cliente c = clienteDAO.buscarClientePorId(id);
        if (c != null) {
            System.out.println("Cliente encontrado: " + c);
        } else {
            System.out.println("Nenhum cliente encontrado com o ID " + id);
        }
    }

    // PIZZA

    private void menuPizzas() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- PIZZAS ---");
            System.out.println("[ 1 ] Cadastrar pizza");
            System.out.println("[ 2 ] Listar pizzas");
            System.out.println("[ 3 ] Atualizar pizza");
            System.out.println("[ 4 ] Deletar pizza");
            System.out.println("[ 5 ] Buscar pizza pelo nome");
            System.out.println("[ 6 ] Adicionar ingredientes");
            System.out.println("[ 7 ] Listar ingredientes da pizza");
            System.out.println("[ 0 ] Voltar");
            System.out.print("Opcao: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    cadastrarPizza();
                    break;
                case 2:
                    listarPizzas();
                    break;
                case 3:
                    atualizarPizzas();
                    break;
                case 4:
                    deletarPizzas();
                    break;
                case 5:
                    buscarPizzaNome();
                    break;
                case 6:
                    vincularIngredienteAPizza();
                    break;
                case 7:
                    listarIngredientesPizza();
                    break;
                case 0:
                    {}
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        }
    }

    private void cadastrarPizza() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Descricao: ");
        String descricao = sc.nextLine();

        System.out.print("Preco (valor com ' , '): ");
        float preco = sc.nextFloat();
        sc.nextLine();
        System.out.print("Tamanho (P/M/G): ");
        String tamanho = sc.nextLine();


        Pizza pizza = new Pizza(nome, descricao, preco, tamanho);
        int idPizzaCriada = pizzaDAO.inserirPizza(pizza);
        if (idPizzaCriada != -1) {
            System.out.println("Pizza cadastrada com sucesso! ID gerado: " + idPizzaCriada);
        } else {
            System.out.println("Erro ao cadastrar pizza.");
        }
    }

    private void listarPizzas() {
        List<Pizza> pizzas = pizzaDAO.selectPizza();
        if (pizzas.isEmpty()) {
            System.out.println("Nenhuma pizza cadastrada.");
            return;
        }
        pizzas.forEach(System.out::println);
    }

    private void atualizarPizzas() {
        System.out.print("Digite o ID da pizza que deseja atualizar: ");
        int id = lerOpcao();

        System.out.print("Novo nome: ");
        String nome = sc.nextLine();

        System.out.print("Nova descricao: ");
        String descricao = sc.nextLine();

        System.out.print("Novo preco: ");
        float preco = sc.nextFloat();
        sc.nextLine();

        System.out.print("Novo tamanho (P/M/G): ");
        String tamanho = sc.nextLine();

        Pizza pizzaAtualizada = new Pizza(nome, descricao, preco, tamanho);

        pizzaDAO.updatePizza(pizzaAtualizada, id);
        System.out.println("Pizza atualizada com sucesso!");

    }

    private void deletarPizzas() {
        System.out.print("ID da pizza a deletar: ");
        int idDel = lerOpcao();
        if(pizzaDAO.deletePizza(idDel)) {
            System.out.println("Pizza removida!");
        } else {
            System.out.println("Erro ao remover ou ID inexistente.");
        }
    }

    private void buscarPizzaNome() {
        System.out.print("Digite o nome da pizza: ");
        String nome = sc.nextLine();

        Pizza p = pizzaDAO.buscarPizzaPorNome(nome);

        if (p != null) {
            System.out.println("Pizza encontrada:");
            System.out.println(p);
        } else {
            System.out.println("Nenhuma pizza encontrada com o nome: " + nome);
        }
    }

    private void vincularIngredienteAPizza() {
        System.out.print("Digite o ID da pizza que deseja adicionar ingredientes: ");
        int idPizza = lerOpcao();
        sc.nextLine();

        Pizza pizza = pizzaDAO.buscarPizzaPorId(idPizza);

        if (pizza == null) {
            System.out.println("Pizza não encontrada!");
            return;
        }

        System.out.println("Ingredientes disponíveis:");
        List<Ingrediente> todosIngredientes = ingredienteDAO.selectIngrediente();
        todosIngredientes.forEach(System.out::println);

        System.out.print("Digite o ID do ingrediente para vincular: ");
        int idIng = lerOpcao();
        sc.nextLine();

        if (pizzaDAO.vincularIngrediente(pizza.getIdPizza(), idIng)) {
            System.out.println("Ingrediente vinculado com sucesso ao banco!");
        } else {
            System.out.println("Erro ao vincular.");
        }
    }

    private void listarIngredientesPizza() {
        System.out.print("Digite o ID da pizza para ver os ingredientes: ");
        int idPizza = lerOpcao();
        pizzaDAO.listarIngredientesDaPizza(idPizza);
    }

    // PEDIDOS

    private void menuPedidos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- PEDIDOS ---");
            System.out.println("[ 1 ] Criar pedido");
            System.out.println("[ 2 ] Listar pedidos");
            System.out.println("[ 3 ] Atualizar pedidos");
            System.out.println("[ 4 ] Deletar pedidos");
            System.out.println("[ 5 ] Deletar pedidos pelo cliente");
            System.out.println("[ 6 ] Listar detalhes do pedido");
            System.out.println("[ 7 ] Listar pedido por cliente");
            System.out.println("[ 0 ] Voltar");
            System.out.print("Opcao: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    criarPedido();
                    break;
                case 2:
                    listarPedidos();
                    break;
                case 3:
                    atualizarPedido();
                    break;
                case 4:
                    deletarPedido();
                    break;
                case 5:
                    deletarPedidoCliente();
                    break;
                case 6:
                    listarDetalhesPedido();
                    break;
                case 7:
                    listarPedidoCliente();
                    break;
                case 0:
                    {}
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        }
    }

    private void criarPedido() {
        List<Pizza> pizzas = pizzaDAO.selectPizza();
        if (pizzas.isEmpty()) {
            System.out.println("Nenhuma pizza cadastrada. Cadastre pizzas primeiro.");
            return;
        }

        System.out.println("Clientes disponíveis:");
        List<Cliente> clientes = clienteDAO.selectCliente();
        for (Cliente c : clientes) {
            System.out.println("ID: " + c.getIdCliente() + " | Nome: " + c.getNome());
        }

        System.out.print("Digite o ID do cliente: ");
        int idCliente = lerOpcao();
        sc.nextLine();

        Cliente clienteExistente = clienteDAO.buscarClientePorId(idCliente);
        if (clienteExistente == null) {
            System.out.println("Erro: Cliente com ID " + idCliente + " não encontrado!");
            return;
        }

        Pedido pedido = new Pedido(java.time.LocalDate.now().toString(), 0f, "Pendente", idCliente);

        String continuar = "s";
        while (continuar.equalsIgnoreCase("s")) {
            System.out.println("Pizzas disponíveis:");

            for (Pizza p : pizzas) {
                System.out.println(p);
            }

            System.out.print("Nome da pizza: ");
            String nomePizza = sc.nextLine();

            Pizza pizzaEncontrada = null;
            for (Pizza p : pizzas) {
                if (p.getNome().equalsIgnoreCase(nomePizza)) {
                    pizzaEncontrada = p;
                    break;
                }
            }

            if (pizzaEncontrada != null) {
                pedido.adicionarPizza(pizzaEncontrada);
                System.out.println("Pizza adicionada ao pedido!");
            } else {
                System.out.println("Pizza nao encontrada.");
            }

            System.out.print("Adicionar mais pizza? (s/n): ");
            continuar = sc.nextLine();

        }

        System.out.println("Forma de pagamento: 1-Pix  2-Cartao");
        int tipoPag = lerOpcao();
        sc.nextLine();
        Pagamento pagamento;
        if (tipoPag == 1) {
            pagamento = new PagamentoPix(pedido.getValor_total(), 0.05);
        } else {
            pagamento = new PagamentoCartao(pedido.getValor_total(), 0.03);
        }
        pedido.setPagamento(pagamento);

        int idPedidoCriado = pedidoDAO.inserirPedido(pedido, idCliente);
        if (idPedidoCriado != -1) {
            System.out.println("Pedido criado com sucesso! ID: " + idPedidoCriado);

            pedido.setIdPedido(idPedidoCriado);

            AtualizaPedido tarefa = new AtualizaPedido(pedido);
            Thread thread = new Thread(tarefa);
            thread.start();

            System.out.println("O pedido está sendo preparado... (Isso ocorrerá em segundo plano)");
        } else {
            System.out.println("Erro ao criar pedido.");
        }
    }

    private void listarPedidos() {
        List<Pedido> pedidos = pedidoDAO.selectPedido();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println();
        System.out.println("--- Lista de Clientes ---");
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }

    private void atualizarPedido() {
        System.out.print("Digite o ID do pedido que deseja atualizar: ");
        int idPedido = lerOpcao();
        sc.nextLine();

        System.out.print("Novo valor total: ");
        float valor = sc.nextFloat();
        sc.nextLine();

        System.out.print("Novo status (ex: Pendente, Pago, Entregue): ");
        String status = sc.nextLine();

        Pedido pedido = new Pedido(valor, status);
        pedido.setIdPedido(idPedido);

        pedidoDAO.updatePedido(pedido);
        System.out.println("Pedido atualizado com sucesso!");
    }

    private void deletarPedido() {
        System.out.print("ID do pedido a deletar: ");
        int id = lerOpcao();
        Pedido p = new Pedido();
        p.setIdPedido(id);
        pedidoDAO.detelePedido(p);
        System.out.println("Pedido deletado!");
    }

    private void deletarPedidoCliente() {
        System.out.print("ID do cliente para deletar todos os seus pedidos: ");
        int idCliente = lerOpcao();
        if(pedidoDAO.deletePedidoByCliente(idCliente)) {
            System.out.println("Pedidos do cliente " + idCliente + " removidos com sucesso.");
        }
    }

    private void listarDetalhesPedido() {
        System.out.println("--- Detalhes de todos os pedidos ---");
        pedidoDAO.listarDetalhesDoPedido();
    }

    private void listarPedidoCliente() {
        System.out.print("Digite o ID do cliente para filtrar os pedidos: ");
        int id = lerOpcao();

        pedidoDAO.listarPedidosClientes(id);
    }

    // INGREDIENTES

    private void menuIngredientes() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- INGREDIENTES ---");
            System.out.println("[ 1 ] Cadastrar ingrediente");
            System.out.println("[ 2 ] Listar ingredientes");
            System.out.println("[ 3 ] Atualizar estoque");
            System.out.println("[ 4 ] Deletar ingrediente");
            System.out.println("[ 0 ] Voltar");
            System.out.print("Opcao: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    cadastrarIngrediente();
                    break;
                case 2:
                    listarIngredientes();
                    break;
                case 3:
                    atualizarIngrediente();
                    break;
                case 4:
                    deletarIngrediente();
                    break;
                case 0:
                    {}
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        }
    }

    private void cadastrarIngrediente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Quantidade em estoque: ");
        int qtd = lerOpcao();
        System.out.print("Unidade (kg/g/un): ");
        String unidade = sc.nextLine();

        Ingrediente ing = new Ingrediente(nome, qtd, unidade);
        if (ingredienteDAO.inserirIngrediente(ing)) {
            System.out.println("Ingrediente cadastrado!");
        }
    }

    private void listarIngredientes() {
        ingredienteDAO.selectIngrediente().forEach(System.out::println);
    }

    private void atualizarIngrediente() {
        System.out.print("Digite o ID do ingrediente: ");
        int id = lerOpcao();
        sc.nextLine();

        System.out.print("Nova quantidade: ");
        int qtd = lerOpcao();
        sc.nextLine();

        Ingrediente ing = new Ingrediente(id, "", qtd, "");

        if (ingredienteDAO.updateIngrediente(ing)) {
            System.out.println("Estoque atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar estoque.");
        }
    }

    private void deletarIngrediente() {
        System.out.print("ID do ingrediente que deseja deletar: ");
        int id = lerOpcao();

        if (ingredienteDAO.deleteIngrediente(id)) {
            System.out.println("Ingrediente deletado com sucesso!");
        } else {
            System.out.println("Erro ao deletar: Ingrediente não encontrado ou erro no banco.");
        }
    }

    // RELATORIO

    private void menuRelatorios() {
        System.out.println("\n--- RELATÓRIOS ---");
        System.out.println("[ 1 ] Ver pedidos por cliente");
        System.out.println("[ 0 ] Voltar");
        System.out.print("Opção: ");
        int op = lerOpcao();

        switch (op){
            case 1:
                listarPedidosClientes();
                break;
            case 0:
                {}
            default:
                System.out.println("Opcao invalida.");
                break;

        }
    }

    private void listarPedidosClientes(){
        RelatorioDAO relatorioDAO = new RelatorioDAO();
        relatorioDAO.listarPedidosComClientes();
    }

    // DEMAIS

    private int lerOpcao() {
        String input = sc.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números.");
            return -1;
        }
    }

}

