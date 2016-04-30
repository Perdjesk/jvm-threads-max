#!/bin/bash
set -v

uname -a

java -version

ulimit -H -a

ulimit -S -a

sysctl kernel.threads-max

sysctl kernel.pid_max

sysctl vm.max_map_count

javac ThreadCreator.java
java ThreadCreator
