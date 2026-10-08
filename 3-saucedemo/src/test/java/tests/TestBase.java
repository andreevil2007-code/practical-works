package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Constants;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Базовый класс теста — жизненный цикл драйвера и артефакты.
 *
 * <p>{@code @BeforeEach} создаёт ChromeDriver на каждый тест,
 * {@code @AfterEach} гарантирует {@code driver.quit()} в любом исходе
 * (в том числе при падении теста) — утечек браузеров нет.</p>
 *
 * <p>Для отчёта автоматически сохраняются:</p>
 * <ul>
 *   <li>скриншот каждого теста — {@code target/screenshots/};</li>
 *   <li>при падении: URL и HTML-исходник страницы — {@code target/failures/}.</li>
 * </ul>
 *
 * <p>Версия chromedriver подбирается автоматически средствами
 * Selenium Manager (встроен в Selenium 4.6+), WebDriverManager не нужен.</p>
 */
public abstract class TestBase implements TestExecutionExceptionHandler {

    protected WebDriver driver;
    protected WebDriverWait wait;

    /** Ошибка, из-за которой упал текущий тест (null — тест прошёл). */
    private Throwable failure;

    @BeforeEach
    void setUp() {
        failure = null;

        ChromeOptions options = new ChromeOptions();
        // headless-режим включается ключом: mvn test -Dheadless=true
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments(
                "--window-size=1600,1000",
                "--disable-gpu",
                // окно браузера не должно «засыпать» при сворачивании/перекрытии
                "--disable-backgrounding-occluded-windows",
                "--disable-background-timer-throttling",
                "--disable-renderer-backgrounding",
                "--disable-features=CalculateNativeWinOcclusion");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constants.PAGE_LOAD_TIMEOUT_SECONDS));
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT_SECONDS));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (driver == null) {
            return;
        }
        try {
            saveScreenshot(testInfo);
            if (failure != null) {
                saveFailureDetails(testInfo);
            }
        } catch (Exception e) {
            System.err.println("Не удалось сохранить артефакты теста: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    /** Перехват падения теста — запоминаем ошибку для сохранения артефактов. */
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        this.failure = throwable;
        throw throwable;
    }

    /** Открыть страницу входа. */
    protected LoginPage openLogin() {
        return new LoginPage(driver).open();
    }

    /** Штатный сценарий: вход штатным пользователем и ожидание каталога. */
    protected InventoryPage loginAsStandardUser() {
        return openLogin()
                .loginAs(Constants.STANDARD_USER, Constants.PASSWORD)
                .waitLoaded();
    }

    private void saveScreenshot(TestInfo testInfo) throws IOException {
        Path dir = Path.of("target", "screenshots");
        Files.createDirectories(dir);
        byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Files.write(dir.resolve(baseName(testInfo) + ".png"), png);
    }

    private void saveFailureDetails(TestInfo testInfo) throws IOException {
        Path dir = Path.of("target", "failures");
        Files.createDirectories(dir);
        Files.writeString(dir.resolve(baseName(testInfo) + "-url.txt"),
                String.valueOf(driver.getCurrentUrl()), StandardCharsets.UTF_8);
        Files.writeString(dir.resolve(baseName(testInfo) + ".html"),
                driver.getPageSource(), StandardCharsets.UTF_8);
    }

    private String baseName(TestInfo testInfo) {
        String className = testInfo.getTestClass().map(Class::getSimpleName).orElse("Unknown");
        String methodName = testInfo.getTestMethod().map(Method::getName).orElse("unknownTest");
        return className + "_" + methodName;
    }
}
