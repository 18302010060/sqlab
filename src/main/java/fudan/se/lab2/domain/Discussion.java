package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.Date;

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

    private String meetingFullname;//会议名称
    private String discussionState;//帖子状态
    private String title;//稿件名称
    private Boolean employState;

    private Long contributionId;//投稿id
    private String username;//发帖人
    private String comment;//讨论内容
    private String subusername;//被回复人
    private String subcomment;
    private String responseUsername;
    private String time; //回复时间
    private String subtime;
    public Discussion() {
    }

    public Discussion(String meetingFullname, String username, String comment,String title,Long contributionId,Boolean employState,String subusername,String time,String subcomment,String responseUsername,String  subtime) {
        this.meetingFullname = meetingFullname;
        this.username = username;
        this.comment = comment;
        this.discussionState = "inFirstDiscussion";
        this.title = title;
        this.contributionId = contributionId;
        this.employState = employState;
        this.subusername = subusername;
        this.time = time;
        this.subcomment = subcomment;
        this.responseUsername = responseUsername;
        this.subtime = subtime;
    }
    public Discussion(Long contributionId){
        this.contributionId = contributionId;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDiscussionState() {
        return discussionState;
    }

    public void setDiscussionState(String discussionState) {
        this.discussionState = discussionState;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getContributionId() {
        return contributionId;
    }

    public void setContributionId(Long contributionId) {
        this.contributionId = contributionId;
    }

    public Boolean getEmployState() {
        return employState;
    }

    public void setEmployState(Boolean employState) {
        this.employState = employState;
    }

    public String getSubusername() {
        return subusername;
    }

    public void setSubusername(String subusername) {
        this.subusername = subusername;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String  time) {
        this.time = time;
    }

    public String getSubcomment() {
        return subcomment;
    }

    public void setSubcomment(String subcomment) {
        this.subcomment = subcomment;
    }

    public String getResponseUsername() {
        return responseUsername;
    }

    public void setResponseUsername(String responseUsername) {
        this.responseUsername = responseUsername;
    }

    public String getSubtime() {
        return subtime;
    }

    public void setSubtime(String subtime) {
        this.subtime = subtime;
    }
}
