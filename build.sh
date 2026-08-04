#!/usr/bin/env bash
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
MAIN_OUT="$PROJECT_DIR/target/classes"
TEST_OUT="$PROJECT_DIR/target/test-classes"

mkdir -p "$MAIN_OUT" "$TEST_OUT"

find "$PROJECT_DIR/src/main/java" -name '*.java' -print0 \
  | xargs -0 javac --release 8 -Xlint:-options -d "$MAIN_OUT"

find "$PROJECT_DIR/src/test/java" -name '*.java' -print0 \
  | xargs -0 javac --release 8 -Xlint:-options -cp "$MAIN_OUT" -d "$TEST_OUT"

echo "Build complete (Java 8 bytecode target)."
