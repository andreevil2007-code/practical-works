package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.AddRemoveElementsPage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест-класс для страницы Add/Remove Elements.
 */
class AddRemoveElementsTest extends TestBase {

    private static final By DELETE_BUTTON = By.xpath("//button[text()='Delete']");

    @Test
    @DisplayName("Позитив: добавлено 2 элемента, удалён 1 — счётчик 0 → 2 → 1")
    void addTwoElementsAndDeleteOne() {
        AddRemoveElementsPage page = new AddRemoveElementsPage(driver).open();

        assertEquals(0, page.count(), "Изначально добавленных элементов быть не должно");

        page.addElements(2);
        page.waitCount(2);
        assertEquals(2, page.count(), "После двух кликов Add Element должно стать 2 элемента");

        page.deleteFirstElement();
        page.waitCount(1);
        assertEquals(1, page.count(), "После удаления одного элемента должен остаться 1");
    }

    @Test
    @DisplayName("Негатив: после удаления всех элементов кнопка Delete исчезает")
    void deleteButtonDisappearsWhenAllElementsRemoved() {
        AddRemoveElementsPage page = new AddRemoveElementsPage(driver).open();

        page.addElements(2);
        page.waitCount(2);

        page.deleteFirstElement();
        page.deleteFirstElement();
        page.waitCount(0);

        assertTrue(driver.findElements(DELETE_BUTTON).isEmpty(),
                "После удаления всех элементов кнопка Delete не должна отображаться");
    }
}
