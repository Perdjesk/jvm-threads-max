# jvm-threads-max
Java VirtualMachine and maximun threads experiments

# Simple test and report
The following script will print system information and will create the maximum possible threads using the `ThreadCreator` Java program.
```
./report.sh
```

# Thread limiting sources

## ulimit
http://man7.org/linux/man-pages/man5/limits.conf.5.html
http://man7.org/linux/man-pages/man8/pam_limits.8.html
```
ulimit -H -a
ulimit -S -a
```
Options that could limit the number of threads:
- `max user processes              (-u)`
- `virtual memory          (kbytes, -v)`
- `max memory size         (kbytes, -m)`

### Temporarily modify ulimit
```
ulimit -S -u 1000000
ulimit -S -u unlimited
```

### Permanently modify ulimit
```
echo "username - nproc 1000000" >> /etc/security/limits.d/90-nproc.conf
echo "username - unlimited" >> /etc/security/limits.d/90-nproc.conf 

```

## Kernel parameters

```
sysctl kernel.threads-max
sysctl kernel.pid_max
sysctl vm.max_map_count
```
https://kernel.org/doc/Documentation/sysctl/kernel.txt
https://kernel.org/doc/Documentation/sysctl/vm.txt

### Temporarily modify kernel parameters

```
sysctl -w kernel.threads-max=1000000
sysctl -w kernel.pid_max=1000000
sysctl -w vm.max_map_count=1000000
```


### Permanently modify kernel parameters
```
echo "kernel.threads-max=1000000" >> /etc/sysctl.d/99-sysctl.conf
echo "kernel.pid_max=1000000" >> /etc/sysctl.d/99-sysctl.conf
echo "vm.max_map_count=1000000" >> /etc/sysctl.d/99-sysctl.conf
```
By using `sysctl -p`, it possible to load the configuration from the configuration files without rebooting.

# Common error examples

## Cannot allocate memory (mmap)
Caused by `vm.max_map_count` being too low.
```
OpenJDK 64-Bit Server VM warning: INFO: os::commit_memory(0x00007f5148648000, 12288, 0) failed; error='Cannot allocate memory' (errno=12)
#
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (mmap) failed to map 12288 bytes for committing reserved memory.
# An error report file with more information is saved as:
# /home/jon/hs_err_pid29398.log
```

## Unable to create new native thread
Caused by one of the following parameter being too low:
- `kernel.threads-max`
- `kernel.pid_max`
- `ulimit -u`

```
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:714)
	at ThreadCreator.main(ThreadCreator.java:16)
```
