package fudan.se.lab2.controller.request;

public class InviteRequest {
    private String meetingName;
    private String userName;
    private String inviteState;
    private String token;

    public InviteRequest() { }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
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
