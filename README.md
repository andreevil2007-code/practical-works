# practical-works

Учебные практические работы 4 курса (тестирование ПО).

## Практическая №2 — UI-автотесты «HerokuApp»

Автоматизация тестов для сайта <https://the-internet.herokuapp.com>
на **Java 17 + Selenium 4 + JUnit 5 + Maven**, паттерн **Page Object**.

### Структура

```
selenium-herokuapp/
├── pom.xml
└── src/test/java/
    ├── pages/     Page Object для 8 страниц сайта
    │   ├── BasePage.java
    │   ├── AddRemoveElementsPage.java
    │   ├── CheckboxesPage.java
    │   ├── DropdownPage.java
    │   ├── InputsPage.java
    │   ├── TyposPage.java
    │   ├── SortableDataTablesPage.java
    │   ├── HoversPage.java
    │   └── NotificationMessagesPage.java
    └── tests/     тест-классы (по одному на страницу)
        ├── TestBase.java            базовый класс: инициализация/утилизация драйвера
        ├── AddRemoveElementsTest.java
        ├── CheckboxesTest.java
        ├── DropdownTest.java
        ├── InputsTest.java
        ├── TyposTest.java
        ├── SortableDataTablesTest.java
        ├── HoversTest.java
        └── NotificationMessagesTest.java
```

### Запуск

```bash
cd selenium-herokuapp
mvn clean test          # обычный режим (откроется Chrome)
mvn test -Dheadless=true  # без окна браузера (для CI)
```

Отчёты Surefire: `selenium-herokuapp/target/surefire-reports/`.

### Требования

* JDK 17+ (проверка: `java -version`)
* Maven 3.9+ (проверка: `mvn -version`)
* Google Chrome — chromedriver подбирается автоматически (Selenium Manager)

### Ветка и Pull Request

Ветка работ: `feature/ИСП9-48ВБ_Андреев_selenium`, все изменения — через PR в `master`.
