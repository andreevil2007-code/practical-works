package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.NotificationMessagesPage;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест-класс для страницы Notification Messages (дополнительный раздел задания).
 */
class NotificationMessagesTest extends TestBase {

    /**
     * Допустимые тексты уведомлений: два варианта подтверждены на живом
     * сайте эмпирически, ещё два — типовые для данного приложения.
     * Список расширен намеренно: сообщение выбирается сервером случайно.
     */
    private static final Set<String> ALLOWED_MESSAGES = Set.of(
            "Action successful",
            "Action unsuccesful, please try again",
            "It's dangerous to go alone! Take this!",
            "Message timed out!"
    );

    @Test
    @DisplayName("Клик по кнопке показывает уведомление с ожидаемым текстом")
    void clickShowsNotificationWithExpectedText() {
        NotificationMessagesPage page = new NotificationMessagesPage(driver).open();

        String initial = page.message();
        assertFalse(initial.isBlank(), "На странице сразу должно отображаться уведомление");
        assertTrue(ALLOWED_MESSAGES.contains(initial),
                "Недопустимый текст уведомления при загрузке: [" + initial + "]");

        String afterClick = page.clickAndWaitNewMessage();

        assertFalse(afterClick.isBlank(), "После клика должно появиться уведомление");
        assertTrue(ALLOWED_MESSAGES.contains(afterClick),
                "Текст уведомления после клика вне допустимого списка: [" + afterClick + "]");
    }
}
