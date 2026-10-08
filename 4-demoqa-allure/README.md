# Практическая №4 — Allure и кроссбраузерные автотесты DemoQA

Автотесты для <https://demoqa.com> на **Java 17 + Selenium 4 + TestNG + Maven**
с отчётностью **Allure** и кроссбраузерным прогоном **Chrome / Edge**.

## Что тестируется

| # | Раздел DemoQA | Сценарий | Тип | Класс |
|---|---------------|----------|-----|-------|
| 1 | Elements → Text Box | валидные данные → блок результата | позитив | `TextBoxTest` |
| 2 | Elements → Text Box | невалидный email блокирует отправку | негатив | `TextBoxTest` |
| 3 | Elements → Check Box | отметка Desktop → список выбранных | позитив | `CheckBoxTest` |
| 4 | Elements → Check Box | снятие чекбокса → результата нет | негатив | `CheckBoxTest` |
| 5 | Elements → Radio Button | выбор Yes → «You have selected Yes» | позитив | `RadioButtonTest` |
| 6 | Elements → Radio Button | кнопка No недоступна (disabled) | негатив | `RadioButtonTest` |
| 7 | Alerts, Frame & Windows → Alerts | confirm + Cancel → «You selected Cancel» | позитив | `AlertsTest` |
| 8 | Alerts, Frame & Windows → Alerts | prompt + текст → «You entered …» | позитив | `AlertsTest` |
| 9 | Alerts, Frame & Windows → Frames | чтение iframe + возврат контекста | позитив | `FramesTest` |
| 10 | Widgets → Progress Bar | прогресс до 100%, кнопка исчезает | позитив | `ProgressBarTest` |
| 11 | Interactions → Selectable | выбор элемента (класс active) | позитив | `SelectableTest` |

**11 тестов** (8 позитивных + 3 негативных) × 2 браузера = 22 проверки за прогон.

## Структура

```
4-demoqa-allure/
├── pom.xml                          TestNG + allure-testng + allure-maven
└── src/test/
    ├── java/
    │   ├── utils/
    │   │   ├── Constants.java       URL, тестовые данные, таймауты
    │   │   ├── DriverFactory.java   кроссбраузерная фабрика драйверов
    │   │   └── AllureAttachments.java  скриншоты/исходник при падении
    │   ├── pages/                   Page Object (7 страниц + BasePage)
    │   │   ├── BasePage.java        явные ожидания, el/click/type, шаги Allure
    │   │   ├── TextBoxPage.java     CheckBoxPage.java   RadioButtonPage.java
    │   │   ├── AlertsPage.java      FramesPage.java
    │   │   ├── ProgressBarPage.java SelectablePage.java
    │   └── tests/
    │       ├── BaseTest.java        драйвер/браузер, артефакты, environment
    │       └── *Test.java           7 тест-классов (11 тестов)
    └── resources/
        ├── testng.xml               Chrome + Edge, parallel="tests"
        ├── allure.properties        allure.results.directory
        ├── environment.properties   блок Environment отчёта
        └── categories.json          классификация падений
```

## Запуск

```bash
cd 4-demoqa-allure

# 1. Кроссбраузерный прогон (по умолчанию: Chrome + Edge, параллельно, headless)
mvn clean test

# 2. Один браузер без testng.xml (профиль crossbrowser отключается)
mvn test -P"!crossbrowser" -Dbrowser=chrome -Dheadless=true

# 3. Отчёт Allure (HTML → target/site/allure-maven/index.html)
mvn allure:report

# 4. Отчёт с локальным сервером (Ctrl+C — остановить)
mvn allure:serve
```

Артефакты:

* отчёты Surefire — `target/surefire-reports/`;
* результаты Allure (JSON, вложения, environment, categories) — `target/allure-results/`;
* HTML-отчёт Allure — `target/site/allure-maven/`.

## Что интегрировано

* **Шаги Allure** — `Allure.step(...)` внутри Page Object (один бизнес-шаг = один метод);
* **Метки** — `@Epic`, `@Feature`, `@Story`, `@Severity`, `@Owner`, `@Link` (репозиторий);
* **Вложения** — скриншот + HTML-исходник страницы при падении теста;
* **Environment** — `environment.properties` копируется в результаты после прогона;
* **Классификация падений** — `categories.json` (утверждения / таймауты / элементы);
* **Кроссбраузерность** — параметры `browser`/`headless` из testng.xml или `-D`-свойств,
  параллельный прогон `parallel="tests"`.

## Требования

* JDK 17+ (`java -version`)
* Maven 3.9+ (`mvn -version`)
* Google Chrome и/или Microsoft Edge — драйверы подбираются Selenium Manager
