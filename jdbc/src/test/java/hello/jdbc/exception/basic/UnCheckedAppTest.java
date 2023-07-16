package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Slf4j
public class UnCheckedAppTest {

    @Test
    void Unchecked(){
        Controller controller = new Controller();
        assertThatThrownBy(() -> controller.request())
                .isInstanceOf(RuntimeSQLException.class);
        //isInstanceOf 역시도 Exception.class을 예외로 잡아주면 이외의 모든 예외들도 모두 다 잡아준다.
    }

    @Test
    void printEx(){
        Controller controller = new Controller();
        try{
            controller.request();
        }catch(Exception e){
//            e.printStackTrace();
            log.info("ex",e);
        }
    }

    static class Controller{
        Service service = new Service();

        public void request(){
            service.logic();
        }
    }

    static class Service{

        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic(){
             repository.call();
             networkClient.call();
        }
    }

    static class NetworkClient {
        public void call(){
            throw new RuntimeConnectException("연결 실패");
        }
    }

    static class Repository{

        public void call(){
            try{
                runSQL();
            }catch(SQLException e){
                throw new RuntimeSQLException(e);
                //잡아서 던질때, SQLException이 아닌 RuntimeException으로 던지는거다.
            }
        }

        public void runSQL() throws SQLException{
            throw new SQLException("ex");
        }
    }

    static class RuntimeConnectException extends RuntimeException{
        public RuntimeConnectException(String message){
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException{
        public RuntimeSQLException(Throwable cause) {
            super(cause);
            // cause는 예외를 포함시켜 가져간다.
        }
    }



}
