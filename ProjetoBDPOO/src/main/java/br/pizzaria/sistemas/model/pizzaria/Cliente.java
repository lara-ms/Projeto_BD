package br.pizzaria.sistemas.model.pizzaria;

public class Cliente {
    private int idCliente;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;

    public Cliente(int idCliente, String nome, String email, String telefone, String endereco) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public  int getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public String toString() {
        return "ID: " + idCliente +
                " | Nome: " + nome +
                " | Email: " + email +
                " | Telefone: " + telefone +
                " | Endereço: " + endereco;
    }

}
