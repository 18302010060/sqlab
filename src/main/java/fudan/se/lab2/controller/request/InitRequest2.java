package fudan.se.lab2.controller.request;

import fudan.se.lab2.service.InitService;

public class InitRequest2 {
    private String username;//当前用户名
    private String state;//meeting I applied for 会议状态
    private String inviteState;
    public InitRequest2(){

    }
    public InitRequest2(String username,String inviteState){
        this.username = username;
        this.inviteState = inviteState;
    }
    public InitRequest2(String state){

        this.state = state;
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


    public String getInviteState() {
        return inviteState;
    }

    public void setInviteState(String inviteState) {
        this.inviteState = inviteState;
    }


}
