
def formatar_moeda(valor: float) -> str:
    """Apresenta valores com duas casas, vírgula decimal e ponto de milhar."""
    numero = f"{valor:,.2f}"
    numero = numero.replace(",", "_").replace(".", ",").replace("_", ".")
    return f"R$ {numero}"
