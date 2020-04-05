package fudan.se.lab2.domain;

import fudan.se.lab2.service.AuthService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @author LBW
 */
@Entity
public class Invitations{

    private static final long serialVersionUID = -6140085056226164016L;


    @Column(unique = true)
    private String chair = "";
    private String meetingName;
    private String username;
    private String inviteState;



    public Invitations() {}
    public Invitations( String meetingName,String username) {

        this.meetingName = meetingName;
        this.username=username;
        this.inviteState = "已邀请";
    }



    //对应的GET SET方法


    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInviteState() {
        return inviteState;
    }

    public void setInviteState(String inviteState) {
        this.inviteState = inviteState;
    }
}
