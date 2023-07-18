package hello.itemservice.repository.memory;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = findById(itemId).orElseThrow();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }
    // 힙 메모리에 있는 값을 참조만 하는 것이기 때문에 따로 저장을 할 필요가 없다.

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();
        return store.values().stream()
                .filter(item -> {
                    if (ObjectUtils.isEmpty(itemName)) {
                        return true;
                    }
                    //itemName을 명시하지 않았으면 모두다 목록에 나와도 된다.
                    //그래서 true로 반환하는 것이다.
                    return item.getItemName().contains(itemName);
                    //있으면 이름이 있는 것만 목록에 나타낸다.
                }).filter(item -> {
                    if (maxPrice == null) {
                        return true;
                    }
                    //maxPrice에 아무것도 없으면 모두다 목록에 나오게 한다.
                    return item.getPrice() <= maxPrice;
                    //maxPrice가 있으면 파라미터로 들어온 가격보다 낮은 것만 필터되게 한다.
                })
                .collect(Collectors.toList());

        //제대로 이해를 못했었는데, 이유가, 조건의 ItemName과 객체의 ItemName을 같은 것으로 생각해서 혼선이 생겼었다.

    }

    public void clearStore() {
        store.clear();
    }

}
