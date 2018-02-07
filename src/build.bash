#!/bin/bash
set -e -x
echo "Building..."
javac *.java
echo "Done!"
echo "Executing.."
java GUI main