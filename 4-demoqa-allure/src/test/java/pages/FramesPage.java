package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

/**
 * Страница Alerts, Frame & Windows → Frames (demoqa.com/frames).
 *
 * <p>Содержит два iframe ({@code frame1}, {@code frame2}) с одинаковым
 * содержимым. После работы внутри фрейма обязательно выполняется
 * {@code switchTo().defaultContent()} — возврат в исходный контекст.</p>
 */
public class FramesPage extends BasePage {

    private static final By FRAME_ONE = By.id("frame1");
    private static final By FRAME_TWO = By.id("frame2");
    private static final By FRAME_HEADING =
            By.xpath("//*[contains(text(), '" + Constants.FRAME_HEADING + "')]");

    public FramesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public FramesPage open() {
        open("/frames", "Frames");
        return this;
    }

    /** Переключиться внутрь первого фрейма. */
    public FramesPage switchToFirstFrame() {
        Allure.step("Переключиться внутрь первого фрейма");
        driver.switchTo().frame("frame1");
        return this;
    }

    /** Заголовок-текст внутри фрейма («This is a sample page»). */
    public String frameHeadingText() {
        return el(FRAME_HEADING).getText().trim();
    }

    /** Вернуться из фрейма на основную страницу. */
    public FramesPage switchToDefaultContent() {
        Allure.step("Вернуться из фрейма на основную страницу");
        driver.switchTo().defaultContent();
        return this;
    }

    /** Отображается ли второй фрейм (проверка возврата контекста). */
    public boolean isSecondFrameDisplayed() {
        return el(FRAME_TWO).isDisplayed();
    }

    /** Первый фрейм на основной странице (для проверок). */
    public boolean isFirstFrameDisplayed() {
        return el(FRAME_ONE).isDisplayed();
    }
}
