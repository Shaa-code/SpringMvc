package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR", // Generator의 이름이다.
        sequenceName = "MEMBER_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1) //allocationSize 호출 1번에 몇 증가?
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name")
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    @Transient
    private int temp;

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
