package utils;

/**
 * Централизованные константы проекта: базовый URL, демонстрационные
 * учётные данные Swag Labs, ожидаемые тексты и пути сайта.
 *
 * <p>Вынесены в один класс, чтобы тесты не содержали «магических чисел»
 * и строк — при изменении сайта правится только этот файл.</p>
 */
public final class Constants {

    private Constants() {
        // утилитный класс — инстанцировать нельзя
    }

    // --- Учётные данные и URL ---

    /** Базовый URL тестируемого приложения. */
    public static final String BASE_URL = "https://www.saucedemo.com/";

    /** Штатный пользователь Swag Labs (демо-данные с главной страницы входа). */
    public static final String STANDARD_USER = "standard_user";

    /** Заблокированный пользователь — для негативного сценария входа. */
    public static final String LOCKED_OUT_USER = "locked_out_user";

    /** Пароль для всех демо-пользователей. */
    public static final String PASSWORD = "secret_sauce";

    // --- Пути страниц (проверяются через URL) ---

    public static final String INVENTORY_PATH = "/inventory.html";
    public static final String CART_PATH = "/cart.html";
    public static final String CHECKOUT_STEP_ONE_PATH = "/checkout-step-one.html";
    public static final String CHECKOUT_STEP_TWO_PATH = "/checkout-step-two.html";
    public static final String CHECKOUT_COMPLETE_PATH = "/checkout-complete.html";

    // --- Ожидаемые тексты UI ---

    /** Заголовок каталога товаров. */
    public static final String PRODUCTS_TITLE = "Products";

    /** Ошибка входа заблокированного пользователя (проверено на сайте). */
    public static final String LOCKED_OUT_ERROR =
            "Epic sadface: Sorry, this user has been locked out.";

    /** Ошибка входа с неверным паролем (проверено на сайте). */
    public static final String WRONG_CREDENTIALS_ERROR =
            "Epic sadface: Username and password do not match any user in this service";

    /** Ошибка валидации пустого обязательного поля «First Name». */
    public static final String FIRST_NAME_REQUIRED_ERROR = "Error: First Name is required";

    /** Сообщение об успешном оформлении заказа. */
    public static final String ORDER_COMPLETE_MESSAGE = "Thank you for your order!";

    // --- Тестовые данные заказа ---

    /** Идентификатор товара (часть id кнопки: add-to-cart-&lt;id&gt;). */
    public static final String BACKPACK_ID = "sauce-labs-backpack";

    public static final String BACKPACK_NAME = "Sauce Labs Backpack";
    public static final String BACKPACK_PRICE = "$29.99";

    /** Самый дешёвый товар каталога (Sauce Labs Onesie). */
    public static final String LOWEST_PRICE = "$7.99";

    /** Самый дорогой товар каталога (Sauce Labs Fleece Jacket). */
    public static final String HIGHEST_PRICE = "$49.99";

    /** Данные формы доставки для чекаута. */
    public static final String CHECKOUT_FIRST_NAME = "Ivan";
    public static final String CHECKOUT_LAST_NAME = "Ivanov";
    public static final String CHECKOUT_POSTAL_CODE = "101000";

    // --- Таймауты ---

    /** Таймаут загрузки страницы, сек. */
    public static final long PAGE_LOAD_TIMEOUT_SECONDS = 60;

    /** Таймаут явных ожиданий, сек. */
    public static final long EXPLICIT_WAIT_SECONDS = 15;
}
