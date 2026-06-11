package br.pizzaria.sistemas.model.pizzaria;

public class Ingrediente {
    private String nome;
    private int quantidade_estoque;
    private String unidade;
    private int idIngrediente;

    public Ingrediente(String nome, int quantidade_estoque, String unidade) {
        this.nome = nome;
        this.quantidade_estoque = quantidade_estoque;
        this.unidade = unidade;
    }

    public Ingrediente(int idIngrediente, String nome, int quantidade_estoque, String unidade) {
        this.idIngrediente = idIngrediente;
        this.nome = nome;
        this.quantidade_estoque = quantidade_estoque;
        this.unidade = unidade;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade_estoque() {
        return quantidade_estoque;
    }

    public String getUnidade() {
        return unidade;
    }

    @Override
    public String toString() {
        return "ID: " + idIngrediente +
                " | Nome: " + nome +
                " | Estoque: " + quantidade_estoque + " " + unidade;
    }
}
