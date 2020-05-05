package fudan.se.lab2.domain;

import java.io.Serializable;

public class idAndUsername implements Serializable {
    private Long id;
    private String username;
    private String email;
    public idAndUsername(){}
    public idAndUsername(Long id,String username,String email){
        this.id=id;
        this.username=username;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
