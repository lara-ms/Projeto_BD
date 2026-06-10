package br.pizzaria.sistemas.model.pizzaria;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Pedido {
    private String data_pedido;
    private float valor_total;
    private String status;
    private int id_cliente;
    private int idPedido;

    private List<Pizza> pizzas;
    private Pagamento pagamento;

    public Pedido(String data_pedido, float valor_total, String status, int id_cliente) {
        this.data_pedido = data_pedido;
        this.valor_total = valor_total;
        this.status = status;
        this.id_cliente = id_cliente;
        this.pizzas = new ArrayList<>();
    }

    public Pedido(String data_pedido, float valor_total, String status, int id_cliente, int idPedido) {
        this.data_pedido = data_pedido;
        this.valor_total = valor_total;
        this.status = status;
        this.id_cliente = id_cliente;
        this.idPedido = idPedido;
        this.pizzas = new ArrayList<>();
    }

    public Pedido() {
    }

    public Pedido(float valor_total, String status) {
        this.valor_total = valor_total;
        this.status = status;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getData_pedido() { return data_pedido; }

    public float getValor_total() { return valor_total; }

    public String getStatus() { return status; }

    public void adicionarPizza(Pizza pizza){
        this.pizzas.add(pizza);
        this.valor_total += pizza.getPreco();
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setPagamento(Pagamento pagamento){
        this.pagamento = pagamento;
        this.valor_total = (float) pagamento.calcularTotal();
    }

    public void gravarStatus(String status) {
        this.status = status;
        try (PrintWriter writer = new PrintWriter(new FileWriter("log_pedidos.txt", true))) {
            writer.println("Status atualizado para: " + status);
        } catch (IOException e) { e.printStackTrace(); }
    }
}