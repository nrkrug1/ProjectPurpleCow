package tech.fearless.purple.web;

import tech.fearless.purple.entity.Item;
import tech.fearless.purple.exception.ItemNotFoundException;
import tech.fearless.purple.repo.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private JacksonTester<Item> jsonItem;
    @Autowired
    private JacksonTester<List<Item>> jsonItemList;

    @Test
    public void getAllItems() throws Exception {
      List<Item> items = new ArrayList<Item>();
      items.add(new Item(1L,"Test_1"));
      items.add(new Item(2L,"Test_2"));

      // given
      given(itemRepository.findAll())
      	.willReturn(items);

      // when
      MockHttpServletResponse response = mvc.perform(
        get("/items")
          .accept(MediaType.APPLICATION_JSON))
          .andReturn().getResponse();

      // then
      assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
      assertThat(response.getContentAsString()).isEqualTo(
        jsonItemList.write(items).getJson()
      );
    }

    @Test
    public void getItemThatExists() throws Exception {
    	// given
      given(itemRepository.findById(1L))
      	.willReturn(Optional.of(new Item(1L,"Test_1")));

      // when
      MockHttpServletResponse response = mvc.perform(
        get("/items/1")
          .accept(MediaType.APPLICATION_JSON))
          .andReturn().getResponse();

      // then
      assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
      assertThat(response.getContentAsString()).isEqualTo(
        jsonItem.write(new Item(1L,"Test_1")).getJson()
      );
    }

    @Test
    public void getItemThatDoesntExist() throws Exception {
      //Don't mock repo data
      try{
        mvc.perform(get("/items/1")
          .accept(MediaType.APPLICATION_JSON))
          .andReturn().getResponse();
      } catch (Exception e){
        assertTrue(e.getMessage().contains("Could not find item"));
      }
    }

    @Test
    public void postNewItem() throws Exception {
      Item newItem = new Item(1L,"Test_1");
      // given
      given(itemRepository.save(newItem)).willReturn(newItem);

      mvc.perform(post("/items")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(newItem)))
        .andExpect(status().isOk());
    }

    @Test
    public void postNewItemBatch() throws Exception {
      Item newItem1= new Item(1L,"Test_1");
      Item newItem2 = new Item(1L,"Test_2");
      List<Item> itemList = new ArrayList<Item>();
      itemList.add(newItem1);
      itemList.add(newItem2);
      // given
      given(itemRepository.saveAll(itemList)).willReturn(itemList);

      mvc.perform(post("/items/batch")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(itemList)))
        .andExpect(status().isOk());
    }

    @Test
    public void putItem() throws Exception {
      Item newItem = new Item(1L,"Test_1");

      // given
      given(itemRepository.findById(1L))
        .willReturn(Optional.of(newItem));

      given(itemRepository.save(newItem))
        .willReturn(newItem);

      MockHttpServletResponse response = mvc.perform(
        put("/items/1")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .content(this.mapper.writeValueAsString(newItem)))
          .andReturn().getResponse();

      assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
      assertThat(response.getContentAsString()).isEqualTo(
        jsonItem.write(new Item(1L,"Test_1")).getJson()
      );
    }

    @Test
    public void deleteItem() throws Exception {
      // when
      MockHttpServletResponse response = mvc.perform(
        delete("/items/1")
          .accept(MediaType.APPLICATION_JSON))
          .andReturn().getResponse();

      assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void deleteAll() throws Exception {
      // when
      MockHttpServletResponse response = mvc.perform(
        delete("/items")
          .accept(MediaType.APPLICATION_JSON))
          .andReturn().getResponse();

      assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
