package fudan.se.lab2.domain;

import javax.persistence.*;


@Entity
@Table(name="DISTRIBUTION")
public class Distribution {
    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullname;//会议名称
    private String username;//审稿人
    private String contributor;//投稿人
    private Long contributionId;
    private String title;
    private String topic;
    private Boolean state=false;//false该审稿人未审稿 true该审稿人已经审稿完成
    private String confirmState;
    private String grade;
    @Lob @Basic(fetch = FetchType.LAZY) @Column(columnDefinition = "text")
    private String comment;
    private String confidence;



    public Distribution(){}
    public Distribution(String fullname,String username,Long contributionId,String title,String contributor,String topic){
        this.fullname=fullname;
        this.username=username;
        this.contributionId=contributionId;
        this.title=title;
        this.contributor=contributor;
        this.topic=topic;
        this.confirmState = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public Long getContributionId() {
        return contributionId;
    }

    public void setContributionId(Long contributionId) {
        this.contributionId = contributionId;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getState() {
        return state;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public void setReview(String grade, String comment, String confidence){
        this.grade=grade;
        this.comment=comment;
        this.confidence=confidence;
        this.state=true;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public String getConfirmState() {
        return confirmState;
    }

    public void setConfirmState(String confirmState) {
        this.confirmState = confirmState;
    }
}
