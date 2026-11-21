#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
# Recompila os fontes a cada execução para evitar classes desatualizadas.
bash preparar_ambiente.sh >/dev/null
exec .java-env/runtime/bin/java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp .java-env/classes Main "$@"
