package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.FramesPage;
import utils.Constants;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/** Тесты раздела Alerts, Frame & Windows → Frames. */
@Epic("Alerts, Frame & Windows")
@Feature("Frames")
@Story("Работа с iframe и возврат в основной контекст")
@Link(name = "Репозиторий", url = "https://github.com/andreevil2007-code/practical-works")
class FramesTest extends BaseTest {

    @Test(description = "Позитив: содержимое iframe читается, после switchTo().defaultContent() контекст восстановлен")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Андреев")
    void shouldReadContentInsideIframeAndReturnBack() {
        FramesPage page = new FramesPage(driver, wait).open();

        page.switchToFirstFrame();
        assertEquals(page.frameHeadingText(), Constants.FRAME_HEADING,
                "Текст заголовка внутри первого фрейма");

        page.switchToDefaultContent();
        assertTrue(page.isSecondFrameDisplayed(),
                "После возврата из фрейма второй фрейм виден на основной странице");
        assertTrue(page.isFirstFrameDisplayed(),
                "После возврата из фрейма первый фрейм виден на основной странице");
    }
}
