package fudan.se.lab2.controller.request;

import fudan.se.lab2.service.InitService;

public class InitRequest2 {
    private String fullname;//会议全称
    private String state;//meeting I applied for 会议状态
    private String inviteState;
    public InitRequest2(){

    }
    public InitRequest2(String fullname,String inviteState){
        this.fullname = fullname;
        this.inviteState = inviteState;
    }
    public InitRequest2(String state){

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


    public String getInviteState() {
        return inviteState;
    }

    public void setInviteState(String inviteState) {
        this.inviteState = inviteState;
    }


}
