package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utils.Constants;

import java.util.List;

/**
 * Страница каталога товаров <b>/inventory.html</b> (после успешного входа).
 */
public class InventoryPage extends BasePage {

    private static final By TITLE = By.cssSelector(".title");
    private static final By ITEM_NAME = By.cssSelector(".inventory_item_name");
    private static final By ITEM_PRICE = By.cssSelector(".inventory_item_price");
    private static final By CART_BADGE = By.cssSelector(".shopping_cart_badge");
    private static final By CART_LINK = By.cssSelector(".shopping_cart_link");
    private static final By SORT_SELECT = By.cssSelector(".product_sort_container");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    /** Явное ожидание: переход в каталог после входа + видимый заголовок. */
    public InventoryPage waitLoaded() {
        waitForPath(Constants.INVENTORY_PATH);
        visible(TITLE);
        return this;
    }

    /** Заголовок страницы каталога. */
    public String title() {
        return visible(TITLE).getText().trim();
    }

    /** Названия товаров в текущем порядке отображения. */
    public List<String> itemNames() {
        return driver.findElements(ITEM_NAME).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    /** Цены товаров в текущем порядке отображения. */
    public List<String> itemPrices() {
        return driver.findElements(ITEM_PRICE).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    /** Нажать «Add to cart» у товара с указанным id. */
    public InventoryPage addToCart(String productId) {
        clickable(By.id("add-to-cart-" + productId)).click();
        // после добавления в шапке появляется счётчик корзины
        wait.until(ExpectedConditions.visibilityOfElementLocated(CART_BADGE));
        return this;
    }

    /** Текст счётчика корзины (например, «1»). */
    public String badgeText() {
        return visible(CART_BADGE).getText().trim();
    }

    /** Отображается ли счётчик корзины. */
    public boolean isBadgeDisplayed() {
        return driver.findElements(CART_BADGE).stream().anyMatch(WebElement::isDisplayed);
    }

    /** Перейти в корзину. */
    public CartPage openCart() {
        clickable(CART_LINK).click();
        return new CartPage(driver);
    }

    /** Выбрать опцию сортировки (az, za, lohi, hilo). */
    public InventoryPage selectSortOption(String value) {
        new Select(visible(SORT_SELECT)).selectByValue(value);
        return this;
    }

    /** Явное ожидание: первый товар в списке — с ожидаемым названием. */
    public InventoryPage waitFirstItemNameIs(String expected) {
        wait.until(d -> expected.equals(firstText(ITEM_NAME)));
        return this;
    }

    /** Явное ожидание: цена первого товара в списке — ожидаемая. */
    public InventoryPage waitFirstItemPriceIs(String expected) {
        wait.until(d -> expected.equals(firstText(ITEM_PRICE)));
        return this;
    }

    private String firstText(By locator) {
        List<WebElement> items = driver.findElements(locator);
        return items.isEmpty() ? "" : items.get(0).getText().trim();
    }
}
