package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Страница Inputs — https://the-internet.herokuapp.com/inputs
 *
 * <p>Поле — {@code <input type="number">}: буквенные значения в него
 * не попадают, стрелки {@link Keys#ARROW_UP}/{@link Keys#ARROW_DOWN}
 * изменяют число на единицу.</p>
 */
public class InputsPage extends BasePage {

    // Локатор из задания
    private static final By INPUT = By.tagName("input");

    public InputsPage(WebDriver driver) {
        super(driver);
    }

    public InputsPage open() {
        open("/inputs");
        return this;
    }

    private WebElement input() {
        return visible(INPUT);
    }

    /** Очистка поля и ввод текста. */
    public void type(String text) {
        WebElement field = input();
        field.clear();
        field.sendKeys(text);
    }

    /** Нажатие клавиши (стрелки и т.п.). */
    public void press(Keys key) {
        input().sendKeys(key);
    }

    /** Текущее значение поля (значение атрибута value). */
    public String value() {
        return input().getAttribute("value");
    }
}
