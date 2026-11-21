"""Erros específicos das regras do pedido."""


class QuantidadeInvalidaError(ValueError):
    """Indica quantidade negativa em um item da compra."""

    def __init__(self) -> None:
        super().__init__("Quantidade inválida")
