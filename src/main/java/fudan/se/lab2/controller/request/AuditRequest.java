package fudan.se.lab2.controller.request;

public class AuditRequest {

    private String fullname;
    private String state;
    private String token;

    public AuditRequest(){
    }

    public AuditRequest(int id, String state,String fullname) {
        this.fullname = fullname;
        this.state = state;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getToken() {
        return token;
    }
}
