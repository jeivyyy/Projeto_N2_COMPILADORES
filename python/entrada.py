"""Leitura de um pedido externo sem dependências adicionais."""

from pathlib import Path

from modelo import Produto


CABECALHO = "nome;quantidade;preco_unitario"


def ler_produtos(caminho: Path) -> list[Produto]:
    linhas = caminho.read_text(encoding="utf-8-sig").splitlines()

    if not linhas or linhas[0].strip() != CABECALHO:
        raise ValueError(f"Cabeçalho esperado: {CABECALHO}")

    produtos = []
    for numero_linha, linha in enumerate(linhas[1:], start=2):
        if not linha.strip():
            continue
        campos = [campo.strip() for campo in linha.split(";")]

        # toda linha precisa ter nome, quantidade inteira e preço
        if len(campos) != 3:
            raise ValueError(f"Linha {numero_linha}: esperados três campos.")

        nome, quantidade, preco = campos
        try:
            produtos.append(Produto(nome, int(quantidade), float(preco)))
        except ValueError as erro:
            raise ValueError(f"Linha {numero_linha}: quantidade ou preço inválido.") from erro

    return produtos
