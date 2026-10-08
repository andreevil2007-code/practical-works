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

## Практическая №3 — UI-автотесты «Swag Labs»

Автоматизация тестов для сайта <https://www.saucedemo.com/>
на **Java 17 + Selenium 4 + JUnit 5 + Maven**, паттерн **Page Object**.

### Структура

```
3-saucedemo/
├── pom.xml
└── src/test/java/
    ├── utils/Constants.java    демо-учётки, ожидаемые тексты, таймауты
    ├── pages/                  Page Object (4 страницы + BasePage)
    │   ├── BasePage.java
    │   ├── LoginPage.java
    │   ├── InventoryPage.java
    │   ├── CartPage.java
    │   └── CheckoutPage.java
    └── tests/                  9 тестов: вход, корзина, чекаут, сортировки
        ├── TestBase.java       драйвер, скриншоты, driver.quit()
        ├── LoginTests.java     1 позитив + 2 негативных
        ├── CartTests.java      добавление/удаление товара
        ├── CheckoutTests.java  полный заказ + валидация пустого поля
        └── SortingTests.java   сортировка по названию и цене
```

### Запуск

```bash
cd 3-saucedemo
mvn clean test            # обычный режим (откроется Chrome)
mvn test -Dheadless=true  # без окна браузера
```

Отчёты Surefire: `3-saucedemo/target/surefire-reports/`,
скриншоты: `3-saucedemo/target/screenshots/`.

### Ветка и Pull Request

Ветка работ: `feature/ИСП9-48ВБ_Андреев_saucedemo`, все изменения — через PR в `master`.
