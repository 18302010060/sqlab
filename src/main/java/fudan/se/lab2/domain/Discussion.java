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
    private String username;//发帖人
    private String discussion;//讨论内容
    private String discussionState;//帖子状态
    private String title;//稿件名称
    private Long contributionId;//投稿id
    private Boolean employState;
    private String reply;//被回复人
    private Date discussTime; //回复时间



    public Discussion() {
    }

    public Discussion(String meetingFullname, String username, String discussion,String title,Long contributionId,Boolean employState,String reply,Date discussTime) {
        this.meetingFullname = meetingFullname;
        this.username = username;
        this.discussion = discussion;
        this.discussionState = "inFirstDiscussion";
        this.title = title;
        this.contributionId = contributionId;
        this.employState = employState;
        this.reply = reply;
        this.discussTime = discussTime;
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

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getDiscussTime() {
        return discussTime;
    }

    public void setDiscussTime(Date discussTime) {
        this.discussTime = discussTime;
    }
}
