# ProgLab8
## БД
Используется Postgresql. Скрипт создания таблиц в createTableScripts.
## Build
mvn package
## Запуск клиента
java -jar target/prog-lab-8-client.jar
## Запуск сервера
В папке, где происходит запуск, должен лежать database.txt, где:
* Первая строка - url бд
* Вторая - логин
* Третья - пароль

java -jar target/prog-lab-8-server.jar