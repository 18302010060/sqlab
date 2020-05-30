package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="CONTRIBUTION")
public class Contribution {
    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;//投稿人
    private String meetingFullname;
    private String title;
    @Lob @Basic(fetch = FetchType.LAZY) @Column(columnDefinition = "text")
    private String summary;
    private String path;
    private String filename;
    private String state;//wait等待审稿 inReview开启审稿 over审稿完成  released发布结果
    private String topic;
    @ElementCollection
    private List<String> topics;

    @Lob@Basic(fetch = FetchType.LAZY) @Column(columnDefinition = "text")
    private String rebuttal;
    private Boolean rebuttalState;
    private Boolean employState;
    /*@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "contribution_id")*/





    public Contribution() {}
    public Contribution(String username, String meetingFullname, String title, String summary, String path,List<String> topics,String topic,String filename) {
        this.meetingFullname=meetingFullname;
        this.username=username;
        this.title=title;
        this.summary=summary;
        this.path=path;
        this.topics=topics;
        this.state="wait";
        this.topic=topic;
        this.filename=filename;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getTopics() {
        return topics;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }


}
