package tests;

import org.junit.jupiter.api.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты страницы входа: позитивный сценарий и два негативных
 * (заблокированный пользователь и неверный пароль).
 */
class LoginTests extends TestBase {

    /** Позитив: вход штатным пользователем открывает каталог товаров. */
    @Test
    void loginWithValidCredentialsOpensInventory() {
        InventoryPage inventory = openLogin()
                .loginAs(Constants.STANDARD_USER, Constants.PASSWORD)
                .waitLoaded();

        assertTrue(driver.getCurrentUrl().contains(Constants.INVENTORY_PATH),
                "После успешного входа ожидается URL " + Constants.INVENTORY_PATH
                        + ", фактический: " + driver.getCurrentUrl());
        assertEquals(Constants.PRODUCTS_TITLE, inventory.title(),
                "Заголовок каталога товаров");
        assertEquals(6, inventory.itemNames().size(),
                "Количество товаров в каталоге");
        assertTrue(inventory.itemNames().contains(Constants.BACKPACK_NAME),
                "Sauce Labs Backpack присутствует в каталоге");
    }

    /** Негатив: заблокированный пользователь получает сообщение об ошибке. */
    @Test
    void loginWithLockedOutUserShowsError() {
        LoginPage loginPage = openLogin()
                .enterCredentials(Constants.LOCKED_OUT_USER, Constants.PASSWORD)
                .submit();

        assertTrue(loginPage.isOnLoginPage(),
                "Заблокированный пользователь остаётся на странице входа, "
                        + "фактический URL: " + driver.getCurrentUrl());
        assertTrue(loginPage.isErrorVisible(),
                "Для заблокированного пользователя отображается сообщение об ошибке");
        assertEquals(Constants.LOCKED_OUT_ERROR, loginPage.errorText(),
                "Текст ошибки блокировки учётной записи");
    }

    /** Негатив: неверный пароль — сообщение о несовпадении учётных данных. */
    @Test
    void loginWithWrongPasswordShowsCredentialsError() {
        LoginPage loginPage = openLogin()
                .enterCredentials(Constants.STANDARD_USER, "wrong_password")
                .submit();

        assertTrue(loginPage.isErrorVisible(),
                "При неверном пароле отображается сообщение об ошибке");
        assertEquals(Constants.WRONG_CREDENTIALS_ERROR, loginPage.errorText(),
                "Текст ошибки при неверном пароле");
        assertTrue(loginPage.isOnLoginPage(),
                "Пользователь остаётся на странице входа, "
                        + "фактический URL: " + driver.getCurrentUrl());
    }
}
