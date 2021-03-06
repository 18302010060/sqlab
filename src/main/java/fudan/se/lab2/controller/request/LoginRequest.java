package fudan.se.lab2.controller.request;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LBW
 */

//18302010071陈淼'part
public class LoginRequest {
    private String username;
    private String password;

    //LoginRequest的构造方法（username + password）
    public LoginRequest() {}
    public LoginRequest(String username,String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
