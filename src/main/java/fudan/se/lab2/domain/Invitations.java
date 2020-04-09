package fudan.se.lab2.domain;

import javax.persistence.*;

/**
 * @author LBW
 */
@Entity
@Table(name="INVITATIONS")
public class Invitations{

    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String chair = "";
    private String fullname;
    private String username;
    private String inviteState;



    public Invitations() {}
    public Invitations(String fullname, String realname) {

        this.fullname = fullname;
        this.username = realname;
        this.inviteState = "已邀请";
    }



    //对应的GET SET方法


    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
