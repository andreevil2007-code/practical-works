package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.SelectablePage;

import static org.testng.Assert.assertTrue;

/** Тесты раздела Interactions → Selectable. */
@Epic("Interactions")
@Feature("Selectable")
@Story("Выбор элемента списка")
@Link(name = "Репозиторий", url = "https://github.com/andreevil2007-code/practical-works")
class SelectableTest extends BaseTest {

    @Test(description = "Позитив: клик по первому элементу помечает его выбранным (класс active)")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Андреев")
    void shouldSelectFirstListItem() {
        SelectablePage page = new SelectablePage(driver, wait)
                .open()
                .selectFirstItem()
                .waitFirstItemSelected();

        assertTrue(page.isFirstItemSelected(),
                "Первый элемент списка должен получить класс active");
    }
}
