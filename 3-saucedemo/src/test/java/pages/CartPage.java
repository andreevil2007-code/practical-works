package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Constants;

import java.util.List;

/**
 * Страница корзины <b>/cart.html</b> («Your Cart»).
 */
public class CartPage extends BasePage {

    private static final By TITLE = By.cssSelector(".title");
    private static final By ITEM_NAME = By.cssSelector(".cart_list .inventory_item_name");
    private static final By ITEM_PRICE = By.cssSelector(".cart_list .inventory_item_price");
    private static final By CART_BADGE = By.cssSelector(".shopping_cart_badge");
    private static final By CHECKOUT_BUTTON = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /** Явное ожидание: переход в корзину + видимый заголовок. */
    public CartPage waitLoaded() {
        waitForPath(Constants.CART_PATH);
        visible(TITLE);
        return this;
    }

    /** Заголовок страницы корзины. */
    public String title() {
        return visible(TITLE).getText().trim();
    }

    /** Названия товаров в корзине. */
    public List<String> itemNames() {
        return driver.findElements(ITEM_NAME).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    /** Цены товаров в корзине. */
    public List<String> itemPrices() {
        return driver.findElements(ITEM_PRICE).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    /** Удалить товар из корзины по его id. */
    public CartPage remove(String productId) {
        By removeButton = By.id("remove-" + productId);
        clickable(removeButton).click();
        // после удаления кнопка исчезает вместе с позицией товара
        wait.until(ExpectedConditions.invisibilityOfElementLocated(removeButton));
        return this;
    }

    /** Отображается ли счётчик корзины в шапке. */
    public boolean isBadgeDisplayed() {
        return driver.findElements(CART_BADGE).stream().anyMatch(WebElement::isDisplayed);
    }

    /** Перейти к оформлению заказа. */
    public CheckoutPage openCheckout() {
        clickable(CHECKOUT_BUTTON).click();
        return new CheckoutPage(driver);
    }
}
