package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Страница Typos — https://the-internet.herokuapp.com/typos
 *
 * <p>Сайт намеренно вносит опечатку во второй абзац при каждой загрузке
 * (например, {@code won,t} вместо {@code won't}), поэтому тест проверяет
 * принадлежность текста списку допустимых вариантов.</p>
 */
public class TyposPage extends BasePage {

    // Локатор из задания: все абзацы контентной области
    private static final By PARAGRAPHS = By.cssSelector("#content p");

    public TyposPage(WebDriver driver) {
        super(driver);
    }

    public TyposPage open() {
        open("/typos");
        return this;
    }

    private List<String> paragraphs() {
        wait.until(d -> d.findElements(PARAGRAPHS).size() >= 2);
        return driver.findElements(PARAGRAPHS).stream()
                .map(p -> p.getText().trim())
                .toList();
    }

    /** Первый (постоянный) абзац. */
    public String firstParagraph() {
        return paragraphs().get(0);
    }

    /** Второй абзац — тот, в котором появляется опечатка. */
    public String secondParagraph() {
        return paragraphs().get(1);
    }

    /** Все абзацы контентной области. */
    public List<String> allParagraphs() {
        return paragraphs();
    }
}
