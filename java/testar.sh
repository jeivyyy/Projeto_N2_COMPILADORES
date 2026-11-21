#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
bash preparar_ambiente.sh
exec .java-env/runtime/bin/java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp .java-env/classes TestCompilador
