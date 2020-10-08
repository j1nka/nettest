package ru.netology.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

// простейшая реализация псевдо-очереди
@RestController
@RequestMapping("/api/events")
@SpringBootApplication
public class WebApplication {
  private final Queue<String> events = new ConcurrentLinkedQueue<>();

  public static void main(String[] args) {
    SpringApplication.run(WebApplication.class, args);
  }

  @GetMapping
  public Queue<String> getAll() {
    return events;
  }

  @PostMapping
  public Queue<String> add(@RequestBody String event) {
    events.add(event);
    return events;
  }
}

