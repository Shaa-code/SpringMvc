package hello.jdbc.exception.basic;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Slf4j
public class UnCheckedTest {

    @Test
    void unchecked_catch(){
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw(){
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }


    /**
     * RuntimeException을 상속받은 예외는 언체크 예외가 된다.
     */
    static class MyUncheckedException extends RuntimeException{
        public MyUncheckedException(String message){
            super(message);
        }
    }

    /**
     * UnChecked 예외는
     * 예외를 잡거나, 던지지 않아도 된다.
     * 예외를 잡지 않으면 자동으로 밖으로 던진다.
     */

    static class Repository{
        // 언체크 예외는 throws를 생략해도 된다. 안붙여줘도 throws가 있는거다.
        public void recall(){
            throw new MyUncheckedException("ex");
        }
    }

    static class Service{

        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아서 처리하면 된다.
         * Intellij에서 오류를 안잡아주는 이유는 그냥 자동으로 던지기 떄문이다.
         */
        public void callThrow(){
            repository.recall();
        }

        /**
         * 예외를 잡고 싶다면 그냥 이렇게 잡아주면된다.
         */
        public void callCatch(){
            try {
                repository.recall();
            }catch (MyUncheckedException e){
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }
    }
}
