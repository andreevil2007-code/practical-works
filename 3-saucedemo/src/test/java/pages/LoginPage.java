package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Constants;

/**
 * Страница входа <a href="https://www.saucedemo.com/">saucedemo.com</a>.
 *
 * <p>Локаторы построены на стабильных id/data-test атрибутах,
 * которые предоставляет само приложение.</p>
 */
public class LoginPage extends BasePage {

    private static final By USERNAME = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Открыть страницу входа. */
    public LoginPage open() {
        driver.get(Constants.BASE_URL);
        visible(USERNAME);
        return this;
    }

    /** Ввести логин. */
    public LoginPage enterUsername(String username) {
        WebElement field = visible(USERNAME);
        field.clear();
        field.sendKeys(username);
        return this;
    }

    /** Ввести пароль. */
    public LoginPage enterPassword(String password) {
        WebElement field = visible(PASSWORD);
        field.clear();
        field.sendKeys(password);
        return this;
    }

    /** Нажать кнопку Login. */
    public LoginPage submit() {
        clickable(LOGIN_BUTTON).click();
        return this;
    }

    /** Ввести логин и пароль (без нажатия кнопки — для негативных сценариев). */
    public LoginPage enterCredentials(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return this;
    }

    /**
     * Выполнить вход: логин, пароль, кнопка.
     *
     * @return страница-обёртка каталога; перед использованием вызвать
     *         {@link InventoryPage#waitLoaded()} — переход асинхронный
     */
    public InventoryPage loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        submit();
        return new InventoryPage(driver);
    }

    /** Отображается ли блок сообщения об ошибке входа. */
    public boolean isErrorVisible() {
        return wait.until(d -> d.findElements(ERROR_MESSAGE).stream()
                .anyMatch(WebElement::isDisplayed));
    }

    /** Текст сообщения об ошибке входа. */
    public String errorText() {
        return visible(ERROR_MESSAGE).getText().trim();
    }

    /** Проверка: пользователь остался на странице входа. */
    public boolean isOnLoginPage() {
        return currentUrl().endsWith("/");
    }
}
