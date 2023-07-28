package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LogRepository {

    //@PersistenceContext 원래 이거 넣어줘야했는데, 스프링이 버전업이 되면서 따로 안 넣어줘도 알아서 주입해준다.
    private final EntityManager em;

//    @Transactional
    public void save(Log logMessage){
        log.info("log 저장");
        em.persist(logMessage);

        if(logMessage.getMessage().contains("로그예외")){
            log.info("log 저장시 예외 발생");
            throw new RuntimeException("예외 발생");
        }
    }

    public Optional<Log> find(String message){
        return em.createQuery("select l from Log l where l.message = :message", Log.class)
                .setParameter("message", message)
                .getResultList().stream().findAny();
        //getSingleResult를 할수도 있는데, 값이 없으면 예외를 터트린다. 그래서 이 예제에서는 사용하지 않는다.
    }


}
