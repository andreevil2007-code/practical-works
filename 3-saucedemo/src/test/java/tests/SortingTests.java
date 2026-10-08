package tests;

import org.junit.jupiter.api.Test;
import pages.InventoryPage;
import utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты сортировки каталога: по названию (A→Z, Z→A)
 * и по цене (low→high, high→low).
 */
class SortingTests extends TestBase {

    /** Ожидаемый порядок названий: A → Z (проверяется против сайта). */
    private static final List<String> ASCENDING_NAMES = List.of(
            "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Fleece Jacket",
            "Sauce Labs Onesie",
            "Test.allTheThings() T-Shirt (Red)");

    /** Позитив: переключение сортировки по названию меняет порядок списка. */
    @Test
    void sortByNameShowsAscendingAndDescendingOrder() {
        InventoryPage inventory = loginAsStandardUser();

        List<String> descending = new ArrayList<>(ASCENDING_NAMES);
        Collections.reverse(descending);

        inventory.selectSortOption("az").waitFirstItemNameIs(ASCENDING_NAMES.get(0));
        assertEquals(ASCENDING_NAMES, inventory.itemNames(),
                "Порядок товаров при сортировке Name (A to Z)");

        inventory.selectSortOption("za").waitFirstItemNameIs(descending.get(0));
        assertEquals(descending, inventory.itemNames(),
                "Порядок товаров при сортировке Name (Z to A)");
    }

    /** Позитив: сортировка по цене даёт монотонную последовательность цен. */
    @Test
    void sortByPriceKeepsOrderAscendingAndDescending() {
        InventoryPage inventory = loginAsStandardUser();

        // дешёвый товар — Sauce Labs Onesie, дорогой — Fleece Jacket
        inventory.selectSortOption("lohi")
                .waitFirstItemPriceIs(Constants.LOWEST_PRICE);
        assertPricesOrdered(inventory.itemPrices(), true, "Price (low to high)");

        inventory.selectSortOption("hilo")
                .waitFirstItemPriceIs(Constants.HIGHEST_PRICE);
        assertPricesOrdered(inventory.itemPrices(), false, "Price (high to low)");
    }

    /** Цены монотонно не убывают (ascending) или не возрастают (descending). */
    private void assertPricesOrdered(List<String> prices, boolean ascending, String sortName) {
        assertTrue(prices.size() >= 2,
                "В каталоге несколько товаров для проверки сортировки по цене");
        for (int i = 1; i < prices.size(); i++) {
            double previous = parsePrice(prices.get(i - 1));
            double current = parsePrice(prices.get(i));
            boolean ordered = ascending ? previous <= current : previous >= current;
            assertTrue(ordered, String.format(
                    "Сортировка %s: цена №%d (%s) должна быть %s цены №%d (%s)",
                    sortName, i + 1, prices.get(i),
                    ascending ? "не меньше" : "не больше",
                    i, prices.get(i - 1)));
        }
    }

    private double parsePrice(String price) {
        return Double.parseDouble(price.replace("$", ""));
    }
}
