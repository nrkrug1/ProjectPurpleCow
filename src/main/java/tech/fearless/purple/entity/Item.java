package tech.fearless.purple.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Item {

  private @Id @GeneratedValue Long id;
  private String name;

  public Item() {}

  public Item(String name) {
    this.name = name;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }

    if (!(o instanceof Item)) {
      return false;
    }

    Item item = (Item) o;
    return Objects.equals(this.id, item.id) &&
      Objects.equals(this.name, item.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name);
  }

  @Override
  public String toString() {
    return "Item [" + "id:" + this.id + ", name:'" + this.name + "']";
  }
}
