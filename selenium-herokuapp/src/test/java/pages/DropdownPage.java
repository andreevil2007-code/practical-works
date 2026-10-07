package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Страница Dropdown — https://the-internet.herokuapp.com/dropdown
 *
 * <p>Работа через встроенный класс Selenium {@link Select}.</p>
 */
public class DropdownPage extends BasePage {

    // Локатор из задания
    private static final By DROPDOWN = By.id("dropdown");

    public DropdownPage(WebDriver driver) {
        super(driver);
    }

    public DropdownPage open() {
        open("/dropdown");
        return this;
    }

    private Select select() {
        return new Select(visible(DROPDOWN));
    }

    /** Тексты всех пунктов списка. */
    public List<String> options() {
        return select().getOptions().stream()
                .map(o -> o.getText().trim())
                .toList();
    }

    /** Выбор пункта по индексу (0 — служебный пункт "Please select an option"). */
    public void selectByIndex(int index) {
        select().selectByIndex(index);
    }

    /** Выбор пункта по видимому тексту. */
    public void selectByVisibleText(String text) {
        select().selectByVisibleText(text);
    }

    /** Текст выбранного пункта. */
    public String selectedText() {
        return select().getFirstSelectedOption().getText().trim();
    }

    /** Значение атрибута value выбранного пункта. */
    public String selectedValue() {
        return select().getFirstSelectedOption().getAttribute("value");
    }
}
