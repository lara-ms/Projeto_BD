package br.pizzaria.sistemas.model.pizzaria;

public class PagamentoCartao extends Pagamento {
    private double taxa;

    public PagamentoCartao(double valor, double taxa) {
        super(valor);
        this.taxa = taxa;
    }

    @Override
    public double calcularTotal() {
        return valor + (valor * taxa);
    }
}
