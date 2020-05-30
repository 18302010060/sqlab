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
//18302010071陈淼'Part + 18302010077张超'Part
@Entity
public class Discussion {

    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String meetingFullname;
    private String username;
    private String discussion;

    public Discussion() {
    }

    public Discussion(String meetingFullname, String username, String discussion) {
        this.meetingFullname = meetingFullname;
        this.username = username;
        this.discussion = discussion;
    }

    public String getMeetingFullname() {
        return meetingFullname;
    }

    public void setMeetingFullname(String meetingFullname) {
        this.meetingFullname = meetingFullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }
}
