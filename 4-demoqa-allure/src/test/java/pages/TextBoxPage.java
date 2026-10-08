package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Страница Elements → Text Box (demoqa.com/text-box).
 *
 * <p>Обратите внимание: на странице два элемента с id {@code currentAddress}
 * и {@code permanentAddress} (поле ввода и блок результата) — поэтому
 * локаторы блока результата скоупятся через {@code #output}.</p>
 */
public class TextBoxPage extends BasePage {

    private static final By USER_NAME = By.id("userName");
    private static final By USER_EMAIL = By.id("userEmail");
    private static final By CURRENT_ADDRESS = By.id("currentAddress");
    private static final By PERMANENT_ADDRESS = By.id("permanentAddress");
    private static final By SUBMIT = By.id("submit");

    private static final By OUTPUT = By.id("output");
    private static final By OUTPUT_NAME = By.cssSelector("#output #name");
    private static final By OUTPUT_EMAIL = By.cssSelector("#output #email");

    public TextBoxPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public TextBoxPage open() {
        open("/text-box", "Text Box");
        return this;
    }

    /** Заполнить форму и нажать Submit (шаг Allure). */
    public TextBoxPage fill(String name, String email, String currentAddr, String permanentAddr) {
        Allure.step("Заполнить форму (name=" + name + ", email=" + email + ") и нажать Submit");
        type(USER_NAME, name);
        type(USER_EMAIL, email);
        type(CURRENT_ADDRESS, currentAddr);
        type(PERMANENT_ADDRESS, permanentAddr);
        click(SUBMIT);
        return this;
    }

    /** Явное ожидание появления блока результата. */
    public TextBoxPage waitOutputVisible() {
        el(OUTPUT);
        return this;
    }

    /** Отображается ли блок результата (без ожидания — для негативных проверок). */
    public boolean isOutputVisible() {
        return driver.findElements(OUTPUT).stream().anyMatch(e -> e.isDisplayed());
    }

    /** Строка «Name:…» в блоке результата. */
    public String outputName() {
        return el(OUTPUT_NAME).getText().trim();
    }

    /** Строка «Email:…» в блоке результата. */
    public String outputEmail() {
        return el(OUTPUT_EMAIL).getText().trim();
    }
}
