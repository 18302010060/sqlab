package fudan.se.lab2.domain;

import javax.persistence.*;

@Embeddable
@Entity
@Table(name="AUTHOR")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long trueId;
    private Long id;

    private String username;
    private String email;
    private String unit;
    private String area;
    private Long index;



    public Author(){}
    public Author(Long id,String username,String unit,String area,String email,Long index){
        this.id=id;
        this.username=username;
        this.unit=unit;
        this.area=area;
        this.email=email;
        this.index=index;
    }

    public Long getTrueId() {
        return trueId;
    }

    public void setTrueId(Long trueId) {
        this.trueId = trueId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
