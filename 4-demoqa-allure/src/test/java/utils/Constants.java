package utils;

/**
 * Централизованные константы проекта: базовый URL DemoQA, тестовые
 * данные и ожидаемые тексты (все значения проверены на живом сайте).
 *
 * <p>Вынесены в один класс, чтобы тесты не содержали «магических чисел»
 * и строк — при изменении сайта правится только этот файл.</p>
 */
public final class Constants {

    private Constants() {
        // утилитный класс — инстанцировать нельзя
    }

    // --- URL страниц (разделы Elements / Alerts, Frame & Windows / Widgets / Interactions) ---

    public static final String BASE_URL = "https://demoqa.com";
    public static final String TEXT_BOX_PATH = "/text-box";
    public static final String CHECKBOX_PATH = "/checkbox";
    public static final String RADIO_PATH = "/radio-button";
    public static final String ALERTS_PATH = "/alerts";
    public static final String FRAMES_PATH = "/frames";
    public static final String PROGRESS_BAR_PATH = "/progress-bar";
    public static final String SELECTABLE_PATH = "/selectable";

    // --- Text Box: тестовые данные ---

    public static final String VALID_NAME = "Ivan Petrov";
    public static final String VALID_EMAIL = "ivan.petrov@example.com";
    public static final String VALID_CURRENT_ADDRESS = "Current Street 1";
    public static final String VALID_PERMANENT_ADDRESS = "Permanent Street 2";
    public static final String INVALID_EMAIL = "not-an-email";

    // --- Alerts: ожидаемые тексты (проверены по бандлу сайта) ---

    /** Текст введённый в prompt (используется в тесте). */
    public static final String PROMPT_TEXT = "demo";

    /** Результат confirm при нажатии Cancel: «You selected Cancel». */
    public static final String CONFIRM_CANCEL_RESULT = "Cancel";

    /** Результат prompt: «You entered demo». */
    public static final String PROMPT_RESULT = "You entered";

    // --- Frames ---

    /** Текст внутри iframe (файл /sample.html). */
    public static final String FRAME_HEADING = "This is a sample page";

    // --- Selectable ---

    /** Первый элемент списка Interactions → Selectable. */
    public static final String SELECTABLE_FIRST_ITEM = "Cras justo odio";

    // --- Таймауты ---

    /** Таймаут загрузки страницы, сек. */
    public static final long PAGE_LOAD_TIMEOUT_SECONDS = 60;

    /** Таймаут явных ожиданий, сек. */
    public static final long EXPLICIT_WAIT_SECONDS = 15;

    /** Таймаут ожидания прогресс-бара до 100%, сек. */
    public static final long PROGRESS_TIMEOUT_SECONDS = 45;
}
