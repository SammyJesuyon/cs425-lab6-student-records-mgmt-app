#!/usr/bin/env bash
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
"$PROJECT_DIR/build.sh" >/dev/null

java -cp "$PROJECT_DIR/target/classes:$PROJECT_DIR/target/test-classes" \
  edu.mum.cs.cs425.demos.studentrecordsmgmtapp.Lab6SelfTest
