import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Entradas dos seis testes */
final class Cenarios {
    static final Map<Integer, String> DESCRICOES = criarDescricoes();

    private Cenarios() {
    }

    private static Map<Integer, String> criarDescricoes() {
        Map<Integer, String> descricoes = new LinkedHashMap<>();
        descricoes.put(1, "Caso usual sem desconto");
        descricoes.put(2, "Condição de limite exato");
        descricoes.put(3, "Caso usual com desconto");
        descricoes.put(4, "Quantidade zerada");
        descricoes.put(5, "Quantidade negativa");
        descricoes.put(6, "Lista vazia");
        return Map.copyOf(descricoes);
    }

    static List<Produto> montar_cenario(int numero) {
        if (!DESCRICOES.containsKey(numero)) {
            throw new IllegalArgumentException("O cenário deve estar entre 1 e 6.");
        }

        // Lista vazia e quantidade zero são condições distintas do planejamento.
        if (numero == 6) {
            return List.of();
        }

        int quantidade = switch (numero) {
            case 1 -> 20;
            case 2 -> 50;
            case 3 -> 100;
            case 4 -> 0;
            case 5 -> -10;
            default -> throw new IllegalArgumentException("Cenário desconhecido.");
        };
        return List.of(new Produto("Insumo agrícola", quantidade, 5.0));
    }
}
