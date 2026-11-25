import java.util.List;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Testes sem JUnit: mantêm a versão Java sem dependências externas. */
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
        // Pequena tolerância limita a comparação a diferenças de ponto flutuante.
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

        testar("entrada manual com desconto e vírgula", () -> {
            Scanner teclado = new Scanner("s\nFertilizante\n50\n5,00\nn\n");
            List<Produto> produtos = Entrada.ler_produtos_interativamente(teclado);
            assertAproximado(225.0, Processamento.processar_pedido(produtos));
        });
        testar("entrada manual com lista vazia", () -> {
            Scanner teclado = new Scanner("n\n");
            if (!Entrada.ler_produtos_interativamente(teclado).isEmpty()) {
                throw new AssertionError("Era esperada uma lista vazia.");
            }
        });
        testar("menu interativo apresenta o resultado", () -> {
            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            PrintStream original = System.out;
            try (PrintStream capturar = new PrintStream(saida, true, StandardCharsets.UTF_8)) {
                System.setOut(capturar);
                Main.executar_menu(new Scanner("1\ns\nFertilizante\n50\n5,00\nn\n0\n"));
            } finally {
                System.setOut(original);
            }
            String texto = saida.toString(StandardCharsets.UTF_8);
            if (!texto.contains("Fertilizante: R$ 225,00")
                    || !texto.contains("TOTAL: R$ 225,00")) {
                throw new AssertionError("Resumo do pedido incorreto.");
            }
        });

        System.out.printf("%n%d testes executados; %d falhas.%n", executados, falhas);
        if (falhas > 0) {
            System.exit(1);
        }
    }
}
