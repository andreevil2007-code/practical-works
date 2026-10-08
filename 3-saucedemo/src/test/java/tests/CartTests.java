package tests;

import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.InventoryPage;
import utils.Constants;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты корзины: добавление товара (состав и счётчик)
 * и удаление товара (корзина опустевает).
 */
class CartTests extends TestBase {

    /** Позитив: добавление товара — счётчик «1», товар и цена в корзине. */
    @Test
    void addToCartShowsBadgeAndItemInCart() {
        InventoryPage inventory = loginAsStandardUser()
                .addToCart(Constants.BACKPACK_ID);

        assertEquals("1", inventory.badgeText(),
                "Счётчик корзины после добавления одного товара");

        CartPage cart = inventory.openCart().waitLoaded();

        assertEquals("Your Cart", cart.title(),
                "Заголовок страницы корзины");
        assertEquals(List.of(Constants.BACKPACK_NAME), cart.itemNames(),
                "Состав корзины (название товара)");
        assertEquals(List.of(Constants.BACKPACK_PRICE), cart.itemPrices(),
                "Цена товара в корзине");
    }

    /** Позитив: удаление товара — корзина пуста, счётчик исчезает. */
    @Test
    void removeFromCartEmptiesCart() {
        CartPage cart = loginAsStandardUser()
                .addToCart(Constants.BACKPACK_ID)
                .openCart()
                .waitLoaded();

        cart.remove(Constants.BACKPACK_ID);

        assertTrue(cart.itemNames().isEmpty(),
                "Корзина пуста после удаления единственного товара");
        assertFalse(cart.isBadgeDisplayed(),
                "Счётчик корзины исчез после удаления последнего товара");
    }
}
