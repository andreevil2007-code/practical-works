package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Страница Elements → Radio Button (demoqa.com/radio-button).
 *
 * <p>Радиокнопка «No» на сайте намеренно отключена (disabled) —
 * это используется в негативном сценарии.</p>
 */
public class RadioButtonPage extends BasePage {

    private static final By YES_RADIO = By.id("yesRadio");
    private static final By NO_RADIO = By.id("noRadio");
    private static final By RESULT = By.xpath("//p[contains(., 'You have selected')]");

    public RadioButtonPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public RadioButtonPage open() {
        open("/radio-button", "Radio Button");
        return this;
    }

    /** Выбрать радиокнопку Yes. */
    public RadioButtonPage selectYes() {
        Allure.step("Выбрать радиокнопку Yes");
        click(YES_RADIO);
        return this;
    }

    /** Включена ли радиокнопка No (на сайте — нет). */
    public boolean isNoEnabled() {
        return el(NO_RADIO).isEnabled();
    }

    /** Явное ожидание появления строки результата. */
    public RadioButtonPage waitResultVisible() {
        el(RESULT);
        return this;
    }

    /** Текст результата, например «You have selected Yes». */
    public String resultText() {
        return el(RESULT).getText().trim();
    }
}
