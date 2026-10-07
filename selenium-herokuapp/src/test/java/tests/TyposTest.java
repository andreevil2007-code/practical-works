package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.TyposPage;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест-класс для страницы Typos.
 *
 * <p>Сайт намеренно вносит опечатку во второй абзац при каждой загрузке,
 * поэтому проверяется принадлежность текста списку допустимых вариантов
 * (рекомендация методики задания).</p>
 */
class TyposTest extends TestBase {

    /** Допустимые варианты второго абзаца: без опечатки и с опечаткой. */
    private static final Set<String> ALLOWED_VARIANTS = Set.of(
            "Sometimes you'll see a typo, other times you won't.",
            "Sometimes you'll see a typo, other times you won,t."
    );

    /** Общая (каноническая) начало фразы — структурная проверка. */
    private static final String PREFIX = "Sometimes you'll see a typo, other times you won";

    @Test
    @DisplayName("Позитив: второй абзац соответствует орфографии (один из допустимых вариантов)")
    void secondParagraphHasAcceptableSpelling() {
        TyposPage page = new TyposPage(driver).open();

        String text = page.secondParagraph();

        assertTrue(ALLOWED_VARIANTS.contains(text),
                "Текст второго абзаца вышел за пределы допустимых вариантов: [" + text + "]");
        assertTrue(page.firstParagraph().startsWith("This example demonstrates a typo"),
                "Первый абзац должен оставаться неизменным");
    }

    @Test
    @DisplayName("Исследовательский: при перезагрузках меняется только допустимая часть текста")
    void typoVariantsStayWithinAllowedRange() {
        TyposPage page = new TyposPage(driver);
        Set<String> seen = new LinkedHashSet<>();

        for (int i = 0; i < 8; i++) {
            page.open();
            String text = page.secondParagraph();
            seen.add(text);
            assertTrue(text.startsWith(PREFIX),
                    "Структура абзаца нарушена: [" + text + "]");
            assertFalse(text.isBlank(), "Абзац не должен быть пустым");
        }

        assertTrue(seen.size() >= 1, "Должен быть хотя бы один вариант текста");
        System.out.println("Наблюденные варианты текста: " + seen);
    }
}
