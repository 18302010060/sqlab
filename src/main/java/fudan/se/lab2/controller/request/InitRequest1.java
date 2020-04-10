package fudan.se.lab2.controller.request;

public class InitRequest1 {
    private String username;//当前用户名
    private String state;//meeting I applied for 会议状态
    private String authority;//meeting I participated in 身份
    private String inviteState;
    private String fullname;//会议全称
    public InitRequest1(){}
    public InitRequest1(String username,String state){
        this.username = username;
        this.state = state;
    }
    public InitRequest1(String username){
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getInviteState() {
        return inviteState;
    }

    public void setInviteState(String inviteState) {
        this.inviteState = inviteState;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
