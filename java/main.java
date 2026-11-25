import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Integra o menu interativo, os cenários acadêmicos e os arquivos CSV. */
class Main {
    private Main() {
    }

    static String executar_cenario(int numero) {
        String resultado;
        try {
            double total = Processamento.processar_pedido(Cenarios.montar_cenario(numero));
            resultado = Formatacao.formatar_moeda(total);
        } catch (QuantidadeInvalidaException erro) {
            resultado = erro.getMessage();
        }
        return String.format("Teste %02d - %s: %s", numero, Cenarios.DESCRICOES.get(numero), resultado);
    }

    static void mostrar_pedido(List<Produto> produtos) {
        // Primeiro valida todos os produtos, para não apresentar um total parcial.
        double total = Processamento.processar_pedido(produtos);
        System.out.println("\n===== RESULTADO DO PEDIDO =====");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto informado.");
        }
        for (Produto produto : produtos) {
            double subtotal = Calculo.calcular_valor_final_item(
                produto.quantidade(), produto.precoUnitario()
            );
            System.out.println(produto.nome() + ": " + Formatacao.formatar_moeda(subtotal));
        }
        System.out.println("\nTOTAL: " + Formatacao.formatar_moeda(total));
    }

    static void executar_menu(Scanner teclado) {
        while (true) {
            System.out.println("\n===== SISTEMA DE VENDAS E ESTOQUE =====");
            System.out.println("1 - Cadastrar produtos manualmente");
            System.out.println("2 - Executar os seis testes");
            System.out.println("3 - Executar um teste individual");
            System.out.println("4 - Processar arquivo CSV");
            System.out.println("0 - Sair");
            System.out.print("\nEscolha uma opção: ");

            String opcao = teclado.nextLine().trim();
            try {
                switch (opcao) {
                    // A opção zero encerra o programa sem modificar os testes.
                    case "0" -> {
                        System.out.println("Programa encerrado.");
                        return;
                    }
                    case "1" -> mostrar_pedido(Entrada.ler_produtos_interativamente(teclado));
                    case "2" -> {
                        System.out.println("\n===== SEIS TESTES OFICIAIS =====");
                        for (int numero = 1; numero <= 6; numero++) {
                            System.out.println(executar_cenario(numero));
                        }
                    }
                    case "3" -> {
                        System.out.print("Número do teste (1 a 6): ");
                        int numero = Integer.parseInt(teclado.nextLine().trim());
                        System.out.println(executar_cenario(numero));
                    }
                    case "4" -> {
                        System.out.print("Caminho do arquivo CSV: ");
                        String caminho = teclado.nextLine().trim();
                        if (caminho.length() >= 2 && caminho.startsWith("\"") && caminho.endsWith("\"")) {
                            caminho = caminho.substring(1, caminho.length() - 1);
                        }
                        mostrar_pedido(Entrada.ler_produtos(Path.of(caminho)));
                    }
                    default -> System.out.println("Opção inválida. Escolha de 0 a 4.");
                }
            } catch (IOException | IllegalArgumentException erro) {
                System.out.println("Erro: " + erro.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Sem argumentos, o usuário acessa diretamente o menu interativo.
            if (args.length == 0 || (args.length == 1 && "--interativo".equals(args[0]))) {
                try (Scanner teclado = new Scanner(System.in, StandardCharsets.UTF_8)) {
                    executar_menu(teclado);
                } catch (NoSuchElementException erro) {
                    System.out.println("\nEntrada encerrada.");
                }
                return;
            }

            // Os argumentos preexistentes permanecem disponíveis sem interação.
            if (args.length == 1 && "--todos".equals(args[0])) {
                for (int numero = 1; numero <= 6; numero++) {
                    System.out.println(executar_cenario(numero));
                }
                return;
            }

            if (args.length == 2 && "--cenario".equals(args[0])) {
                System.out.println(executar_cenario(Integer.parseInt(args[1])));
                return;
            }

            if (args.length == 2 && "--arquivo".equals(args[0])) {
                double valor = Processamento.processar_pedido(
                    Entrada.ler_produtos(Path.of(args[1]))
                );
                System.out.println("Total do pedido: " + Formatacao.formatar_moeda(valor));
                return;
            }

            throw new IllegalArgumentException(
                "Uso: [--interativo] | [--todos] | [--cenario 1..6] | [--arquivo caminho]"
            );
        } catch (IOException | IllegalArgumentException erro) {
            System.err.println("Erro: " + erro.getMessage());
            System.exit(1);
        }
    }
}
