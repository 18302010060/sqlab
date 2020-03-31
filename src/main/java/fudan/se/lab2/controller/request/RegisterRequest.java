package fudan.se.lab2.controller.request;

import java.util.Set;

/**
 * @author LBW
 */
//18302010077张超'Part
public class RegisterRequest {
    //定义6个参数
    private String username;
    private String password;
    private String email;
    private String area;
    private String unit;
    private String fullname;

    //RegisterRequest的构造函数（username + password + email + area + unit）
    public RegisterRequest() {}
    public RegisterRequest(String username, String password, String email,String area,String unit,String fullname) {
        this.username = username;
        this.password = password;
        this.email=email;
        this.area=area;
        this.unit=unit;
        this.fullname=fullname;
    }

    //对应的GET SET方法
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

