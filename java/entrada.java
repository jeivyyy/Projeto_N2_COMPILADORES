import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Leitura de pedidos por arquivo ou diretamente pelo terminal. */
final class Entrada {
    private static final String CABECALHO = "nome;quantidade;preco_unitario";

    private Entrada() {
    }

    static List<Produto> ler_produtos(Path caminho) throws IOException {
        List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8);
        String cabecalho = linhas.isEmpty() ? "" : linhas.get(0).replace("\uFEFF", "").trim();

        // O cabeçalho evita interpretar os campos do arquivo em ordem incorreta.
        if (!CABECALHO.equals(cabecalho)) {
            throw new IllegalArgumentException("Cabeçalho esperado: " + CABECALHO);
        }

        List<Produto> produtos = new ArrayList<>();
        for (int indice = 1; indice < linhas.size(); indice++) {
            String linha = linhas.get(indice);
            if (linha.isBlank()) {
                continue;
            }
            String[] campos = linha.split(";", -1);

            // O formato simples exige nome, quantidade e preço, sem delimitador no nome.
            if (campos.length != 3) {
                throw new IllegalArgumentException(
                    "Linha " + (indice + 1) + ": esperados três campos."
                );
            }

            try {
                produtos.add(new Produto(
                    campos[0].trim(),
                    Integer.parseInt(campos[1].trim()),
                    Double.parseDouble(campos[2].trim())
                ));
            } catch (NumberFormatException erro) {
                throw new IllegalArgumentException(
                    "Linha " + (indice + 1) + ": quantidade ou preço inválido.", erro
                );
            }
        }
        return produtos;
    }

    static List<Produto> ler_produtos_interativamente(Scanner teclado) {
        List<Produto> produtos = new ArrayList<>();
        System.out.println("\n===== CADASTRO DE PRODUTOS =====");

        while (true) {
            String pergunta = produtos.isEmpty()
                ? "Deseja adicionar um produto? (S/N): "
                : "Deseja adicionar outro produto? (S/N): ";

            // Responder N na primeira pergunta representa uma lista vazia.
            if (!ler_sim_nao(teclado, pergunta)) {
                return produtos;
            }

            String nome = ler_nome(teclado);
            int quantidade = ler_quantidade(teclado);
            double preco = ler_preco(teclado);
            produtos.add(new Produto(nome, quantidade, preco));
            System.out.println("Produto adicionado.\n");
        }
    }

    private static boolean ler_sim_nao(Scanner teclado, String pergunta) {
        while (true) {
            System.out.print(pergunta);
            String resposta = teclado.nextLine().trim().toLowerCase(java.util.Locale.ROOT);

            // Só confirma respostas S ou N, evitando decisões acidentais.
            if ("s".equals(resposta) || "n".equals(resposta)) {
                return "s".equals(resposta);
            }
            System.out.println("Opção inválida. Digite S ou N.");
        }
    }

    private static String ler_nome(Scanner teclado) {
        while (true) {
            System.out.print("Nome do produto: ");
            String nome = teclado.nextLine().trim();

            // Cada item precisa de um nome para ser identificado no resultado.
            if (!nome.isEmpty()) {
                return nome;
            }
            System.out.println("Informe o nome do produto.");
        }
    }

    private static int ler_quantidade(Scanner teclado) {
        while (true) {
            System.out.print("Quantidade: ");
            try {
                return Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException erro) {
                System.out.println("Quantidade inválida para entrada. Informe um número inteiro.");
            }
        }
    }

    private static double ler_preco(Scanner teclado) {
        while (true) {
            System.out.print("Preço unitário: ");
            String texto = teclado.nextLine().trim().replace(',', '.');
            try {
                double preco = Double.parseDouble(texto);

                // Valores infinitos ou NaN não devem entrar no pedido manual.
                if (Double.isFinite(preco)) {
                    return preco;
                }
            } catch (NumberFormatException erro) {
                // Uma entrada não numérica é solicitada novamente ao usuário.
            }
            System.out.println("Preço inválido. Informe um número decimal.");
        }
    }
}
