package fudan.se.lab2.controller.request;

public class InitRequest1 {
    private String username;//当前用户名
    private String state;//meeting I applied for 会议状态
    private String fullname;


    public InitRequest1(){}
    public InitRequest1(String username,String state,String fullname){
        this.username = username;
        this.state = state;
        this.fullname=fullname;
    }
    public InitRequest1(String username){
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getFullname(){
        return this.fullname;

    }

    public void setFullname(String fullname){
        this.fullname=fullname;
    }


}
