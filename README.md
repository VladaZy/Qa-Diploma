## Документация

- План автоматизации тестирования [Plan.md](documents%2FPlan.md)
- Отчётные документы по итогам тестирования [Report.md](documents%2FReport.md)
- Отчётные документы по итогам автоматизации [Summary.md](documents%2FSummary.md)

Процедура запуска авто-тестов
---

***
Перед запуском авто-тестов необходимо установить:

- IntelliJ IDEA
- [Docker Desktop](https://www.docker.com/products/docker-desktop/ "Docker Desktop")
- Google Chrome или другой браузер
- GitHub

Запуск авто-тестов:
--- 
****

1. Запустить контейнеры СУБД MySQl, PostgerSQL и Node.js командой в терминале:

> docker-compose -f docker-compose.yml up

2. Запустить SUT в терминале при помощи команды:

- для MySQL:

> java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

- для PostgreSQL:

> java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

3. Убедиться в готовности системы. Приложение должно быть доступно по адресу:

> http://localhost:8080/

4. Запуск тестов:

- для MySQL:

> ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"

- для PostgreSQL:

> ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

5. Для генерация отчёта Allure Report по результатам тестирования и автоматическое открытие отчета в браузере по умолчанию.

> ./gradlew allureServe

Для остановки приложения в окне терминала нужно ввести команду Ctrl+С и повторить необходимые действия из предыдущих разделов.