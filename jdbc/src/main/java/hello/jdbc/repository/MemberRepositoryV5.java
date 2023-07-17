package hello.jdbc.repository;


import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;


import javax.sql.DataSource;
import java.sql.*;

/**
 * JdbcTemplate 사용
 */

@Slf4j
public class MemberRepositoryV5 implements MemberRepository {

    private final JdbcTemplate template;

    public MemberRepositoryV5(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

//    private final DataSource dataSource;
//    private final SQLExceptionTranslator exTranslator;

//    public MemberRepositoryV5(DataSource dataSource) {
//        this.dataSource = dataSource;
//        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
//    }

    //@Override는 해도되고 안해도 되는데, 넣어두면 컴파일러가 메서드가 같은지 확인하고 오류를 잡아주기 때문에 넣는게 좋다.
    @Override
    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values (?, ?)";
        template.update(sql, member.getMemberId(), member.getMoney());
        return member;
    }


    @Override
    public Member findById(String memberId) {
        String sql = "select * from member where member_id = ?";
        return template.queryForObject(sql, memberRowMapper(), memberId);
        // 객체 하나만 검색할때 사용하는 방식, sql의 결과인 ResultSet이 RowMapper()를 수행하고, 반환된 결과를 반환한다.
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMoney(rs.getInt("money"));
            return member;
        };
    }

    @Override
    public void update(String memberId, int money){
        String sql = "update member set money=? where member_id=?";
        template.update(sql, money, memberId);
    }

    @Override
    public void delete(String memberId){
        String sql = "delete from member where member_id=?";
        template.update(sql, memberId);
    }

}
