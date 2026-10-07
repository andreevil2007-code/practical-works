package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Страница Checkboxes — https://the-internet.herokuapp.com/checkboxes
 *
 * <p>Согласно заданию используется локатор {@code By.cssSelector("[type=checkbox]")}.</p>
 */
public class CheckboxesPage extends BasePage {

    private static final By CHECKBOXES = By.cssSelector("[type=checkbox]");

    public CheckboxesPage(WebDriver driver) {
        super(driver);
    }

    public CheckboxesPage open() {
        open("/checkboxes");
        return this;
    }

    /** Явно ждём появления обоих чекбоксов и возвращаем их количество. */
    public int waitForCount(int expected) {
        wait.until(d -> d.findElements(CHECKBOXES).size() >= expected);
        return driver.findElements(CHECKBOXES).size();
    }

    private WebElement checkbox(int index) {
        List<WebElement> boxes = driver.findElements(CHECKBOXES);
        if (index >= boxes.size()) {
            throw new IllegalArgumentException("Чекбокс #" + index + " не найден, всего: " + boxes.size());
        }
        return boxes.get(index);
    }

    /** Состояние чекбокса (true — отмечен). */
    public boolean isChecked(int index) {
        waitForCount(index + 1);
        return checkbox(index).isSelected();
    }

    /** Клик по чекбоксу с явным ожиданием кликабельности. */
    public void toggle(int index) {
        WebElement box = checkbox(index);
        wait.until(ExpectedConditions.elementToBeClickable(box));
        box.click();
    }
}
