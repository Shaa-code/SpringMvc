package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
@RequiredArgsConstructor
public class JpaItemRepositoryV2 implements ItemRepository {

    private final SpringDataJpaItemRepository repository;

    @Override
    public Item save(Item item) {
        return repository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = repository.findById(itemId).orElseThrow();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());

//        Item findItem = repository.getById(itemId);
//        findItem.setItemName(updateParam.getItemName());
//        findItem.setPrice(updateParam.getPrice());
//        findItem.setQuantity(updateParam.getQuantity());
//        그냥 적어 봤는데, 이렇게 하면 그냥 객체만 뽑아낸다.
//        Optional로 빼려면 findById를 사용해야 한다.
    }

    @Override
    public Optional<Item> findById(Long id) {
        return repository.findById(id);// Optional을 포함한다.
//      return Optional.ofNullable(repository.getById(id));
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        if(StringUtils.hasText(itemName) && maxPrice != null){
//            return repository.findByItemNameLikeAndPriceLessThanEqual("%" + itemName + "%", maxPrice);
            return repository.findItems("%" + itemName + "%", maxPrice);
        }else if (StringUtils.hasText(itemName)){
            return repository.findByItemNameLike("%" + itemName + "%");
        }else if (maxPrice != null){
            return repository.findByPriceLessThanEqual(maxPrice);
        }else{
            return repository.findAll();
        }
        // 실무에서는 QueryDSL로 동적쿼리를 쓰지 이렇게 작성하지 않는다.
        // 가끔 조건이 2개 밖에 없으면 이렇게 작성할 때도 있긴하다고 한다.
    }
}
