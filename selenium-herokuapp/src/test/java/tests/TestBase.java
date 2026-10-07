package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;

/**
 * Базовый класс теста — жизненный цикл драйвера.
 *
 * <p>{@code @BeforeEach} создаёт ChromeDriver на каждый тест,
 * {@code @AfterEach} гарантирует {@code driver.quit()} в любом исходе
 * (в том числе при падении теста) — утечек браузеров нет.</p>
 *
 * <p>Версия chromedriver подбирается автоматически средствами
 * Selenium Manager (встроен в Selenium 4.6+), WebDriverManager не нужен.</p>
 */
public abstract class TestBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        // headless-режим включается ключом: mvn test -Dheadless=true
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1600,1000", "--disable-gpu");

        driver = new ChromeDriver(options);
        // Запас на возможные сетевые стопы (эпизоды ~30 с фиксировались на маршруте к сайту)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /** Базовый URL приложения (для проверок переходов). */
    protected String baseUrl() {
        return BasePage.BASE_URL;
    }
}
