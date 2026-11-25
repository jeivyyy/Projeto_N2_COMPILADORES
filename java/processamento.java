/** Responsável por agregar os subtotais de um pedido. */
final class Processamento { 
    private Processamento() {
    }

    static double processar_pedido(Iterable<Produto> produtos) {
        double total = 0.0;

        // Cada item é calculado em separado; erro interrompe a consolidação.
        for (Produto produto : produtos) {
            total += Calculo.calcular_valor_final_item(
                produto.quantidade(), produto.precoUnitario()
            );
        }

        return total;
    }
}
