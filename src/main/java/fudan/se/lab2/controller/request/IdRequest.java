package fudan.se.lab2.controller.request;

public class IdRequest {
    private Long id;

    public IdRequest(){

    }
    public IdRequest(Long id){
        this.id=id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
