package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.AlertsPage;
import utils.Constants;

import static org.testng.Assert.assertTrue;

/** Тесты раздела Alerts, Frame & Windows → Alerts. */
@Epic("Alerts, Frame & Windows")
@Feature("Alerts")
@Story("Нативные JS-диалоги: confirm и prompt")
@Link(name = "Репозиторий", url = "https://github.com/andreevil2007-code/practical-works")
class AlertsTest extends BaseTest {

    @Test(description = "Confirm: dismiss выводит «You selected Cancel»")
    @Severity(SeverityLevel.MINOR)
    @Owner("Андреев")
    void shouldCancelConfirmDialog() {
        AlertsPage page = new AlertsPage(driver, wait)
                .open()
                .openConfirmAndDismiss();

        assertTrue(page.confirmText().contains(Constants.CONFIRM_CANCEL_RESULT),
                "Ожидался результат «You selected Cancel», фактически: " + page.confirmText());
    }

    @Test(description = "Prompt: введённый текст отражается в результате")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Андреев")
    void shouldSendTextIntoPromptDialog() {
        AlertsPage page = new AlertsPage(driver, wait)
                .open()
                .openPromptAndSend(Constants.PROMPT_TEXT);

        assertTrue(page.promptText().contains(Constants.PROMPT_TEXT),
                "Ожидался введённый текст в результате, фактически: " + page.promptText());
    }
}
