Проект test создан с использованием:<br>
<br>
• Spring Boot и Data JPA;<br>
• project Lombok;<br>
• openapi codegen, для генерации интерфейсов rest контроллеров и dto классов по контракту, описанному в формате
yaml;<br>
• mapstruct для генерации маппер классов;<br>
• Postgresql используется в качестве драйвера бд;<br>
• liquibase для версионирования скриптов миграции бд.<br>
• Все эндпоинты описаны в файле openapi.yml по пути /resources/swagger/<br>
<br>
Для запуска проекта необходимо убедиться что:<br>

1) проект открыт в IntelliJ IDEA
2) установлен дистрибутив PostgreSQL >=15
3) в application.properties поменять url, username и password на свои
4) выполнить maven команду mvn clean install, для генерации контроллеров, dto и маппер классов, помимо этого будут также
   запущены тесты
5) пометить папку target/generated-sources/openapi/src/main/java как sources root(пкм => mark directory as...), если она
   не почемена<br>
6) запустить проект