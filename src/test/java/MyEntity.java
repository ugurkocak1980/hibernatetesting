import jakarta.persistence.*;

//jdk8
//import javax.persistence.*;

@Entity
@Table(name = "MyEntity")
public class MyEntity {

    @Id
    @Column
    private Long f1;

    @Column
    private String f2;

    public MyEntity(Long f1, String f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public MyEntity() {
    }

    public Long f1() {
        return f1;
    }

    public MyEntity setF1(Long f1) {
        this.f1 = f1;
        return this;
    }

    public String f2() {
        return f2;
    }

    public MyEntity setF2(String f2) {
        this.f2 = f2;
        return this;
    }
}
