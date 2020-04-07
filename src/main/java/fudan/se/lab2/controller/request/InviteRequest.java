package fudan.se.lab2.controller.request;

public class InviteRequest {
    private String fullname;
    private String userName;
    private String inviteState;
    private String token;

    public InviteRequest() { }

    public InviteRequest(String fullname, String userName) {
        this.fullname = fullname;
        this.userName = userName;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
