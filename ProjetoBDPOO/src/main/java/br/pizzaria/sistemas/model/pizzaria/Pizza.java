package br.pizzaria.sistemas.model.pizzaria;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private String nome;
    private String descricao;
    private float preco;
    private String tamanho;
    private List<Ingrediente> ingredientes;
    private int idPizza;

    public Pizza(String nome, String descricao, float preco, String tamanho) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.tamanho = tamanho;this.ingredientes = new ArrayList<>();
    }

    public Pizza(int idPizza, String nome, String descricao, float preco, String tamanho) {
        this.idPizza = idPizza;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.tamanho = tamanho;
        this.ingredientes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getPreco() {
        return preco;
    }

    public String getTamanho() {
        return tamanho;
    }

    public int getIdPizza() {
        return idPizza;
    }

    public void adicionarIngrediente(Ingrediente ing){
        if (ing != null) {
            this.ingredientes.add(ing);
        }
    }

    @Override
    public String toString() {
        return "ID: " + idPizza +
                " | Nome: " + nome +
                " | Descrição: " + descricao +
                " | Preço: R$" + String.format("%.2f", preco) +
                " | Tamanho: " + tamanho;
    }

}
