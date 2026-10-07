package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.SortableDataTablesPage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест-класс для страницы Sortable Data Tables (дополнительный раздел задания).
 *
 * <p>Нюанс XPath: строка {@code tr[1]} содержит заголовки {@code <th>},
 * поэтому ячейки данных берутся начиная с {@code tr[2]}.</p>
 */
class SortableDataTablesTest extends TestBase {

    @Test
    @DisplayName("Позитив: на странице две таблицы и корректные заголовки")
    void pageContainsTwoTablesWithExpectedHeaders() {
        SortableDataTablesPage page = new SortableDataTablesPage(driver).open();

        assertEquals(2, page.tableCount(), "На странице должно быть 2 таблицы");
        assertEquals("Last Name", page.header(1, 1, 1), "Первый заголовок первой таблицы");
        assertEquals("First Name", page.header(1, 1, 2), "Второй заголовок первой таблицы");
        assertEquals("Due", page.header(1, 1, 4), "Заголовок колонки Due");
    }

    @Test
    @DisplayName("Позитив: содержимое 5 ячеек первой таблицы соответствует ожиданиям")
    void cellValuesMatchExpectedData() {
        SortableDataTablesPage page = new SortableDataTablesPage(driver).open();

        // tr[2] — первая строка данных (tr[1] — заголовок)
        assertEquals("Smith", page.cell(1, 2, 1), "Фамилия в первой строке данных");
        assertEquals("John", page.cell(1, 2, 2), "Имя в первой строке данных");
        assertEquals("jsmith@gmail.com", page.cell(1, 2, 3), "E-mail в первой строке данных");

        assertEquals("Doe", page.cell(1, 4, 1), "Фамилия в третьей строке данных");
        assertEquals("$100.00", page.cell(1, 4, 4), "Сумма задолженности третьей строки");
    }
}
