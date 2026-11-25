import java.util.Locale;

/** Formatação da saída em reais, com duas casas decimais. */
final class Formatacao {
    private static final Locale LOCALE_BRASILEIRO = Locale.forLanguageTag("pt-BR");

    private Formatacao() {
    }

    static String formatar_moeda(double valor) {
        return String.format(LOCALE_BRASILEIRO, "R$ %,.2f", valor);
    }
}
