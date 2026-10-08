package tests;

import org.junit.jupiter.api.Test;
import pages.CheckoutPage;
import utils.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты оформления заказа: полный сценарий до страницы подтверждения
 * и негативный сценарий с пустым обязательным полем.
 */
class CheckoutTests extends TestBase {

    /** Позитив: чекаут от корзины до «Thank you for your order!». */
    @Test
    void checkoutFlowCompletesWithThankYouMessage() {
        CheckoutPage checkout = loginAsStandardUser()
                .addToCart(Constants.BACKPACK_ID)
                .openCart()
                .waitLoaded()
                .openCheckout()
                .waitStepOneLoaded();

        checkout.fillInformation(
                        Constants.CHECKOUT_FIRST_NAME,
                        Constants.CHECKOUT_LAST_NAME,
                        Constants.CHECKOUT_POSTAL_CODE)
                .submitContinue()
                .waitStepTwoLoaded();

        assertEquals("Item total: " + Constants.BACKPACK_PRICE, checkout.subtotalText(),
                "Сумма товаров в обзоре заказа");
        assertTrue(checkout.overviewItemNames().contains(Constants.BACKPACK_NAME),
                "Товар отображается в обзоре заказа, фактический список: "
                        + checkout.overviewItemNames());
        // $29.99 + налог 8% ($2.40) = $32.39
        assertEquals("Total: $32.39", checkout.totalText(),
                "Итоговая сумма заказа с учётом налога");

        checkout.finish().waitCompleteLoaded();

        assertEquals(Constants.ORDER_COMPLETE_MESSAGE, checkout.completeHeaderText(),
                "Подтверждение успешного оформления заказа");
        assertTrue(driver.getCurrentUrl().contains(Constants.CHECKOUT_COMPLETE_PATH),
                "URL страницы завершения заказа, фактический: " + driver.getCurrentUrl());
    }

    /** Негатив: пустое обязательное поле First Name блокирует переход дальше. */
    @Test
    void checkoutWithEmptyFirstNameShowsValidationError() {
        CheckoutPage checkout = loginAsStandardUser()
                .addToCart(Constants.BACKPACK_ID)
                .openCart()
                .waitLoaded()
                .openCheckout()
                .waitStepOneLoaded()
                .submitContinue();

        assertTrue(driver.getCurrentUrl().contains(Constants.CHECKOUT_STEP_ONE_PATH),
                "При пустом обязательном поле переход к обзору заказа невозможен, "
                        + "фактический URL: " + driver.getCurrentUrl());
        assertEquals(Constants.FIRST_NAME_REQUIRED_ERROR, checkout.errorText(),
                "Сообщение валидации при пустом поле First Name");
    }
}
