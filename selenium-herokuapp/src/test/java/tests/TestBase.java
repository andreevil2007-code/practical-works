package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

/**
 * Базовый класс теста — жизненный цикл драйвера.
 *
 * <p>{@code @BeforeEach} создаёт ChromeDriver на каждый тест,
 * {@code @AfterEach} гарантирует {@code driver.quit()} в любом исходе
 * (в том числе при падении теста) — утечек браузеров нет.</p>
 *
 * <p>Дополнительно: после каждого теста сохраняется скриншот
 * ({@code target/screenshots}), а при падении — скриншот, URL, заголовок
 * и исходник страницы в {@code target/failures}.</p>
 */
public abstract class TestBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Перехватчик исключения теста: срабатывает ДО {@code @AfterEach},
     * пока драйвер ещё жив — поэтому успеваем сохранить артефакты падения.
     */
    @RegisterExtension
    final TestExecutionExceptionHandler failureArtifacts = new TestExecutionExceptionHandler() {
        @Override
        public void handleTestExecutionException(ExtensionContext context, Throwable exception) throws Throwable {
            saveFailureArtifacts(context);
            throw exception;
        }
    };

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        // headless-режим включается ключом: mvn test -Dheadless=true
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless");
        }
        options.addArguments(
                "--window-size=1600,1000",
                "--disable-gpu",
                // Стабильность: Chrome не должен сворачивать/выгружать «скрытые» окна,
                // иначе web view закрывается и WebDriver падает с NoSuchWindowException
                "--disable-backgrounding-occluded-windows",
                "--disable-background-timer-throttling",
                "--disable-renderer-backgrounding",
                "--disable-features=CalculateNativeWinOcclusion,MemorySaver,MemorySaverMode"
        );

        driver = new ChromeDriver(options);
        // Запас на возможные сетевые стопы (эпизоды ~30 с фиксировались на маршруте к сайту)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    void tearDown(TestInfo info) {
        if (driver != null) {
            // Скриншот результата теста — материал для отчёта
            takeScreenshot(fileName(info), "screenshots");
            driver.quit();
        }
    }

    /** Базовый URL приложения (для проверок переходов). */
    protected String baseUrl() {
        return BasePage.BASE_URL;
    }

    private String fileName(TestInfo info) {
        String raw = info.getTestClass().map(Class::getSimpleName).orElse("Test")
                + "_" + info.getTestMethod().map(m -> m.getName()).orElse("test");
        return raw.replaceAll("[^a-zA-Z0-9_.-]", "_");
    }

    private String fileName(ExtensionContext context) {
        String raw = context.getRequiredTestClass().getSimpleName()
                + "_" + context.getRequiredTestMethod().getName();
        return raw.replaceAll("[^a-zA-Z0-9_.-]", "_");
    }

    private void saveFailureArtifacts(ExtensionContext context) {
        String name = fileName(context);
        Path dir = Path.of("target", "failures");
        try {
            Files.createDirectories(dir);
        } catch (Exception e) {
            return;
        }
        takeScreenshot(name, "failures");
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("test: ").append(name).append('\n');
            sb.append("url: ").append(driver.getCurrentUrl()).append('\n');
            sb.append("title: ").append(driver.getTitle()).append('\n');
            sb.append("pageSource:\n").append(driver.getPageSource());
            Files.writeString(dir.resolve(name + ".txt"), sb.toString());
        } catch (Exception ignored) {
            // окно уже недоступно — сохраняем хотя бы то, что получилось
        }
    }

    private void takeScreenshot(String name, String folder) {
        try {
            Path dir = Path.of("target", folder);
            Files.createDirectories(dir);
            File shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(shot.toPath(), dir.resolve(name + ".png"), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ignored) {
            // скриншот недоступен (например, окно закрыто) — не мываем прогон
        }
    }
}
