package fudan.se.lab2.domain;

import java.io.Serializable;

public class usernameAndFullname implements Serializable {
    private String username;
    private String fullname;
    public usernameAndFullname(){}
    public usernameAndFullname(String username,String fullname){
        this.username = username;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
