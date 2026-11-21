/** Cálculo unitário isolado para posterior análise com javap. */
final class Calculo {
    private static final double PERCENTUAL_DESCONTO = 0.10;
    private static final int LIMITE_DESCONTO = 50;

    private Calculo() {
    }

    /** Mantém o nome da função Python para comparação direta do mesmo algoritmo. */
    static double calcular_valor_final_item(int quantidade, double preco) {
        //  quantidade negativa invalida o item antes do cálculo.
        if (quantidade < 0) {
            throw new QuantidadeInvalidaException();
        }

        double subtotal = quantidade * preco;

        //  se a quantidade é 50 exato, desconto de 10% ainda se aplica
        if (quantidade >= LIMITE_DESCONTO) {
            return subtotal * (1 - PERCENTUAL_DESCONTO);
        }

        return subtotal;
    }
}
