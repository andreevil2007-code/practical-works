package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Страница Alerts, Frame & Windows → Alerts (demoqa.com/alerts).
 *
 * <p>Работа с нативными JS-диалогами: после клика диалог ловится
 * явным ожиданием {@code alertIsPresent}. Тексты результатов
 * («You selected Cancel», «You entered …») проверены по бандлу сайта.</p>
 */
public class AlertsPage extends BasePage {

    private static final By CONFIRM_BUTTON = By.id("confirmButton");
    private static final By PROMPT_BUTTON = By.id("promtButton");
    private static final By CONFIRM_RESULT = By.id("confirmResult");
    private static final By PROMPT_RESULT = By.id("promptResult");

    public AlertsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public AlertsPage open() {
        open("/alerts", "Alerts");
        return this;
    }

    /** Открыть confirm-диалог и отклонить его (Cancel). */
    public AlertsPage openConfirmAndDismiss() {
        Allure.step("Открыть confirm-диалог и нажать Cancel");
        click(CONFIRM_BUTTON);
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
        return this;
    }

    /** Открыть prompt-диалог, ввести текст и принять его. */
    public AlertsPage openPromptAndSend(String text) {
        Allure.step("Открыть prompt-диалог, ввести текст и принять");
        click(PROMPT_BUTTON);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
        alert.accept();
        return this;
    }

    /** Результат confirm-диалога, например «You selected Cancel». */
    public String confirmText() {
        return el(CONFIRM_RESULT).getText().trim();
    }

    /** Результат prompt-диалога, например «You entered demo». */
    public String promptText() {
        return el(PROMPT_RESULT).getText().trim();
    }
}
