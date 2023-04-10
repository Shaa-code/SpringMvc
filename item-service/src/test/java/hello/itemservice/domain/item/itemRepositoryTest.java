package hello.itemservice.domain.item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class itemRepositoryTest {

    itemRepository itemRepository = new itemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }
    @Test
    void save(){
        //given
        Item item = new Item("선물",1000,1);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(1L);
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll(){
        //given
        Item item1 = new Item("선물1",1000,1);
        Item item2 = new Item("선물2",2000,2);
        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1,item2);
    }

    @Test
    void updateItem(){
        //given
        Item item = new Item("선물",1000,1);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        Item editedItem = new Item("선물2",10000,2);
        itemRepository.update(itemId,editedItem);

        //then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem).isEqualTo(item);

        assertThat(findItem.getItemName()).isEqualTo(editedItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(editedItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(editedItem.getQuantity());

    }
}
