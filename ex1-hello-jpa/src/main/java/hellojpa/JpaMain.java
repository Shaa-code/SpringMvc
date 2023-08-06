package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //persistence.xml에 있는 <persistence-unit name="hello"> 내부의 정보를 가져온다.
        EntityTransaction tx = em.getTransaction();
        //설정 내용을 토대로 EntityManager를 생성한다.
        tx.begin();

        try{
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            Team team1 = new Team();
            team1.setName("teamA");

            //Team 객체의 Member List로 쿼리
            team1.getMembers().add(member);

            em.persist(team1);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            //Member 객체의 Team으로 쿼리
            member.changeTeam(team2);

            em.persist(member);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();
    }
}