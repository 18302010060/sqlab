package fudan.se.lab2.domain;

import java.util.List;

public class Contribution3 {
    private  Contribution contribution;
    private List<String> topics;
    private String topic;
    private List<Author> authors;
    public Contribution3(){}
    public Contribution3(Contribution contribution,List<String>topics,String topic,List<Author> authors){
        this.contribution=contribution;
        this.topics=topics;
        this.topic=topic;
        this.authors=authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Author> getAuthors() {
        return authors;
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
