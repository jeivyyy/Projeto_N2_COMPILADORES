#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
[[ -x .venv/bin/python ]] || bash preparar_ambiente.sh
exec .venv/bin/python -m unittest -v test_compilador
