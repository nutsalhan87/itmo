```
+---------------------+
|,--.,--.,--.         |
||  |`--'|  |-.  ,---.|
||  |,--.| .-. '| .--'|
||  ||  || `-' |\ `--.|
|`--'`--' `---'  `---'|
+---------------------+
```

# Перейдем к libc

Стандартная библиотека языка C описывает заголовочные файлы, которые заключают в себе различные функции, константы и макросы.

Однако это всего лишь спецификация, единой реализации нет. Некоторые реализации: glibc, musl, dietlibc, bsd libc, newlib, bionic, uClibc-ng, picolibc.
Самым распространенным на Linux системах является glibc - GNU libc.

Когда вы компилируете программу на си, то компилируется именно ваш код. В бинарном файле нет инструкций, реализующих libc. Программа просто линкуется с уже существующей в системе реализацией libc. Если бы это было не так, то программы на си весили бы попросту больше.