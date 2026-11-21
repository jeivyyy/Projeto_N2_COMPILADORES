## Preparar ambiente isolado

Windows PowerShell:

```powershell
.\preparar_ambiente.ps1
.\executar.ps1 --todos
.\testar.ps1
```

Linux/macOS:

```bash
bash preparar_ambiente.sh
bash executar.sh --todos
bash testar.sh
```

## Execuções

```powershell
.\executar.ps1 --cenario 2
.\executar.ps1 --arquivo dados/exemplo_pedido.csv
.\executar.ps1 --arquivo dados/erro_quantidade.csv
```

O arquivo de entrada é UTF-8, tem cabeçalho `nome;quantidade;preco_unitario`, separador `;` e ponto decimal. Não aceita `;` dentro do nome.


## Módulos

`modelo`, `erros`, `calculo`, `processamento`, `formatacao`, `cenarios`, `entrada`, `main` e `test_compilador` têm correspondência 1:1 com os nove arquivos Java.

## Decisões de escopo

- Quantidade negativa resulta em erro e impede a consolidação do pedido.
- Desconto aplicado ao subtotal do item desde 50 unidades, inclusive.
- A representação numérica segue o requisito do relatório: `float`/`double` e duas casas na apresentação.
- Validações de preço negativo e precisão financeira avançada não estão especificadas no relatório-base; permanecem como pontos para eventual decisão, não foram acrescentadas silenciosamente.
