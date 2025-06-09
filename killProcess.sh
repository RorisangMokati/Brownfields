#!/bin/bash

PROCESS_NAME="libs/reference-server-0.2.3.jar"

# Get the process ID(s) of the given process name
# shellcheck disable=SC2009
PIDS=$(ps aux | grep "$PROCESS_NAME")

# Check if the process is running
if [ -z "$PIDS" ]; then
    echo "No process found with name: $PROCESS_NAME"
    exit 1
fi

# Kill the process(es)
for PID in $PIDS; do
    echo "Killing process with PID: $PID"
    kill "$PID"
done

echo "Process(es) with name $PROCESS_NAME terminated."
