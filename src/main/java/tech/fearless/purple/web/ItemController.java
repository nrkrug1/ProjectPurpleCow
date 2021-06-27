package tech.fearless.purple.web;

import java.util.List;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tech.fearless.purple.entity.Item;
import tech.fearless.purple.exception.ItemNotFoundException;
import tech.fearless.purple.repo.ItemRepository;

@RestController
public class ItemController {
  private static final Logger log = LoggerFactory.getLogger(ItemController.class);

  private final ItemRepository repository;

  ItemController(ItemRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/items")
  List<Item> all() {
    return repository.findAll();
  }

  @GetMapping("/items/{id}")
  Item one(@PathVariable Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new ItemNotFoundException(id));
  }

  @PostMapping("/items")
  Item newItem(@RequestBody Item newItem) {
    return repository.save(newItem);
  }

  @PostMapping("/items/batch")
  List<Item> newItem(@RequestBody List<Item> newItems) {
    return repository.saveAll(newItems);
  }

  @PutMapping("/items/{id}")
  Item replaceItem(@RequestBody Item newItem, @PathVariable Long id) {
    return repository.findById(id)
      .map(item -> {
        item.setName(newItem.getName());
        return repository.save(item);
      })
      .orElseGet(() -> {
        newItem.setId(id);
        return repository.save(newItem);
      });
  }

  @DeleteMapping("/items/{id}")
  void deleteItem(@PathVariable Long id) {
    repository.deleteById(id);
  }

  @DeleteMapping("/items")
  void deleteAll() {
    repository.deleteAllInBatch();
  }
}
