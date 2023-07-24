package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class JpaItemRepository implements ItemRepository {

    private final EntityManager em;

    public JpaItemRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Item save(Item item) {
        em.persist(item); //Item 객체의 매핑 정보를 토대로 save를 진행해 준다.
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = em.find(Item.class, itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        // 저장을 또 해줘야할 것 같은데, 따로 리포지토리에 저장을 하지 않아도 된다.
        // 트랜잭션이 끝날 때, JPA가 Update 쿼리를 만들어서 날린다.
        // 내부 로직에 대한 자세한 내용은 JPA강의에서 배운다.
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);// 클래스 먼저, 다음은 PK
        return Optional.ofNullable(item);

    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        //하나를 조회할 때는 id를 조회하면 된다.
        //여러가지 조건을 복잡하게 조회할 때는 SQL과 95%정도 비슷한 JPQL이란걸 사용한다.
        //객체를 다루는 언어이다.

        String jpql = "select i from Item i";

        Integer maxPrice = cond.getMaxPrice();
        String itemName = cond.getItemName();

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            jpql += " where";
        }

        boolean andFlag = false;

        if (StringUtils.hasText(itemName)) {
            jpql += " i.itemName like concat('%',:itemName,'%')";
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                jpql += " and";
            }
            jpql += " i.price <= :maxPrice";
        }

        log.info("jpql={}", jpql);

        TypedQuery<Item> query = em.createQuery(jpql, Item.class);

        if (StringUtils.hasText(itemName)) {
            query.setParameter("itemName", itemName);
        }

        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }

        return query.getResultList();

        //이것도 동적쿼리에 약하다.
    }
}
