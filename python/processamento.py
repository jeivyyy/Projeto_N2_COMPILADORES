
from collections.abc import Iterable

from calculo import calcular_valor_final_item
from modelo import Produto


def processar_pedido(produtos: Iterable[Produto]) -> float:
    """Soma os subtotais; um item inválido interrompe o pedido."""
    total = 0.0

    # O laço processa cada produto separadamente, inclusive quando a lista é vazia.
    for produto in produtos:
        total += calcular_valor_final_item(
            produto.quantidade, produto.preco_unitario
        )

    return total
