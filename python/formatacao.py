

def formatar_moeda(valor: float) -> str:
    
    numero = f"{valor:,.2f}"
    numero = numero.replace(",", "_").replace(".", ",").replace("_", ".")
    return f"R$ {numero}"
