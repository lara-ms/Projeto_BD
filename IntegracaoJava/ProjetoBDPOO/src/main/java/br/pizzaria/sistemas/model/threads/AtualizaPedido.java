package br.pizzaria.sistemas.model.threads;

import br.pizzaria.sistemas.model.pizzaria.Pedido;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AtualizaPedido implements Runnable{
    private Pedido pedido;

    public AtualizaPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);

            pedido.gravarStatus("Finalizado");
            System.out.println("\n[Thread] Pedido atualizado com sucesso!");
        } catch (InterruptedException e) {
            System.out.println("Erro na thread: " + e.getMessage());
        }
    }
}
