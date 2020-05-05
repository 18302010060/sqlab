package fudan.se.lab2.domain;

import java.io.Serializable;

public class idAndUsername implements Serializable {
    private Long id;
    private String username;
    public idAndUsername(){}
    public idAndUsername(Long id,String username){
        this.id=id;
        this.username=username;
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
