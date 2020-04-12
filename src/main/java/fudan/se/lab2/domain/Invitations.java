package fudan.se.lab2.domain;

import javax.persistence.*;

/**
 * @author LBW
 */
@Entity
@IdClass(usernameAndFullname.class)
@Table(name="INVITATIONS")
public class Invitations{

    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    private String fullname;
    @Id
    private String username;

    private String chair = "";
    private String inviteState;



    public Invitations() {}
    public Invitations(String fullname, String realname) {

        this.fullname = fullname;
        this.username = realname;
        this.inviteState = "invited";
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
