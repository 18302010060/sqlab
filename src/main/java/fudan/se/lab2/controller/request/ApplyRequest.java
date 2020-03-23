package fudan.se.lab2.controller.request;

import java.util.Date;

//18302010060 黄怡清'part
public class ApplyRequest {
    private String shortname;
    private String fullname;
    private Date time;
    private String place;
    private Date deadline;
    private Date releasetime;


    public ApplyRequest() {}


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
}
