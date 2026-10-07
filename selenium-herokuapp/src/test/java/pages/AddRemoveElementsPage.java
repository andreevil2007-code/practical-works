package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Страница Add/Remove Elements — https://the-internet.herokuapp.com/add_remove_elements/
 *
 * <p>Важно: путь обязан заканчиваться слешем, иначе сервер отдаёт 404.</p>
 */
public class AddRemoveElementsPage extends BasePage {

    // Локаторы из задания
    private static final By ADD_BUTTON = By.xpath("//button[text()='Add Element']");
    private static final By DELETE_BUTTON = By.xpath("//button[text()='Delete']");

    // Добавленные элементы: <button class="added-manually">Delete</button> внутри #elements
    private static final By ADDED_ELEMENTS = By.cssSelector("#elements button.added-manually");

    public AddRemoveElementsPage(WebDriver driver) {
        super(driver);
    }

    public AddRemoveElementsPage open() {
        open("/add_remove_elements/");
        return this;
    }

    /** Клик по кнопке Add Element. */
    public void addElement() {
        clickable(ADD_BUTTON).click();
    }

    /** Добавить N элементов подряд. */
    public void addElements(int count) {
        for (int i = 0; i < count; i++) {
            addElement();
        }
    }

    /** Удалить первый элемент (страница удаляет всегда первый). */
    public void deleteFirstElement() {
        wait.until(d -> !d.findElements(DELETE_BUTTON).isEmpty());
        driver.findElement(DELETE_BUTTON).click();
    }

    /** Текущее количество добавленных элементов. */
    public int count() {
        return driver.findElements(ADDED_ELEMENTS).size();
    }

    /** Явное ожидание, что количество станет равным ожидаемому. */
    public void waitCount(int expected) {
        wait.until(d -> d.findElements(ADDED_ELEMENTS).size() == expected);
    }
}
