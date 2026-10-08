package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

/**
 * Базовый класс Page Object: хранит WebDriver, явные ожидания
 * и общие обёртки действий (el/click/type) для всех страниц DemoQA.
 *
 * <p>Конструктор принимает driver и wait от {@code BaseTest} —
 * единая точка синхронизации всего тестового набора.</p>
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /** Явное ожидание: элемент появился и стал видимым. */
    protected WebElement el(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Явное ожидание: элемент стал кликабельным, затем клик. */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /** Очистка поля и ввод текста (явное ожидание видимости). */
    protected void type(By locator, String text) {
        WebElement field = el(locator);
        field.clear();
        field.sendKeys(text);
    }

    /** Открыть страницу по относительному пути (фиксируется как шаг Allure). */
    protected void open(String path, String title) {
        Allure.step("Открыть страницу " + title);
        driver.get(Constants.BASE_URL + path);
        waitForPath(path);
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
