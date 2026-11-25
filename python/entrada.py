"""Leitura de um pedido externo sem dependências adicionais."""

from pathlib import Path
from modelo import Produto
import math


CABECALHO = "nome;quantidade;preco_unitario"


def ler_produtos(caminho: Path) -> list[Produto]:
    """Lê um arquivo UTF-8 com três campos separados por ponto e vírgula."""
    linhas = caminho.read_text(encoding="utf-8-sig").splitlines()

    # O cabeçalho obrigatório impede a troca silenciosa de quantidade e preço.
    if not linhas or linhas[0].strip() != CABECALHO:
        raise ValueError(f"Cabeçalho esperado: {CABECALHO}")

    produtos = []
    for numero_linha, linha in enumerate(linhas[1:], start=2):
        if not linha.strip():
            continue
        campos = [campo.strip() for campo in linha.split(";")]

        # Cada linha precisa ter nome, quantidade inteira e preço decimal.
        if len(campos) != 3:
            raise ValueError(f"Linha {numero_linha}: esperados três campos.")

        nome, quantidade, preco = campos
        try:
            produtos.append(Produto(nome, int(quantidade), float(preco)))
        except ValueError as erro:
            raise ValueError(f"Linha {numero_linha}: quantidade ou preço inválido.") from erro

    return produtos


def _ler_sim_nao(pergunta: str) -> bool:
    """Solicita uma decisão simples, repetindo perguntas inválidas."""
    while True:
        resposta = input(pergunta).strip().lower()

        if resposta in {"s", "n"}:
            return resposta == "s"
        print("Opção inválida. Digite S ou N.")


def _ler_nome() -> str:
    """Obtém o nome do produto sem aceitar valores em branco."""
    while True:
        nome = input("Nome do produto: ").strip()

        # O nome identifica cada linha apresentada no resumo do pedido.
        if nome:
            return nome
        print("Informe o nome do produto.")


def _ler_quantidade() -> int:
    while True:
        texto = input("Quantidade: ").strip()
        try:
            return int(texto)
        except ValueError:
            print("Quantidade inválida para entrada. Informe um número inteiro.")


def _ler_preco() -> float:
    while True:
        texto = input("Preço unitário: ").strip().replace(",", ".")
        try:
            preco = float(texto)

            # Entradas não finitas não representam um preço digitável útil.
            if math.isfinite(preco):
                return preco
        except ValueError:
            pass
        print("Preço inválido. Informe um número decimal.")


def ler_produtos_interativamente() -> list[Produto]:
   # Menu 
   
    produtos: list[Produto] = []
    print("\n===== CADASTRO DE PRODUTOS =====")

    while True:
        pergunta = (
            "Deseja adicionar outro produto? (S/N): "
            if produtos else "Deseja adicionar um produto? (S/N): "
        )

        # Responder N no início permite testar uma lista inteiramente vazia.
        if not _ler_sim_nao(pergunta):
            return produtos

        nome = _ler_nome()
        quantidade = _ler_quantidade()
        preco = _ler_preco()
        produtos.append(Produto(nome, quantidade, preco))
        print("Produto adicionado.\n")
