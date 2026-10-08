package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.RadioButtonPage;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/** Тесты раздела Elements → Radio Button. */
@Epic("Elements")
@Feature("Radio Buttons")
@Story("Выбор радиокнопки и проверка недоступного варианта")
@Link(name = "Репозиторий", url = "https://github.com/andreevil2007-code/practical-works")
class RadioButtonTest extends BaseTest {

    @Test(description = "Позитив: выбор Yes выводит строку «You have selected Yes»")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Андреев")
    void shouldSelectYesAndShowResult() {
        RadioButtonPage page = new RadioButtonPage(driver, wait)
                .open()
                .selectYes()
                .waitResultVisible();

        String result = page.resultText();
        assertTrue(result.contains("You have selected"),
                "Ожидалась строка «You have selected …», фактически: " + result);
        assertTrue(result.contains("Yes"),
                "Ожидалось выбранное «Yes», фактически: " + result);
    }

    @Test(description = "Негатив: радиокнопка No недоступна (disabled)")
    @Severity(SeverityLevel.MINOR)
    @Owner("Андреев")
    void shouldKeepNoRadioDisabled() {
        RadioButtonPage page = new RadioButtonPage(driver, wait).open();

        assertFalse(page.isNoEnabled(),
                "Радиокнопка No на сайте должна быть недоступна (disabled)");
    }
}
