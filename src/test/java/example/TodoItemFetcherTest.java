package example;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

public class TodoItemFetcherTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    private TodoItemFetcher todoItemFetcher;

    @Before
    public void setUp() {
        todoItemFetcher = new TodoItemFetcher(wireMockRule.baseUrl());
    }

    @Test
    public void testSuccessfulRequest() {

        wireMockRule.stubFor(get("/todos/1")
            .willReturn(okJson("{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 3,\n" +
                "  \"title\": \"Some title for this todo\",\n" +
                "  \"completed\": false\n" +
                "}")));

        TodoItem result = todoItemFetcher.fetch("1");
        assertEquals(1, result.getUserId());
        assertEquals(3, result.getId());
        assertEquals("Some title for this todo", result.getTitle());
        assertFalse(result.isCompleted());

        verify(getRequestedFor(urlEqualTo("/todos/1")));
    }

    @Test
    public void testNotFound() {
        wireMockRule.stubFor(get("/todos/x")
            .willReturn(notFound()));

        TodoItem result = todoItemFetcher.fetch("x");
        assertNull(result);
    }

    public void testServerError() {
        wireMockRule.stubFor(get("/todos/x")
            .willReturn(serverError()));

        todoItemFetcher.fetch("x");
    }

}
