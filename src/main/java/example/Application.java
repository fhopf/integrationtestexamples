package example;

public class Application {

    public static void main(String [] args) {
        TodoItemFetcher fetcher = new TodoItemFetcher("https://jsonplaceholder.typicode.com");

        TodoItem item = fetcher.fetch("1");

        System.out.println(item);
    }

}
