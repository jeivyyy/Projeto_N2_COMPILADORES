"""Executa o cadastro interativo, os cenários da N2 ou pedidos em arquivo."""

import argparse
from pathlib import Path

from calculo import calcular_valor_final_item
from cenarios import DESCRICOES, montar_cenario
from erros import QuantidadeInvalidaError
from entrada import ler_produtos, ler_produtos_interativamente
from formatacao import formatar_moeda
from modelo import Produto
from processamento import processar_pedido


def executar_cenario(numero: int) -> str:
    """Produz uma linha demonstrável e comparável com a versão Java."""
    try:
        resultado = formatar_moeda(processar_pedido(montar_cenario(numero)))
    except QuantidadeInvalidaError as erro:
        resultado = str(erro)
    return f"Teste {numero:02d} - {DESCRICOES[numero]}: {resultado}"


def mostrar_pedido(produtos: list[Produto]) -> None:
    """Apresenta cada item e o total somente após validar o pedido inteiro."""
    # Uma quantidade negativa interrompe o cálculo sem exibir total parcial.
    total = processar_pedido(produtos)
    print("\n===== RESULTADO DO PEDIDO =====")

    if not produtos:
        print("Nenhum produto informado.")
    for produto in produtos:
        subtotal = calcular_valor_final_item(produto.quantidade, produto.preco_unitario)
        print(f"{produto.nome}: {formatar_moeda(subtotal)}")
    print(f"\nTOTAL: {formatar_moeda(total)}")


def executar_menu() -> None:
    """Oferece cadastro, testes oficiais e leitura de arquivo em um só lugar."""
    while True:
        print("\n===== SISTEMA DE VENDAS E ESTOQUE =====")
        print("1 - Cadastrar produtos manualmente")
        print("2 - Executar os seis testes")
        print("3 - Executar um teste individual")
        print("4 - Processar arquivo CSV")
        print("0 - Sair")

        try:
            opcao = input("\nEscolha uma opção: ").strip()

            # A opção zero encerra o programa sem alterar pedidos ou cenários.
            if opcao == "0":
                print("Programa encerrado.")
                return

            if opcao == "1":
                mostrar_pedido(ler_produtos_interativamente())
            elif opcao == "2":
                print("\n===== SEIS TESTES OFICIAIS =====")
                for numero in DESCRICOES:
                    print(executar_cenario(numero))
            elif opcao == "3":
                numero = int(input("Número do teste (1 a 6): ").strip())
                print(executar_cenario(numero))
            elif opcao == "4":
                caminho = input("Caminho do arquivo CSV: ").strip().strip('"')
                mostrar_pedido(ler_produtos(Path(caminho)))
            else:
                print("Opção inválida. Escolha de 0 a 4.")
        except (EOFError, KeyboardInterrupt):
            print("\nEntrada encerrada.")
            return
        except (OSError, ValueError) as erro:
            print(f"Erro: {erro}")


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    grupo = parser.add_mutually_exclusive_group()
    grupo.add_argument("--interativo", action="store_true")
    grupo.add_argument("--cenario", type=int, choices=range(1, 7))
    grupo.add_argument("--todos", action="store_true")
    grupo.add_argument("--arquivo", type=Path)
    args = parser.parse_args()

   
    if args.interativo or not (args.todos or args.cenario or args.arquivo):
        executar_menu()
        return

    try:
       
        if args.todos:
            for numero in DESCRICOES:
                print(executar_cenario(numero))
        elif args.arquivo is not None:
            valor = processar_pedido(ler_produtos(args.arquivo))
            print(f"Total do pedido: {formatar_moeda(valor)}")
        else:
            print(executar_cenario(args.cenario))
    except (OSError, ValueError) as erro:
        parser.exit(1, f"Erro: {erro}\n")


if __name__ == "__main__":
    main()