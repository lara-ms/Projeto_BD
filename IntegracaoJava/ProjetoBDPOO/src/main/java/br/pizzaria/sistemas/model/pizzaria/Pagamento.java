package br.pizzaria.sistemas.model.pizzaria;

import br.pizzaria.sistemas.model.interfaces.Calculavel;

public class Pagamento implements Calculavel {
    protected double valor;

    public Pagamento(double valor) {
        this.valor = valor;
    }

    @Override
    public double calcularTotal() {
        return 0;
    }
}
