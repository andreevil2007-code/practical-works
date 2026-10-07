package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Страница Hovers — https://the-internet.herokuapp.com/hovers
 *
 * <p>Карточки профилей скрыты до наведения мыши: надпись «name: userN» и
 * ссылка «View profile» появляются в {@code .figcaption} только при hover,
 * поэтому используется {@link Actions#moveToElement(WebElement)}.</p>
 */
public class HoversPage extends BasePage {

    private static final By FIGURES = By.cssSelector(".figure");

    public HoversPage(WebDriver driver) {
        super(driver);
    }

    public HoversPage open() {
        open("/hovers");
        return this;
    }

    private WebElement figure(int profileId) {
        wait.until(d -> d.findElements(FIGURES).size() >= profileId);
        return driver.findElements(FIGURES).get(profileId - 1);
    }

    /** Наведение мыши на карточку профиля (1..3). */
    public void hoverOverProfile(int profileId) {
        WebElement fig = figure(profileId);
        new Actions(driver).moveToElement(fig).perform();
    }

    /** Имя профиля, появившееся при наведении («name: user1»). */
    public String hoveredName(int profileId) {
        WebElement caption = figure(profileId).findElement(By.cssSelector(".figcaption"));
        wait.until(ExpectedConditions.visibilityOf(caption));
        return caption.findElement(By.tagName("h5")).getText().trim();
    }

    /** Клик по ссылке «View profile» внутри карточки. */
    public void clickViewProfile(int profileId) {
        WebElement link = figure(profileId).findElement(By.cssSelector(".figcaption a"));
        wait.until(ExpectedConditions.visibilityOf(link));
        link.click();
    }

    /** Текущий URL (для проверки перехода). */
    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    /** Признак страницы 404 («Not Found»). */
    public boolean isNotFoundPage() {
        return driver.getPageSource().contains("Not Found");
    }
}
