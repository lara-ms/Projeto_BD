package br.pizzaria.sistemas.model.pizzaria;

public class PagamentoPix extends Pagamento{
    private double desconto;

    public PagamentoPix(double valor, double desconto) {
        super(valor);
        this.desconto = desconto;
    }

    @Override
    public double calcularTotal() {

        return valor - (valor * desconto);
    }
}
