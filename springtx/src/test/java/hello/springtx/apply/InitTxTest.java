package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;


@SpringBootTest
public class InitTxTest {

    @Autowired Hello hello;

    @Test
    void go(){
        hello.initV1();
    }

    @Test
    void go2(){
//        hello.initV2();
    }


    @TestConfiguration
    static class InitTxTestConfig{
        @Bean
        Hello hello(){
            return new Hello();
        }
    }

    @Slf4j
    static class Hello{

        @Transactional
        @PostConstruct
        public void initV1(){
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init @PostConstruct tx active = {}",isActive);
        }

    }
}