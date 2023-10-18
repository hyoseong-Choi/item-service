package hello.itemservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();
    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }
    @Test
    void save() {
        item item = new item("A", 10000, 10);
        item saveditem = itemRepository.save(item);
        item finditem = itemRepository.findById(item.getId());
        assertThat(finditem).isEqualTo(saveditem);
    }
    
    @Test
    void findAll() {
        item item1 = new item("A", 10000, 10);
        item item2 = new item("B", 20000, 20);
        itemRepository.save(item1);
        itemRepository.save(item2);
        List<item> result = itemRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);
    }
    
    @Test
    void update() {
        //given
        item item = new item("item1", 10000, 10);
        item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();
        //when
        item updateParam = new item("item2", 20000, 30);
        itemRepository.update(itemId, updateParam);
        item findItem = itemRepository.findById(itemId);
        //then
    
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
    
}