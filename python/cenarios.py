"""Entradas fixas dos seis testes definidos no relatório N2."""

from modelo import Produto


DESCRICOES = {
    1: "Caso usual sem desconto",
    2: "Condição de limite exato",
    3: "Caso usual com desconto",
    4: "Quantidade zerada",
    5: "Quantidade negativa",
    6: "Lista vazia",
}


def montar_cenario(numero: int) -> list[Produto]:
    """Constrói uma entrada independente para um dos seis cenários."""
    if numero not in DESCRICOES:
        raise ValueError("O cenário deve estar entre 1 e 6.")

    if numero == 6:
        return []

    quantidades = {1: 20, 2: 50, 3: 100, 4: 0, 5: -10}
    return [Produto("Insumo agrícola", quantidades[numero], 5.0)]
