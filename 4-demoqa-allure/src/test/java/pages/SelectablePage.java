package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

/**
 * Страница Interactions → Selectable (demoqa.com/selectable).
 *
 * <p>Во вкладке List элементы списка получают класс {@code active}
 * при выборе — по нему и проверяется состояние.</p>
 */
public class SelectablePage extends BasePage {

    private static final By FIRST_ITEM = By.xpath(
            "//*[@id='verticalListContainer']//li[text()='" + Constants.SELECTABLE_FIRST_ITEM + "']");

    public SelectablePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public SelectablePage open() {
        open("/selectable", "Selectable");
        return this;
    }

    /** Выбрать первый элемент списка. */
    public SelectablePage selectFirstItem() {
        Allure.step("Выбрать первый элемент списка: " + Constants.SELECTABLE_FIRST_ITEM);
        click(FIRST_ITEM);
        return this;
    }

    /** Явное ожидание: у элемента появился класс active. */
    public SelectablePage waitFirstItemSelected() {
        wait.until(d -> d.findElement(FIRST_ITEM).getAttribute("class").contains("active"));
        return this;
    }

    /** Выбран ли первый элемент (класс active). */
    public boolean isFirstItemSelected() {
        return driver.findElement(FIRST_ITEM).getAttribute("class").contains("active");
    }
}
