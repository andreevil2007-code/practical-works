package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CheckboxesPage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест-класс для страницы Checkboxes.
 */
class CheckboxesTest extends TestBase {

    @Test
    @DisplayName("Позитив: первый чекбокс не отмечен → после клика отмечен")
    void firstCheckboxStartsUncheckedAndBecomesChecked() {
        CheckboxesPage page = new CheckboxesPage(driver).open();
        page.waitForCount(2);

        assertFalse(page.isChecked(0), "Первый чекбокс по умолчанию не должен быть отмечен");

        page.toggle(0);
        assertTrue(page.isChecked(0), "После клика первый чекбокс должен стать отмеченным");
    }

    @Test
    @DisplayName("Позитив: второй чекбокс отмечен → после клика снят")
    void secondCheckboxStartsCheckedAndBecomesUnchecked() {
        CheckboxesPage page = new CheckboxesPage(driver).open();
        page.waitForCount(2);

        assertTrue(page.isChecked(1), "Второй чекбокс по умолчанию должен быть отмечен");

        page.toggle(1);
        assertFalse(page.isChecked(1), "После клика второй чекбокс должен стать неотмеченным");
    }
}
