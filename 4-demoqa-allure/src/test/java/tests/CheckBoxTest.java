package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.CheckBoxPage;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/** Тесты раздела Elements → Check Box. */
@Epic("Elements")
@Feature("Check Box")
@Story("Отметка чекбокса и блок результата")
@Link(name = "Репозиторий", url = "https://github.com/andreevil2007-code/practical-works")
class CheckBoxTest extends BaseTest {

    @Test(description = "Позитив: отметка Desktop выводит список выбранных элементов")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Андреев")
    void shouldShowSelectionResultAfterCheck() {
        CheckBoxPage page = new CheckBoxPage(driver, wait)
                .open()
                .selectDesktop()
                .waitResultVisible();

        String result = page.resultText();
        assertTrue(page.isResultDisplayed(),
                "Блок результата отображается после отметки чекбокса");
        assertTrue(result.contains("desktop"),
                "В результате должен быть desktop, фактически: " + result);
        assertTrue(result.contains("notes"),
                "В результате должны быть вложенные элементы (notes), фактически: " + result);
    }

    @Test(description = "Негатив: после снятия чекбокса блок результата исчезает")
    @Severity(SeverityLevel.MINOR)
    @Owner("Андреев")
    void shouldHideResultAfterUncheck() {
        CheckBoxPage page = new CheckBoxPage(driver, wait)
                .open()
                .selectDesktop()
                .waitResultVisible()
                .unselectDesktop()
                .waitResultGone();

        assertFalse(page.isResultDisplayed(),
                "После снятия чекбокса блок результата исчезает");
    }
}
