import java.io.IOException;
import java.nio.file.Path;

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

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println(executar_cenario(1));
                return;
            }

            // a opção --todos preserva os seis cenários e suas entradas originais.
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

            // arquivos permitem testar listas próprias sem modificar a aplicação.
            if (args.length == 2 && "--arquivo".equals(args[0])) {
                double valor = Processamento.processar_pedido(
                    Entrada.ler_produtos(Path.of(args[1]))
                );
                System.out.println("Total do pedido: " + Formatacao.formatar_moeda(valor));
                return;
            }

            throw new IllegalArgumentException(
                "Uso: [--todos] | [--cenario 1..6] | [--arquivo caminho]"
            );
        } catch (IOException | IllegalArgumentException erro) {
            System.err.println("Erro: " + erro.getMessage());
            System.exit(1);
        }
    }
}
