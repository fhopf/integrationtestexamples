package example;

import org.jdbi.v3.core.Jdbi;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TodoItemDaoTest {

    @Rule
    public MySQLContainer container = new MySQLContainer();

    private Jdbi jdbi;
    private TodoItemDao todoItemDao;

    @Before
    public void setUp() {
        jdbi = Jdbi.create(container.getJdbcUrl(), container.getUsername(), container.getPassword());
        todoItemDao = new TodoItemDao(container.getJdbcUrl(), container.getUsername(), container.getPassword());

        jdbi.withHandle(handle -> {
            handle.execute("CREATE TABLE todoitem (id INTEGER PRIMARY KEY, user_id INTEGER, title VARCHAR(255), completed VARCHAR(255))");
            return null;
        });
    }

    @Test
    public void getAllOnEmptyTable() {
        List<TodoItem> todoItems = todoItemDao.readAll();
        assertEquals(0, todoItems.size());
    }

    @Test
    public void itemCanBePersisted() {
        TodoItem item = new TodoItem();
        item.setId(6);
        item.setUserId(1);
        item.setTitle("testtitle");
        item.setCompleted(true);

        todoItemDao.persist(item);

        jdbi.withHandle(handle -> {
           long items = handle.createQuery("select count(*) from todoitem").map((rs, ctx) -> rs.getLong(1)).first();
           assertEquals(1, items);
           return null;
        });
    }

}
