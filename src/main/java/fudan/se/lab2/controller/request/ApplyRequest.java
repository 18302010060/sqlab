package fudan.se.lab2.controller.request;

import java.util.Date;

//18302010060 黄怡清'part
public class ApplyRequest {
    private String shortname;
    private String fullname;
    private String place;
    private Date time;
    private Date deadline;
    private Date releasetime;
    private String token;


    public ApplyRequest() {}

    public ApplyRequest(   String shortname, String fullname, String place, Date time, Date deadline, Date releasetime, String token){
        this.shortname=shortname;
        this.fullname=fullname;
        this.place=place;
        this.time=time;
        this.releasetime=releasetime;
        this.deadline=deadline;
        this.token=token;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public Date getTime(){
        return time;
    }
    public void setTime(Date time){
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(Date releasetime) {
        this.releasetime = releasetime;
    }

    public String getToken(){
        return this.token;
    }

}
