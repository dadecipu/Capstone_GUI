#!/bin/bash
set -e -x
echo "Building..."
javac *.java -d bin
echo "Done!"
echo "Executing.."
java GUI main
