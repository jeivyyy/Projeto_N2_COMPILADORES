# Processamento de Vendas e Estoque — Python

Versão-fonte do trabalho N2 da disciplina Introdução a Compiladores. Python 3.10+ e apenas biblioteca padrão.

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

O ambiente `.venv` é criado localmente; não é incluído no ZIP porque não é portável entre sistemas.

Como alternativa, no Prompt de Comando do Windows (CMD), utilize os arquivos
`preparar_ambiente.cmd`, `executar.cmd` e `testar.cmd`. Assim, não é
necessário alterar a política de execução do PowerShell.

## Execuções

```powershell
.\executar.ps1 --cenario 2
.\executar.ps1 --arquivo dados/exemplo_pedido.csv
.\executar.ps1 --arquivo dados/erro_quantidade.csv
```

O arquivo de entrada é UTF-8, tem cabeçalho `nome;quantidade;preco_unitario`, separador `;` e ponto decimal. Não aceita `;` dentro do nome.

## Evidência de bytecode (para etapa posterior)

No terminal com o ambiente preparado:

```powershell
.\.venv\Scripts\python.exe -c "import dis; from calculo import calcular_valor_final_item; dis.dis(calcular_valor_final_item)"
```

Esse comando examina a função de cálculo no interpretador Python utilizado. Sua saída deve ser coletada na etapa de evidências, se solicitada.

## Módulos

`modelo`, `erros`, `calculo`, `processamento`, `formatacao`, `cenarios`, `entrada`, `main` e `test_compilador` têm correspondência 1:1 com os nove arquivos Java.

## Decisões de escopo

- Quantidade negativa resulta em erro e impede a consolidação do pedido.
- Desconto aplicado ao subtotal do item desde 50 unidades, inclusive.
- A representação numérica segue o requisito do relatório: `float`/`double` e duas casas na apresentação.
- Validações de preço negativo e precisão financeira avançada não estão especificadas no relatório-base; permanecem como pontos para eventual decisão, não foram acrescentadas silenciosamente.
