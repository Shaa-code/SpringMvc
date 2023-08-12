package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //persistence.xml에 있는 <persistence-unit name="hello"> 내부의 정보를 가져온다.
        EntityTransaction tx = em.getTransaction();
        //설정 내용을 토대로 EntityManager를 생성한다.
        tx.begin();

        try{
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            member1.setTeam(team);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            member2.setTeam(team2);

            em.flush();
            em.clear();

            Member member = em.find(Member.class, member1.getId());
            System.out.println("team = " + member.getTeam().getClass());



            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally{
            em.close();
        }
        emf.close();
    }
}