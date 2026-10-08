package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Constants;

import java.util.List;

/**
 * Оформление заказа: шаг 1 (данные), шаг 2 (обзор) и завершение.
 *
 * <p>Один класс обслуживает три страницы чекаута, объединённые
 * общей навигацией (Continue → Finish).</p>
 */
public class CheckoutPage extends BasePage {

    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By FINISH_BUTTON = By.id("finish");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    private static final By SUBTOTAL_LABEL = By.cssSelector(".summary_subtotal_label");
    private static final By TOTAL_LABEL = By.cssSelector(".summary_total_label");
    // на шаге обзора класс названия отличается от каталога/корзины
    private static final By OVERVIEW_ITEM_NAME =
            By.cssSelector(".cart_item_name, [data-test='inventory-item-name']");
    private static final By COMPLETE_HEADER = By.cssSelector(".complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // --- Шаг 1: данные покупателя ---

    /** Явное ожидание: открылся шаг 1 чекаута. */
    public CheckoutPage waitStepOneLoaded() {
        waitForPath(Constants.CHECKOUT_STEP_ONE_PATH);
        visible(FIRST_NAME);
        return this;
    }

    /** Заполнить форму доставки (имя, фамилия, индекс). */
    public CheckoutPage fillInformation(String firstName, String lastName, String postalCode) {
        enter(FIRST_NAME, firstName);
        enter(LAST_NAME, lastName);
        enter(POSTAL_CODE, postalCode);
        return this;
    }

    private void enter(By locator, String value) {
        WebElement field = visible(locator);
        field.clear();
        field.sendKeys(value);
    }

    /** Нажать Continue. */
    public CheckoutPage submitContinue() {
        clickable(CONTINUE_BUTTON).click();
        return this;
    }

    /** Текст сообщения об ошибке валидации. */
    public String errorText() {
        return visible(ERROR_MESSAGE).getText().trim();
    }

    // --- Шаг 2: обзор заказа ---

    /** Явное ожидание: открылся шаг 2 (обзор заказа). */
    public CheckoutPage waitStepTwoLoaded() {
        waitForPath(Constants.CHECKOUT_STEP_TWO_PATH);
        visible(SUBTOTAL_LABEL);
        return this;
    }

    /** Строка «Item total: …». */
    public String subtotalText() {
        return visible(SUBTOTAL_LABEL).getText().trim();
    }

    /** Строка «Total: …» (итог с учётом налога). */
    public String totalText() {
        return visible(TOTAL_LABEL).getText().trim();
    }

    /** Названия товаров в обзоре заказа. */
    public List<String> overviewItemNames() {
        return driver.findElements(OVERVIEW_ITEM_NAME).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    /** Нажать Finish — завершить оформление. */
    public CheckoutPage finish() {
        clickable(FINISH_BUTTON).click();
        return this;
    }

    // --- Завершение заказа ---

    /** Явное ожидание: открылась страница успешного заказа. */
    public CheckoutPage waitCompleteLoaded() {
        waitForPath(Constants.CHECKOUT_COMPLETE_PATH);
        visible(COMPLETE_HEADER);
        return this;
    }

    /** Заголовок-подтверждение «Thank you for your order!». */
    public String completeHeaderText() {
        return visible(COMPLETE_HEADER).getText().trim();
    }
}
