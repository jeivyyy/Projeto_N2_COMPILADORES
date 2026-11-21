## Preparar ambiente local isolado

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

A entrada usa `nome;quantidade;preco_unitario` e ponto decimal. O `;` não pode fazer parte do nome. Valores monetários são apresentados com vírgula decimal.

## Evidência de bytecode (para etapa posterior)

Após executar `preparar_ambiente.ps1`:

```powershell
javap -c -p -classpath .java-env/classes Calculo
```

A saída permite inspecionar especificamente o método `calcular_valor_final_item(int, double)`. A evidência deve ser coletada na etapa solicitada.

## Organização

Existem nove arquivos `.java` com nomes-base correspondentes aos nove `.py`: `modelo`, `erros`, `calculo`, `processamento`, `formatacao`, `cenarios`, `entrada`, `main` e `test_compilador`.

As classes Java seguem nomes PascalCase e estão no pacote padrão (sem `public`), permitindo manter nomes de arquivo rigorosamente iguais entre as duas versões. Os métodos de negócio têm nomes equivalentes. Não usamos frameworks, nem pacotes externos.

## Escopo

A validação de negativos e o desconto seguem estritamente o relatório parcial. A precisão financeira de `double` e eventuais outras validações permanecem pontos para futura decisão, sem alterar os requisitos por iniciativa própria.

Para reproduzir corretamente as mensagens com acentos em consoles não configurados para português, os scripts de execução definem explicitamente a codificação UTF-8 de saída do Java.
