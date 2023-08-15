package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member{

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;


    //기간
    private Period workPeriod;

    //주소
    private Address homeAddress;

}