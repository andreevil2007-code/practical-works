package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Фабрика драйверов для кроссбраузерного запуска.
 *
 * <p>Браузер выбирается параметром TestNG ({@code browser}) или
 * системным свойством Maven ({@code -Dbrowser=chrome|firefox|edge});
 * приоритет — за системным свойством. Версии драйверов подбираются
 * автоматически средствами Selenium Manager (встроен в Selenium 4.6+).</p>
 */
public final class DriverFactory {

    private DriverFactory() {
        // утилитный класс — инстанцировать нельзя
    }

    /** Создать WebDriver для указанного браузера. */
    public static WebDriver create(String browser, boolean headless) {
        return switch (browser.toLowerCase()) {
            case "firefox" -> firefox(headless);
            case "edge" -> edge(headless);
            default -> chrome(headless);
        };
    }

    private static WebDriver chrome(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments(
                "--window-size=1600,1000",
                "--disable-gpu",
                // окно не должно «засыпать» при сворачивании/перекрытии
                "--disable-backgrounding-occluded-windows",
                "--disable-background-timer-throttling",
                "--disable-renderer-backgrounding",
                "--disable-features=CalculateNativeWinOcclusion");
        return new ChromeDriver(options);
    }

    private static WebDriver edge(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments(
                "--window-size=1600,1000",
                "--disable-gpu",
                "--disable-backgrounding-occluded-windows",
                "--disable-background-timer-throttling",
                "--disable-renderer-backgrounding",
                "--disable-features=CalculateNativeWinOcclusion");
        return new EdgeDriver(options);
    }

    private static WebDriver firefox(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        options.addArguments("--width=1600", "--height=1000");
        return new FirefoxDriver(options);
    }
}
