package fudan.se.lab2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

//18302010060 黄怡清'part
@Entity
@Table(name="MEETING")
public class Meeting  {

    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String shortname;//会议简称
    private Date time;//时间
    private Date releasetime;//结果发布时间;
    private Date deadline;//投稿截止日期
    private String place;//举办地点
    private String fullname;//会议全称
    private String state;//会议状态
    private String chair="";//会议申请人
    private String topic;
    @ElementCollection
    private List<String> topics;

    //@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    //private Set<Authority> authorities = new HashSet<>();


    public Meeting() {}
    public Meeting(String shortname, String fullname, Date time, String place,Date deadline,Date releasetime,String chair,List<String> topics,String topic) {
        this.shortname = shortname;
        this.place= place;
        this.fullname = fullname;
        this.deadline=deadline;
        this.releasetime=releasetime;
        this.time=time;
        this.state="inAudit";
        this.chair=chair;
        this.topics = topics;
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(Date releasetime) {
        this.releasetime = releasetime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getState(){
        return this.state;
    }

    public void setState(String state){
        this.state=state;
    }

    public String getChair(){
        return  this.chair;
    }

    public void setChair(String chair){
        this.chair=chair;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
