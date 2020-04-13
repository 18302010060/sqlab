package fudan.se.lab2.domain;

import javax.persistence.*;

@Entity
@IdClass(usernameAndMeetingFullname.class)
@Table(name="CONTRIBUTION")
public class Contribution {
    private static final long serialVersionUID = -6140085056226164016L;

   /* @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/


   /* @Column(unique = true)*/
    @Id
    private String username;
    @Id
    private String meetingFullname;
    private String title;
    private String summary;
    private String path;

    public Contribution() {}
    public Contribution(String username, String meetingFullname, String title, String summary, String path) {
       this.meetingFullname=meetingFullname;
       this.username=username;
       this.title=title;
       this.summary=summary;
       this.path=path;
    }

   /* public Long getId(){
        return this.id;
    }*/

    public String getUsername(){
        return username;
    }

    public String getMeetingFullname(){
        return  meetingFullname;
    }

    public String getTitle(){
        return  title;
    }

    public String getSummary(){
        return summary;
    }

    public String getPath(){
        return path;
    }
}
