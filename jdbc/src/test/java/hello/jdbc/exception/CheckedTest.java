package hello.jdbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;


@Slf4j
public class CheckedTest {

        @Test
        void checked_catch(){
            Service service = new Service();
            service.callCatch();
        }

        @Test
        void checked_throw(){
             Service service = new Service();
             Assertions.assertThatThrownBy(() -> service.callThrow())
                    .isInstanceOf(MyCheckedException.class);
        }

        /**
         * Exception을 상속받은 예외는 체크 예외가 된다.
         */
        static class MyCheckedException extends Exception{
            public MyCheckedException(String message){
                super(message);
            }
        }

        static class Service{
            Repository repository = new Repository();

            /**
             * 예외를 잡아서 처리하는 코드
             */

            public void callCatch(){
                try{
                    repository.call();
                }catch(MyCheckedException e){
                    log.info("예외처리, message={}",e.getMessage(),e);
                    //e는 {}가 필요없어서 마지막에 넣어주면 된다.
                }
            }

            /**
             * 예외를 잡지 않아도 된다. 자연스럽게 상위로 넘어간다.
             * 체크 예외와 다르게 throws 예외 선언을 하지 않아도 된다.
             */
            public void callThrow() throws MyCheckedException {
                repository.call();
            }
        }


    /**
     * 체크 예외를 밖으로 던지는 코드
     * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야한다.
     * @throws MyCheckedException
     */
        static class Repository{
            public void call() throws MyCheckedException {
                throw new MyCheckedException("ex");
        }
    }
}
