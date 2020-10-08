package ru.netology.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WebApplicationTests {
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void events() {
    {
      // первый запрос (GET) - пустая очередь:
      final var items = restTemplate
          .exchange("/api/events", HttpMethod.GET, null, Object[].class)
          .getBody();
      assertEquals(items.length, 0);
    }

    {
      // второй запрос (POST) - добавляем событие в очередь:
      final var items = restTemplate
          .exchange("/api/events", HttpMethod.POST, new HttpEntity<>("Security Event"), Object[].class)
          .getBody();
      assertArrayEquals(items, List.of("Security Event").toArray());
    }

    {
      // третий запрос (GET) - событие должно было сохраниться в очереди:
      final var items = restTemplate
          .exchange( "/api/events", HttpMethod.GET, null, Object[].class)
          .getBody();
      assertArrayEquals(items, List.of("Security Event").toArray());
    }
  }

}
