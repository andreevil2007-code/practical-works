package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.ProgressBarPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/** Тесты раздела Widgets → Progress Bar. */
@Epic("Widgets")
@Feature("Progress Bar")
@Story("Доведение прогресс-бара до 100%")
@Link(name = "Репозиторий", url = "https://github.com/andreevil2007-code/practical-works")
class ProgressBarTest extends BaseTest {

    @Test(description = "Позитив: после запуска прогресс-бар доходит до 100%, кнопка исчезает")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Андреев")
    void shouldReachHundredPercent() {
        ProgressBarPage page = new ProgressBarPage(driver, wait)
                .open()
                .start()
                .waitUntilComplete();

        assertEquals(page.progressValue(), "100",
                "Значение aria-valuenow после завершения");
        assertTrue(page.progressText().contains("100"),
                "Текст прогресс-бара после завершения, фактически: " + page.progressText());
        assertFalse(page.isControlButtonDisplayed(),
                "Кнопка Start/Stop исчезает после завершения прогресса");
    }
}
