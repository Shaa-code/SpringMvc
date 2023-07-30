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

            System.out.println("===============");

            em.persist(member);

            System.out.println("member.id = " + member.getId());
            System.out.println("===============");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();
    }
}
