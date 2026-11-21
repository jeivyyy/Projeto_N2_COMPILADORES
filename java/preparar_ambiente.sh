#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
command -v javac >/dev/null || { echo "É necessário JDK 17+" >&2; exit 1; }
command -v jlink >/dev/null || { echo "É necessário jlink (JDK 17+)" >&2; exit 1; }
mkdir -p .java-env/classes
if [[ ! -x .java-env/runtime/bin/java ]]; then
  jlink --add-modules java.base,jdk.localedata --strip-debug --no-header-files --no-man-pages --output .java-env/runtime
fi
javac -encoding UTF-8 -d .java-env/classes ./*.java
.java-env/runtime/bin/java -version
