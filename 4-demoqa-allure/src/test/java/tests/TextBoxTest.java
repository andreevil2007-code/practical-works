package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.TextBoxPage;
import utils.Constants;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/** Тесты раздела Elements → Text Box. */
@Epic("Elements")
@Feature("Text Box")
@Story("Заполнение формы и валидация email")
@Link(name = "Репозиторий", url = "https://github.com/andreevil2007-code/practical-works")
class TextBoxTest extends BaseTest {

    @Test(description = "Позитив: валидные данные приводят к отображению блока результата")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Андреев")
    void shouldSubmitValidData() {
        TextBoxPage page = new TextBoxPage(driver, wait)
                .open()
                .fill(Constants.VALID_NAME, Constants.VALID_EMAIL,
                        Constants.VALID_CURRENT_ADDRESS, Constants.VALID_PERMANENT_ADDRESS)
                .waitOutputVisible();

        assertTrue(page.isOutputVisible(),
                "Ожидалось появление блока результата");
        assertTrue(page.outputName().contains(Constants.VALID_NAME),
                "Имя в блоке результата, фактически: " + page.outputName());
        assertTrue(page.outputEmail().contains(Constants.VALID_EMAIL),
                "Email в блоке результата, фактически: " + page.outputEmail());
    }

    @Test(description = "Негатив: невалидный email блокирует отправку формы")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Андреев")
    void shouldNotShowResultForInvalidEmail() {
        TextBoxPage page = new TextBoxPage(driver, wait)
                .open()
                .fill(Constants.VALID_NAME, Constants.INVALID_EMAIL,
                        Constants.VALID_CURRENT_ADDRESS, Constants.VALID_PERMANENT_ADDRESS);

        assertFalse(page.isOutputVisible(),
                "Блок результата не должен появиться при невалидном email");
    }
}
