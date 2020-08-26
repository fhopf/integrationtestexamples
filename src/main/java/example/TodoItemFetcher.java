package example;

import kong.unirest.*;

import static kong.unirest.Unirest.get;

public class TodoItemFetcher {

    private final String todoRoute;
    private final ObjectMapper objectMapper = new JsonObjectMapper();

    public TodoItemFetcher(String host) {
        this.todoRoute = host + "/todos/{id}";
    }

    public TodoItem fetch(String id) {

        HttpResponse<String> response = get(todoRoute)
            .routeParam("id", id)
            .asString();

        return objectMapper.readValue(response.getBody(), TodoItem.class);
    }



}
