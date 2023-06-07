package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void AfterEach(){
        memberRepository.clearStore();
    }

    @Test
    public void save(){
        //given
        Member member = new Member();
        member.setId(1L);
        member.setUsername("신승윤");
        member.setAge(27);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(savedMember).isEqualTo(findMember);
    }

    @Test
    void findAll(){
        //given
        Member member1 = new Member("나윤권",20);
        Member member2 = new Member("나미리",23);
        Member member3 = new Member("나좋아",25);

        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);
        Member savedMember3 = memberRepository.save(member3);

        //when
        List<Member> findUsers = memberRepository.findAll();

        //then
        assertThat(findUsers.size()).isEqualTo(3);
        assertThat(findUsers).contains(savedMember1,savedMember2,savedMember3);
    }

}
