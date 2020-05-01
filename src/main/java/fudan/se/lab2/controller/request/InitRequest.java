package fudan.se.lab2.controller.request;

public class InitRequest {
    private String username;//当前用户名

    private String authority;//meeting I participated in 身份

    private String fullname;//会议全称
    public InitRequest(){}
    public InitRequest(String authority,String username){
        this.authority = authority;
        this.username = username;
    }
    public InitRequest(String fullname){
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
