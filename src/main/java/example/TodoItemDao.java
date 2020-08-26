package example;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class TodoItemDao {

    private final Jdbi jdbi;

    public TodoItemDao(String datasource) {
        jdbi = Jdbi.create(datasource);
    }

    public void persist(TodoItem todoItem) {
        jdbi.withHandle(handle -> {
            handle.execute("REPLACE INTO todoitem(id, user_id, title, completed) VALUES (?, ?, ?, ?)",
                todoItem.getId(), todoItem.getUserId(), todoItem.getTitle(), todoItem.isCompleted());
            return null;
        });
    }

    public List<TodoItem> readAll() {
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM todoitem")
                .map((rs, ctx) -> {
                    TodoItem todoItem = new TodoItem();
                    todoItem.setId(rs.getLong("id"));
                    todoItem.setTitle(rs.getString("title"));
                    todoItem.setUserId(rs.getLong("user_id"));
                    todoItem.setCompleted(Boolean.parseBoolean(rs.getString("completed")));
                    return todoItem;
                })
                .list();
        });
    }

}
