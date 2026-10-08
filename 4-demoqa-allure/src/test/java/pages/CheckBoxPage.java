package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Страница Elements → Check Box (demoqa.com/checkbox).
 *
 * <p>Дерево построено на компоненте rc-tree: чекбоксы — это span'ы
 * с ролью checkbox и стабильным aria-label («Select Desktop» и т.д.),
 * блок результата имеет id {@code result}.</p>
 */
public class CheckBoxPage extends BasePage {

    private static final By DESKTOP_CHECKBOX =
            By.cssSelector(".rc-tree-checkbox[aria-label='Select Desktop']");
    private static final By RESULT = By.id("result");

    /** Корневой узел дерева Home (его чекбоксы детей видны только после раскрытия). */
    private static final By HOME_NODE =
            By.xpath("//div[@role='treeitem' and .//span[@title='Home']]");
    private static final By HOME_SWITCHER = By.xpath(
            "//div[@role='treeitem' and .//span[@title='Home']]//span[contains(@class,'rc-tree-switcher')]");

    public CheckBoxPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public CheckBoxPage open() {
        open("/checkbox", "Check Box");
        return this;
    }

    /** Раскрыть корневой узел Home (в свёрнутом состоянии чекбоксы детей не видны). */
    public CheckBoxPage expandHome() {
        String expanded = el(HOME_NODE).getAttribute("aria-expanded");
        if (!"true".equals(expanded)) {
            Allure.step("Раскрыть корневой узел дерева Home");
            click(HOME_SWITCHER);
        }
        return this;
    }

    /** Отметить чекбокс Desktop (выделяет и вложенные элементы). */
    public CheckBoxPage selectDesktop() {
        expandHome();
        Allure.step("Отметить чекбокс Desktop");
        click(DESKTOP_CHECKBOX);
        return this;
    }

    /** Снять чекбокс Desktop. */
    public CheckBoxPage unselectDesktop() {
        expandHome();
        Allure.step("Снять чекбокс Desktop");
        click(DESKTOP_CHECKBOX);
        return this;
    }

    /** Явное ожидание появления блока результата. */
    public CheckBoxPage waitResultVisible() {
        el(RESULT);
        return this;
    }

    /** Отображается ли блок результата (без ожидания — для негативных проверок). */
    public boolean isResultDisplayed() {
        return driver.findElements(RESULT).stream().anyMatch(e -> e.isDisplayed());
    }

    /** Явное ожидание исчезновения блока результата. */
    public CheckBoxPage waitResultGone() {
        wait.until(d -> d.findElements(RESULT).stream().noneMatch(e -> e.isDisplayed()));
        return this;
    }

    /** Текст блока результата в нижнем регистре (для contains-проверок). */
    public String resultText() {
        return el(RESULT).getText().toLowerCase().replaceAll("\\s+", " ").trim();
    }
}
