"""Cálculo isolado para permitir a análise de bytecode com dis."""

from erros import QuantidadeInvalidaError


PERCENTUAL_DESCONTO = 0.10
LIMITE_DESCONTO = 50


def calcular_valor_final_item(quantidade: int, preco: float) -> float:
    # 1uantidades negativas tornam o item inválido.
    if quantidade < 0:
        raise QuantidadeInvalidaError()

    subtotal = quantidade * preco

    # O desconto de 10% começa exatamente em 50 unidades, inclusive.
    if quantidade >= LIMITE_DESCONTO:
        return subtotal * (1 - PERCENTUAL_DESCONTO)

    return subtotal
