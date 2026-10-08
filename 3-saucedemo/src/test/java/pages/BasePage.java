package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

import java.time.Duration;

/**
 * Базовый класс Page Object: хранит WebDriver, явные ожидания
 * и общие хелперы для всех страниц saucedemo.com.
 */
public abstract class BasePage {

    /** Явные ожидания (общий таймаут для всех страниц). */
    protected static final Duration TIMEOUT = Duration.ofSeconds(Constants.EXPLICIT_WAIT_SECONDS);

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

    /** Явное ожидание: URL содержит фрагмент пути. */
    protected void waitForPath(String path) {
        wait.until(ExpectedConditions.urlContains(path));
    }

    /** Текущий URL браузера (для проверок переходов). */
    protected String currentUrl() {
        return driver.getCurrentUrl();
    }
}
