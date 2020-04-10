package fudan.se.lab2.controller.request;

public class InviteRequest {
    //前端传参
    private String fullname;//会议全称
    private String username;//用户姓名
    private String chair;//得到当前用户姓名即为chair


    private String realname;//真实姓名用于查找
    private String inviteState;//邀请状态初始化为已邀请

    public InviteRequest() { }

    public InviteRequest(String fullname, String username,String chair) {
        this.fullname = fullname;
        this.username = username;
        this.chair = chair;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

   /* public void setUsername(String username) {
        this.username = username;
    }

    public String getInviteState() {
        return inviteState;
    }

    public void setInviteState(String inviteState) {
        this.inviteState = inviteState;
    }
*/


    public String getChair() {
        return chair;
    }

    /*public void setChair(String chair) {
        this.chair = chair;
    }*/
}
