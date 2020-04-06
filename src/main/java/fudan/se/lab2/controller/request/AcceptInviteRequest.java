package fudan.se.lab2.controller.request;

public class AcceptInviteRequest {
    private String fullname;
    private String inviteState;
    private String token;

    public AcceptInviteRequest(String fullname, String inviteState) {
        this.fullname = fullname;
        this.inviteState = inviteState;
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
}
