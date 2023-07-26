package hello.springtx.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders") // SQL 문법 order by 때문에 오류가 발생하는 경우가 많아서 orders로 많이 사용한다.
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String username; //정상, 예외, 잔고부족
    private String payStatus;//대기, 완료

}
