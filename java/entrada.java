import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Leitura da mesma entrada externa na versão Python. */
final class Entrada {
    private static final String CABECALHO = "nome;quantidade;preco_unitario";

    private Entrada() {
    }

    static List<Produto> ler_produtos(Path caminho) throws IOException {
        List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8);
        String cabecalho = linhas.isEmpty() ? "" : linhas.get(0).replace("\uFEFF", "").trim();

        //  cabeçalho não interpretaa os campos do arquivo em ordem incorreta
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
}
