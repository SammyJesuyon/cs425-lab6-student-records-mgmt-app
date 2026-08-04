#!/usr/bin/env bash
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
EVIDENCE_DIR="$PROJECT_DIR/evidence"
mkdir -p "$EVIDENCE_DIR"

{
  echo '$ java -version'
  java -version 2>&1
  echo
  echo '$ javac -version'
  javac -version 2>&1
  echo
  echo '$ /usr/libexec/java_home -V'
  /usr/libexec/java_home -V 2>&1
} > "$EVIDENCE_DIR/jdk-environment.txt"

{
  echo '$ ./run.sh students'
  "$PROJECT_DIR/run.sh" students
} > "$EVIDENCE_DIR/student-records-output.txt"

{
  echo '$ ./run.sh practice'
  "$PROJECT_DIR/run.sh" practice
} > "$EVIDENCE_DIR/coding-practice-output.txt"

{
  echo '$ ./test.sh'
  "$PROJECT_DIR/test.sh"
} > "$EVIDENCE_DIR/self-test-output.txt"

echo "Evidence logs written to $EVIDENCE_DIR"
