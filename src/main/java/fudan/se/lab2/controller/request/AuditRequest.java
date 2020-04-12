package fudan.se.lab2.controller.request;

import java.util.Set;

/**
 * @author LBW
 */
//fullname，state
//18302010060黄怡清'Part
public class AuditRequest {
    //定义2个参数
    private String fullname;//会议全称
    private String state;//会议状态（inAudit/passed/rejected/投稿中）
    //private String username;


    public AuditRequest() {}
    public AuditRequest(String fullname,String state) {
        //this.username = username;
        this.state = state;
        this.fullname=fullname;
    }

    //对应的GET SET方

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

    /*public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }*/
}

