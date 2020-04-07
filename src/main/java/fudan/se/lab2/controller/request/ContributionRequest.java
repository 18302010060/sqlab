package fudan.se.lab2.controller.request;

public class ContributionRequest {

    private String meetingFullname;
    private String title;
    private String summary;
    private String path;
    private String token;

    public ContributionRequest(){}



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
