package fudan.se.lab2.controller.request;

public class ContributionRequest {

    private String meetingFullname;
    private String title;
    private String summary;
    private String path;
    private String token;

    public ContributionRequest(){}

    public ContributionRequest(String meetingFullname, String title, String summary, String path) {
        this.meetingFullname = meetingFullname;
        this.title = title;
        this.summary = summary;
        this.path = path;
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

    public String getToken(){
        return token;
    }
}
