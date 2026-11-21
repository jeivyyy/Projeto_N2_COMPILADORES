"""Cálculo isolado para permitir a análise de bytecode com dis."""

from erros import QuantidadeInvalidaError


PERCENTUAL_DESCONTO = 0.10
LIMITE_DESCONTO = 50


def calcular_valor_final_item(quantidade: int, preco: float) -> float:
    """Retorna o valor do item, com desconto por volume quando aplicável."""
    if quantidade < 0:
        raise QuantidadeInvalidaError()

    subtotal = quantidade * preco

    # o desconto de 10% começa em 50 unidades
    if quantidade >= LIMITE_DESCONTO:
        return subtotal * (1 - PERCENTUAL_DESCONTO)

    return subtotal
