package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Базовый класс Page Object: хранит WebDriver, явные ожидания
 * и общие хелперы для всех страниц.
 */
public abstract class BasePage {

    /** Базовый URL тестируемого приложения. */
    public static final String BASE_URL = "https://the-internet.herokuapp.com";

    /** Таймаут явных ожиданий. */
    protected static final Duration TIMEOUT = Duration.ofSeconds(15);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    /** Явное ожидание: элемент появился и стал видимым. */
    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Явное ожидание: элемент стал кликабельным. */
    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** Переход на страницу по относительному пути. */
    protected void open(String path) {
        driver.get(BASE_URL + path);
    }
}
