package fudan.se.lab2.controller.request;

public class AcceptInviteRequest {
    private String fullname;//会议全称
    private String inviteState;//邀请状态
    private String username;//用户姓名 init参数


    private String token;

    public AcceptInviteRequest(String fullname, String inviteState,String username) {
        this.fullname = fullname;
        this.inviteState = inviteState;
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getInviteState() {
        return inviteState;
    }

    public void setInviteState(String inviteState) {
        this.inviteState = inviteState;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
