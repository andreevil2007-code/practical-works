package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Страница Notification Messages — https://the-internet.herokuapp.com/notification_message
 *
 * <p>Клик по «Click here» перезагружает страницу и показывает случайное
 * сообщение в блоке {@code #flash}. Текст сообщения непредсказуем,
 * поэтому тест сверяет его со списком допустимых вариантов.</p>
 */
public class NotificationMessagesPage extends BasePage {

    private static final By CLICK_HERE = By.linkText("Click here");
    private static final By FLASH = By.id("flash");

    public NotificationMessagesPage(WebDriver driver) {
        super(driver);
    }

    public NotificationMessagesPage open() {
        open("/notification_message");
        return this;
    }

    /** Текущий текст уведомления (без крестика закрытия). */
    public String message() {
        return visible(FLASH).getText().replace("×", "").trim();
    }

    /**
     * Клик по «Click here» и ожидание нового уведомления:
     * страница перезагружается, поэтому ждём устаревания старого элемента,
     * а затем появления непустого текста в новом.
     */
    public String clickAndWaitNewMessage() {
        WebElement oldFlash = driver.findElement(FLASH);
        clickable(CLICK_HERE).click();
        wait.until(ExpectedConditions.stalenessOf(oldFlash));
        wait.until(d -> !d.findElement(FLASH).getText().replace("×", "").trim().isEmpty());
        return message();
    }
}
