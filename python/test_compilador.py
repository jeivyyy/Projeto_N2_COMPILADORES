"""testes automaticos dos seis cenários"""

import unittest

from calculo import calcular_valor_final_item
from cenarios import montar_cenario
from erros import QuantidadeInvalidaError
from formatacao import formatar_moeda
from modelo import Produto
from processamento import processar_pedido


class TesteProcessamentoVendas(unittest.TestCase):
    def test_01_caso_usual_sem_desconto(self) -> None:
        self.assertAlmostEqual(processar_pedido(montar_cenario(1)), 100.0)

    def test_02_limite_exato(self) -> None:
        self.assertAlmostEqual(processar_pedido(montar_cenario(2)), 225.0)

    def test_03_caso_usual_com_desconto(self) -> None:
        self.assertAlmostEqual(processar_pedido(montar_cenario(3)), 450.0)

    def test_04_quantidade_zerada(self) -> None:
        self.assertAlmostEqual(processar_pedido(montar_cenario(4)), 0.0)

    def test_05_quantidade_negativa(self) -> None:
        with self.assertRaisesRegex(QuantidadeInvalidaError, "Quantidade inválida"):
            processar_pedido(montar_cenario(5))

    def test_06_lista_vazia(self) -> None:
        self.assertAlmostEqual(processar_pedido(montar_cenario(6)), 0.0)

    def test_fronteira_49_nao_recebe_desconto(self) -> None:
        self.assertAlmostEqual(calcular_valor_final_item(49, 5.0), 245.0)

    def test_pedido_com_varios_itens(self) -> None:
        produtos = [Produto("A", 20, 5.0), Produto("B", 50, 5.0)]
        self.assertEqual(formatar_moeda(processar_pedido(produtos)), "R$ 325,00")


if __name__ == "__main__":
    unittest.main(verbosity=2)
