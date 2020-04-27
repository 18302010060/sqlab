package fudan.se.lab2.domain;

import javax.persistence.*;


@Entity
@Table(name="DISTRIBUTION")
public class Distribution {
    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullname;
    private String username;
    private Long contributionId;
    private String title;
    private Boolean state=false;//false该审稿人未审稿 true该审稿人已经审稿完成
    private String grade;
    private String comment;
    private String confidence;


    public Distribution(){}
    public Distribution(String fullname,String username,Long contributionId,String title){
        this.fullname=fullname;
        this.username=username;
        this.contributionId=contributionId;
        this.title=title;
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

    public void setReview(String grade,String comment,String confidence){
        this.grade=grade;
        this.comment=comment;
        this.confidence=confidence;
        this.state=true;
    }
}
