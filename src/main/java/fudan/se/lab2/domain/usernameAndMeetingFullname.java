package fudan.se.lab2.domain;

import java.io.Serializable;

public class usernameAndMeetingFullname implements Serializable {
    private String username;
    private String meetingFullname;
    public usernameAndMeetingFullname(){}
    public usernameAndMeetingFullname(String username,String meetingFullname){
        this.username = username;
        this.meetingFullname = meetingFullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }
}
