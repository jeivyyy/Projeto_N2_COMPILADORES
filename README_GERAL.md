## Estrutura

```text
Projeto_N2_COMPILADORES/
├── python/
│   ├── modelo.py, erros.py, calculo.py, processamento.py
│   ├── formatacao.py, cenarios.py, entrada.py, main.py
│   ├── test_compilador.py
│   ├── dados/
│   └── scripts e README.md
├── java/
│   ├── modelo.java, erros.java, calculo.java, processamento.java
│   ├── formatacao.java, cenarios.java, entrada.java, main.java
│   ├── test_compilador.java
│   ├── dados/
│   └── scripts e README.md
└── MANIFESTO_SHA256.json
```

São **9 arquivos de código em cada versão**, com os mesmos nomes-base e funções. 

## Execução rápida no Windows

Extraia o ZIP, abra duas janelas de terminal, uma em `python` e outra em `java`.

**Python (CMD):**

```bat
preparar_ambiente.cmd
executar.cmd --todos
testar.cmd
```

**Java (CMD):**

```bat
preparar_ambiente.cmd
executar.cmd --todos
testar.cmd
```

## Regra de negócio

Cada produto é representado por nome, quantidade inteira e preço unitário de ponto flutuante (float). O subtotal recebe desconto de 10% se a quantidade for maior ou igual a 50. Quantidade negativa interrompe o processamento com `Quantidade inválida`. O total da lista é formatado com duas casas decimais. A lista vazia retorna zero.

O projeto contempla os **6 cenários da N2** e **2 verificações complementares**