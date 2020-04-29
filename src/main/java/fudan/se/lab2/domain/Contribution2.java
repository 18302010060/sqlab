package fudan.se.lab2.domain;

import java.util.List;

public class Contribution2 {
    private  Contribution contribution;
    private List<String> topics;
    private String topic;
    public Contribution2(){}
    public Contribution2(Contribution contribution,List<String>topics,String topic){
        this.contribution=contribution;
        this.topics=topics;
        this.topic=topic;
    }

    public Contribution getContribution() {
        return contribution;
    }

    public void setContribution(Contribution contribution) {
        this.contribution = contribution;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
