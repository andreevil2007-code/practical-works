package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

import java.time.Duration;

/**
 * Страница Widgets → Progress Bar (demoqa.com/progress-bar).
 *
 * <p>Прогресс-бар — элемент {@code [role=progressbar]} с атрибутом
 * {@code aria-valuenow}; при завершении он достигает 100, а кнопка
 * Start/Stop исчезает. Явное ожидание — по сути шага (дождаться 100%).</p>
 */
public class ProgressBarPage extends BasePage {

    private static final By START_BUTTON = By.id("startStopButton");
    private static final By PROGRESS_BAR = By.cssSelector("[role='progressbar']");

    public ProgressBarPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public ProgressBarPage open() {
        open("/progress-bar", "Progress Bar");
        return this;
    }

    /** Запустить прогресс-бар кнопкой Start. */
    public ProgressBarPage start() {
        Allure.step("Запустить прогресс-бар кнопкой Start");
        click(START_BUTTON);
        return this;
    }

    /** Явное ожидание: прогресс-бар дошёл до 100% (отдельный таймаут — до 45 с). */
    public ProgressBarPage waitUntilComplete() {
        Allure.step("Дождаться завершения прогресса (aria-valuenow = 100)");
        new WebDriverWait(driver, Duration.ofSeconds(Constants.PROGRESS_TIMEOUT_SECONDS))
                .until(d -> "100".equals(d.findElement(PROGRESS_BAR).getAttribute("aria-valuenow")));
        return this;
    }

    /** Текущее значение прогресса (aria-valuenow), например «100». */
    public String progressValue() {
        return driver.findElement(PROGRESS_BAR).getAttribute("aria-valuenow");
    }

    /** Текст внутри прогресс-бара, например «100%». */
    public String progressText() {
        return el(PROGRESS_BAR).getText().trim();
    }

    /** Отображается ли кнопка Start/Stop (при завершении исчезает). */
    public boolean isControlButtonDisplayed() {
        return driver.findElements(START_BUTTON).stream().anyMatch(e -> e.isDisplayed());
    }
}
