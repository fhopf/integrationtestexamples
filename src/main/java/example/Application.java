package example;

public class Application {

    public static void main(String [] args) {
        TodoItemFetcher fetcher = new TodoItemFetcher("https://jsonplaceholder.typicode.com");
        TodoItemDao todoItemDao = new TodoItemDao("jdbc:mysql://root:@localhost:3306/todo");

        TodoItem item = fetcher.fetch("1");
        todoItemDao.persist(item);

        System.out.println(todoItemDao.readAll());
    }

}
