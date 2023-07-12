package hello.jdbc.repository;


import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 * DataSourceUtils.getConnection()
 * DataSourceUtils.releaseConnection()
 */

@Slf4j
public class MemberRepositoryV3 {

    private final DataSource dataSource;

    public MemberRepositoryV3(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql); //values (?, ?) 부분을 매핑 해준다.
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2,member.getMoney());
            pstmt.executeUpdate(); //Statement를 통해 준비된 SQL을 커넥션을 통해 실제 데이터베이스에 전달한다.

            return member;

        } catch (SQLException e){
            log.error("db error", e);
            throw e;

        } finally{
            close(con,pstmt,null);
        }

    }



    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,memberId);
            rs = pstmt.executeQuery(); // 쿼리를 실행한 결과들을 담고있는 데이터를 ResultSet에 반환함.
            if (rs.next()) { //한번은 호출 해줘야 함 Cursor가 아무것도 안가르키고 있다가 next() 호출함으로써 다음을 지정함.
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }else{
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        } catch (SQLException e) {
            log.error("error", e);
            throw e;

        } finally {
            close(null, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,money);
            pstmt.setString( 2,memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);

        } catch (SQLException e) {
            log.error("error", e);
            throw e;

        } finally {
            close(null, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }

    }

    private void close(Connection con, Statement stmt, ResultSet rs){
        JdbcUtils.closeResultSet(rs);
//        JdbcUtils.closeConnection(con); //이제는 파라미터로 받으면 닫으면 커넥션이 종료되서 트랜잭션이 끝나버린다.
        JdbcUtils.closeStatement(stmt);
        // 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        DataSourceUtils.releaseConnection(con, dataSource);
    }


    private Connection getConnection() throws SQLException {
        // 트랜잭션 동기화를 사용하기 위해서 DataSourceUtils를 사용한다.
//        Connection con = dataSource.getConnection();
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("get connection={}, class={}",con,con.getClass());
        return con;
    }

}
