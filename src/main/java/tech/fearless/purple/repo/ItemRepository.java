package tech.fearless.purple.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.fearless.purple.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
