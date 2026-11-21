"""Entidades utilizadas na venda"""

from dataclasses import dataclass


@dataclass(frozen=True, slots=True)
class Produto:
    nome: str
    quantidade: int
    preco_unitario: float
