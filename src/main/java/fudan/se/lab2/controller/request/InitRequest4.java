package fudan.se.lab2.controller.request;

public class InitRequest4 {
    private String username;
    private String fullname;

    public InitRequest4(){

    }
    public InitRequest4(String username,String fullname){
        this.username=username;
        this.fullname=fullname;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
