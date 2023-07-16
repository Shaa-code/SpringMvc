package hello.jdbc.exception.basic;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.net.ConnectException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

public class CheckedAppTest {

    @Test
    void checked(){
        Controller controller = new Controller();
        assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
        //isInstanceOf 역시도 Exceptio.class을 예외로 잡아주면 이외의 모든 예외들도 모두 다 잡아준다.
    }

    static class Controller{
        Service service = new Service();

        public void request() throws SQLException, ConnectException {
            service.logic();
        }
    }

    static class Service{
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() throws SQLException, ConnectException {
             repository.call();
             networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() throws ConnectException{
            throw new ConnectException("연결 실패");
        }
    }

    static class Repository{
        public void call() throws SQLException{
            throw new SQLException("ex");
        }
    }
}
