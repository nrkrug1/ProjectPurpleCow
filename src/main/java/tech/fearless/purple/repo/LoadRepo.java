package tech.fearless.purple.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.fearless.purple.entity.Item;

@Configuration
public class LoadRepo {

  private static final Logger log = LoggerFactory.getLogger(LoadRepo.class);

  @Bean
  CommandLineRunner initDatabase(ItemRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Item("Test1")));
      log.info("Preloading " + repository.save(new Item("Test2")));
    };
  }
}
