# Практическая №13 — UI-автотесты «Swag Labs» (saucedemo.com)

Автоматизация тестов для сайта <https://www.saucedemo.com/>
на **Java 17 + Selenium 4 + JUnit 5 + Maven**, паттерн **Page Object**.

## Что тестируется

| # | Сценарий | Тип | Класс теста |
|---|----------|-----|-------------|
| 1 | Вход `standard_user` → каталог товаров | позитив | `LoginTests` |
| 2 | Вход заблокированным `locked_out_user` → ошибка | негатив | `LoginTests` |
| 3 | Вход с неверным паролем → ошибка | негатив | `LoginTests` |
| 4 | Добавление товара в корзину (счётчик, название, цена) | позитив | `CartTests` |
| 5 | Удаление товара из корзины (пустая корзина) | позитив | `CartTests` |
| 6 | Полное оформление заказа до «Thank you for your order!» | позитив | `CheckoutTests` |
| 7 | Оформление с пустым обязательным полем First Name | негатив | `CheckoutTests` |
| 8 | Сортировка по названию: A→Z и Z→A | позитив | `SortingTests` |
| 9 | Сортировка по цене: low→high и high→low | позитив | `SortingTests` |

Итого: **9 тестов** (6 позитивных + 3 негативных), 4 Page Object-класса
(`LoginPage`, `InventoryPage`, `CartPage`, `CheckoutPage`) + `BasePage`.

## Структура

```
3-saucedemo/
├── pom.xml
└── src/test/java/
    ├── utils/
    │   └── Constants.java          базовый URL, демо-учётки, ожидаемые тексты
    ├── pages/                      Page Object
    │   ├── BasePage.java           явные ожидания и общие хелперы
    │   ├── LoginPage.java          страница входа
    │   ├── InventoryPage.java      каталог товаров и сортировка
    │   ├── CartPage.java           корзина
    │   └── CheckoutPage.java       оформление заказа (3 шага)
    └── tests/
        ├── TestBase.java           жизненный цикл драйвера, скриншоты, driver.quit()
        ├── LoginTests.java
        ├── CartTests.java
        ├── CheckoutTests.java
        └── SortingTests.java
```

## Запуск

```bash
cd 3-saucedemo
mvn clean test            # обычный режим (откроется Chrome)
mvn test -Dheadless=true  # без окна браузера (для CI/отчёта)
```

Артефакты:

* отчёты Surefire — `target/surefire-reports/`;
* скриншот каждого теста — `target/screenshots/`;
* при падении: URL и HTML-исходник — `target/failures/`.

## Демо-данные Swag Labs

| Логин | Пароль | Назначение |
|-------|--------|------------|
| `standard_user` | `secret_sauce` | штатный вход |
| `locked_out_user` | `secret_sauce` | негативный сценарий блокировки |

## Требования

* JDK 17+ (`java -version`)
* Maven 3.9+ (`mvn -version`)
* Google Chrome — chromedriver подбирается автоматически (Selenium Manager)
