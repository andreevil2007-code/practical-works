package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Страница Sortable Data Tables — https://the-internet.herokuapp.com/tables
 *
 * <p>Индексация XPath — как в задании, «как есть» в DOM:
 * {@code //table[1]//tr[2]//td[1]}.</p>
 *
 * <p><b>Важный нюанс:</b> первая строка таблицы ({@code tr[1]}) — это
 * заголовок с ячейками {@code <th>}, поэтому {@code tr[1]//td[...]} ничего
 * не находит. Данные начинаются со строки {@code tr[2]}; для заголовков
 * используется отдельный метод {@link #header(int, int, int)}.</p>
 */
public class SortableDataTablesPage extends BasePage {

    public SortableDataTablesPage(WebDriver driver) {
        super(driver);
    }

    public SortableDataTablesPage open() {
        open("/tables");
        return this;
    }

    /** Текст ячейки данных: tableNo/rowNo/colNo — номера с 1, tr[1] = заголовок. */
    public String cell(int tableNo, int rowNo, int colNo) {
        By locator = By.xpath("//table[" + tableNo + "]//tr[" + rowNo + "]//td[" + colNo + "]");
        return visible(locator).getText().trim();
    }

    /** Текст ячейки заголовка (th). */
    public String header(int tableNo, int rowNo, int colNo) {
        By locator = By.xpath("//table[" + tableNo + "]//tr[" + rowNo + "]//th[" + colNo + "]");
        return visible(locator).getText().trim();
    }

    /** Количество таблиц на странице. */
    public int tableCount() {
        return driver.findElements(By.xpath("//table")).size();
    }
}
