package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import pages.InputsPage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест-класс для страницы Inputs.
 */
class InputsTest extends TestBase {

    @Test
    @DisplayName("Позитив: ввод числа и изменение значения стрелками ↑/↓")
    void numberCanBeEnteredAndChangedWithArrowKeys() {
        InputsPage page = new InputsPage(driver).open();

        page.type("42");
        assertEquals("42", page.value(), "Поле должно содержать введённое число");

        page.press(Keys.ARROW_UP);
        assertEquals("43", page.value(), "ARROW_UP должен увеличить значение на 1");

        page.press(Keys.ARROW_DOWN);
        assertEquals("42", page.value(), "ARROW_DOWN должен уменьшить значение на 1");
    }

    @Test
    @DisplayName("Негатив: нецифровые значения не принимаются полем")
    void nonNumericInputIsRejected() {
        InputsPage page = new InputsPage(driver).open();

        page.type("abc");
        assertEquals("", page.value(), "Поле type=number не должно принимать буквы");

        page.type("10a5");
        assertNotEquals("10a5", page.value(), "Смешанное значение не должно приниматься как есть");
    }
}
