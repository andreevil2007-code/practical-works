package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.AllureAttachments;
import utils.Constants;
import utils.DriverFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

/**
 * Базовый класс теста — жизненный цикл драйвера и артефакты Allure.
 *
 * <p>Кроссбраузерность: параметры {@code browser} и {@code headless}
 * приходят из testng.xml ({@code @Parameters}) или из системных свойств
 * Maven ({@code -Dbrowser=}, {@code -Dheadless=}); приоритет — за
 * системными свойствами.</p>
 *
 * <ul>
 *   <li>{@code @BeforeMethod} — новый драйвер на каждый тест;</li>
 *   <li>{@code @AfterMethod} — при падении прикладываются скриншот и
 *       HTML-исходник страницы, затем всегда {@code driver.quit()};</li>
 *   <li>{@code @AfterSuite} — в каталог Allure-результатов копируются
 *       {@code environment.properties} и {@code categories.json}.</li>
 * </ul>
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @Parameters({"browser", "headless"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browserParam,
                      @Optional("true") String headlessParam) {
        String browser = firstNonEmpty(System.getProperty("browser"), browserParam, "chrome");
        boolean headless = Boolean.parseBoolean(
                firstNonEmpty(System.getProperty("headless"), headlessParam, "true"));

        driver = DriverFactory.create(browser, headless);
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(Constants.PAGE_LOAD_TIMEOUT_SECONDS));
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT_SECONDS));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver == null) {
            return;
        }
        try {
            if (!result.isSuccess()) {
                AllureAttachments.screenshot(driver, "Failure screenshot");
                AllureAttachments.pageSource(driver, "Page source on failure");
            }
        } finally {
            driver.quit();
        }
    }

    /** После прогона: environment.properties и categories.json — в allure-results. */
    @AfterSuite(alwaysRun = true)
    public void copyAllureEnvironment() {
        copyResourceToResults("/environment.properties", "environment.properties");
        copyResourceToResults("/categories.json", "categories.json");
    }

    private static void copyResourceToResults(String resource, String fileName) {
        try (InputStream in = BaseTest.class.getResourceAsStream(resource)) {
            if (in == null) {
                System.err.println("Ресурс не найден: " + resource);
                return;
            }
            Path target = Path.of("target", "allure-results", fileName);
            Files.createDirectories(target.getParent());
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Не удалось скопировать " + resource + ": " + e.getMessage());
        }
    }

    /** Первое непустое значение (системное свойство → параметр TestNG → по умолчанию). */
    private static String firstNonEmpty(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return "";
    }
}
