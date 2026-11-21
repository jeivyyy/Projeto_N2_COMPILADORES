import argparse
from pathlib import Path

from cenarios import DESCRICOES, montar_cenario
from erros import QuantidadeInvalidaError
from entrada import ler_produtos
from formatacao import formatar_moeda
from processamento import processar_pedido


def executar_cenario(numero: int) -> str:
    try:
        resultado = formatar_moeda(processar_pedido(montar_cenario(numero)))
    except QuantidadeInvalidaError as erro:
        resultado = str(erro)
    return f"Teste {numero:02d} - {DESCRICOES[numero]}: {resultado}"


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    grupo = parser.add_mutually_exclusive_group()
    grupo.add_argument("--cenario", type=int, choices=range(1, 7))
    grupo.add_argument("--todos", action="store_true")
    grupo.add_argument("--arquivo", type=Path)
    args = parser.parse_args()

    try:
        # A execução geral usa as mesmas seis entradas do planejamento acadêmico.
        if args.todos:
            for numero in DESCRICOES:
                print(executar_cenario(numero))
            return

        # Um arquivo permite processar um pedido real sem alterar o código-fonte.
        if args.arquivo is not None:
            valor = processar_pedido(ler_produtos(args.arquivo))
            print(f"Total do pedido: {formatar_moeda(valor)}")
            return

        print(executar_cenario(args.cenario or 1))
    except (OSError, ValueError) as erro:
        parser.exit(1, f"Erro: {erro}\n")


if __name__ == "__main__":
    main()
