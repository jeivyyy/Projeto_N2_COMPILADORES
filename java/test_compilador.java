import java.util.List;

class TestCompilador {
    private static int executados = 0;
    private static int falhas = 0;

    private TestCompilador() {
    }

    private static void testar(String nome, Runnable teste) {
        executados++;
        try {
            teste.run();
            System.out.println("OK - " + nome);
        } catch (RuntimeException | AssertionError erro) {
            falhas++;
            System.err.println("FALHOU - " + nome + ": " + erro.getMessage());
        }
    }

    private static void assertAproximado(double esperado, double obtido) {
        // tolerância limita a comparação a diferenças de float.
        if (Math.abs(esperado - obtido) > 0.000001) {
            throw new AssertionError("Esperado " + esperado + ", obtido " + obtido);
        }
    }

    public static void main(String[] args) {
        testar("01 caso usual sem desconto", () ->
            assertAproximado(100.0, Processamento.processar_pedido(Cenarios.montar_cenario(1))));
        testar("02 limite exato", () ->
            assertAproximado(225.0, Processamento.processar_pedido(Cenarios.montar_cenario(2))));
        testar("03 caso com desconto", () ->
            assertAproximado(450.0, Processamento.processar_pedido(Cenarios.montar_cenario(3))));
        testar("04 quantidade zero", () ->
            assertAproximado(0.0, Processamento.processar_pedido(Cenarios.montar_cenario(4))));
        testar("05 quantidade negativa", () -> {
            try {
                Processamento.processar_pedido(Cenarios.montar_cenario(5));
                throw new AssertionError("Deveria rejeitar a quantidade negativa.");
            } catch (QuantidadeInvalidaException erro) {
                if (!"Quantidade inválida".equals(erro.getMessage())) {
                    throw new AssertionError("Mensagem inesperada.");
                }
            }
        });
        testar("06 lista vazia", () ->
            assertAproximado(0.0, Processamento.processar_pedido(Cenarios.montar_cenario(6))));
        testar("fronteira: 49 unidades", () ->
            assertAproximado(245.0, Calculo.calcular_valor_final_item(49, 5.0)));
        testar("pedido com vários itens", () -> {
            double total = Processamento.processar_pedido(List.of(
                new Produto("A", 20, 5.0), new Produto("B", 50, 5.0)
            ));
            if (!"R$ 325,00".equals(Formatacao.formatar_moeda(total))) {
                throw new AssertionError("Valor formatado incorretamente.");
            }
        });

        System.out.printf("%n%d testes executados; %d falhas.%n", executados, falhas);
        if (falhas > 0) {
            System.exit(1);
        }
    }
}
