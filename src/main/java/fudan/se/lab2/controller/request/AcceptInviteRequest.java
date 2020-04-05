package fudan.se.lab2.controller.request;

public class AcceptInviteRequest {
    private String meetingName;
    private String inviteState;
    private String token;

    public AcceptInviteRequest(String meetingName, String inviteState) {
        this.meetingName = meetingName;
        this.inviteState = inviteState;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
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
