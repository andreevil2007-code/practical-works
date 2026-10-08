package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

/**
 * Вложения Allure: скриншоты и HTML-исходник страницы.
 *
 * <p>Прикладываются дозированно: при падении теста (из
 * {@code BaseTest.tearDown}) — скриншот и исходник страницы.</p>
 */
public final class AllureAttachments {

    private AllureAttachments() {
        // утилитный класс — инстанцировать нельзя
    }

    /** Приложить скриншот текущего состояния браузера. */
    public static void screenshot(WebDriver driver, String name) {
        try {
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            System.err.println("Не удалось приложить скриншот: " + e.getMessage());
        }
    }

    /** Приложить HTML-исходник страницы. */
    public static void pageSource(WebDriver driver, String name) {
        try {
            Allure.addAttachment(name, "text/html", driver.getPageSource(), ".html");
        } catch (Exception e) {
            System.err.println("Не удалось приложить исходник страницы: " + e.getMessage());
        }
    }
}
