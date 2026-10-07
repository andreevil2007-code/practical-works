package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.DropdownPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест-класс для страницы Dropdown.
 */
class DropdownTest extends TestBase {

    @Test
    @DisplayName("Позитив: в списке присутствуют все пункты")
    void dropdownContainsAllOptions() {
        DropdownPage page = new DropdownPage(driver).open();

        List<String> options = page.options();

        assertEquals(3, options.size(), "В списке должно быть 3 пункта");
        assertEquals("Please select an option", options.get(0), "Первый пункт — служебный");
        assertTrue(options.contains("Option 1"), "Нет пункта Option 1");
        assertTrue(options.contains("Option 2"), "Нет пункта Option 2");
    }

    @Test
    @DisplayName("Позитив: выбор первого, затем второго пункта")
    void selectFirstThenSecondOption() {
        DropdownPage page = new DropdownPage(driver).open();

        page.selectByIndex(1);
        assertEquals("Option 1", page.selectedText(), "Должен быть выбран Option 1");
        assertEquals("1", page.selectedValue(), "Значение выбранного пункта — 1");

        page.selectByIndex(2);
        assertEquals("Option 2", page.selectedText(), "Должен быть выбран Option 2");
        assertEquals("2", page.selectedValue(), "Значение выбранного пункта — 2");
    }
}
