package fudan.se.lab2.service;

import com.alibaba.fastjson.JSONArray;
import fudan.se.lab2.controller.InviteController;
import fudan.se.lab2.controller.request.InitRequest4;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class OperationService {
    MeetingRepository meetingRepository;
    MeetingAuthorityRepository meetingAuthorityRepository;
    InvitationRepository invitationRepository;
    UserRepository userRepository;
    ContributionRepository contributionRepository;
    DistributionRespository distributionRespository;
    AuthorRepository authorRepository;
    Logger logger = LoggerFactory.getLogger(InviteController.class);
    @Autowired
    public OperationService(MeetingRepository meetingRepository,MeetingAuthorityRepository meetingAuthorityRepository,InvitationRepository invitationRepository,UserRepository userRepository,ContributionRepository contributionRepository, DistributionRespository distributionRespository,AuthorRepository authorRepository){
        this.meetingAuthorityRepository = meetingAuthorityRepository;
        this.meetingRepository = meetingRepository;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.contributionRepository = contributionRepository;
        this.distributionRespository=distributionRespository;
        this.authorRepository=authorRepository;
    }

    //根据会议名称返回该会议的topics
    public List<String> getTopicsByFullname(String fullname){
        try {
            Meeting meeting=meetingRepository.findByFullname(fullname);
            return meeting.getTopics();
        }catch (Exception e){
            return null;
        }
    }
    public List<List<String>> getTopicsByFullnameAndUsername(String fullname){

            List<MeetingAuthority> meetingAuthority = meetingAuthorityRepository.findAllByFullnameAndAuthority(fullname,"PCmember");
            List<List<String>> topics = new ArrayList<>();
            for (MeetingAuthority authority : meetingAuthority) {
                List<String> topics1 = authority.getTopics();
                topics.add(topics1); }
            return topics;
    }

    public Boolean openReview(String fullname,String strategy){//开启审稿

        logger.info("fullname:  "+fullname);
        logger.info("strategy:  "+strategy);

        try {
            Meeting meeting=meetingRepository.findByFullname(fullname);
            logger.info("state:  "+meeting.getState());
            if(strategy.equals("Topic Based on Allocation Strategy")){
                //distibuteContibutionsByTopicsRelevancy(fullname);
                if(distibuteContibutionsByTopicsRelevancy(fullname)){//如果分配成功，改变会议状态为审核中
                    meeting.setState("inReview");
                    //更改该会议的稿件状态为inReview
                    List<Contribution> list=contributionRepository.findAllByMeetingFullname(fullname);
                    for (Contribution contribution : list) {
                        contribution.setState("inReview");
                        contributionRepository.save(contribution);
                    }
                    logger.info("稿件状态为开启审稿，处于审稿中（start）");
                    meetingRepository.save(meeting);
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                //distributeContributionsByAverage(fullname);
                if(distributeContributionsByAverage(fullname)){
                    meeting.setState("inReview");
                    //更改该会议的稿件状态为inReview
                    List<Contribution> list=contributionRepository.findAllByMeetingFullname(fullname);
                    for (Contribution contribution : list) {
                        contribution.setState("inReview");
                        contributionRepository.save(contribution);
                    }
                    logger.info("稿件状态为开启审稿，处于审稿中（start）");
                    meetingRepository.save(meeting);
                    return true;
                }
                else{
                    return false; } }
        }catch (Exception e){
            return false;
        }
    }
    //审稿信息提交
    public Boolean setReview(Long id,String grade,String comment,String confidence){
        logger.info("id:  "+id);
        logger.info("grade:  "+grade);
        logger.info("comment:  "+comment);
        logger.info("confidence:  "+confidence);
        try {
            Distribution distribution=distributionRespository.findDistributionById(id);
            String meetingFullname = distribution.getFullname();
            List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(meetingFullname);
            Meeting meeting = meetingRepository.findByFullname(meetingFullname);
            distribution.setReview(grade,comment,confidence);
            distribution.setState(true);
            distributionRespository.save(distribution);
            //更改稿件的状态
            Long contributionID=distribution.getContributionId();
            List<Distribution> list=distributionRespository.findAllByContributionId(contributionID);
            int temp=0;
            for (Distribution distribution1 : list) {
                Boolean state = distribution1.getState();
                if (state) {
                    temp++;
                }
            }
            if(temp==3){ Contribution contribution=contributionRepository.findContributionById(contributionID);
                contribution.setState("over");
                contributionRepository.save(contribution);
                 }

            return true;
        }catch (Exception e){
            return false;
        } }
        public boolean meetingReviewIsOver(String fullname){
        List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(fullname);
        boolean state = true;
        for (Contribution contribution : contributionList) {
            if (!contribution.getState().equals("over")) {
                state = false;
                break;
            } }
        return state; }

    public List<Distribution> getReviewContributions(String fullname,String username,Boolean state){
        logger.info("username:  "+username);
        logger.info("fullname:  "+fullname);
        try {
            List<Distribution> list=distributionRespository.findAllByFullnameAndUsernameAndState(fullname,username,state);
            return list;
        }catch (Exception e){
            return null;
        } }
        //根据稿件id得到当前的稿件信息
    public Contribution getContribution(Long id){
        logger.info("contributionId:  "+id);
        try{
            Contribution contribution=contributionRepository.findContributionById(id);
            return contribution;
        }catch (Exception e){
            return null;
        } }

    public Contribution3 getContributionAndMeetingTopics(Long id){
        logger.info("contributionId:  "+id);
        try{
            Contribution contribution=contributionRepository.findContributionById(id);
            String fullname=contribution.getMeetingFullname();
            Meeting meeting=meetingRepository.findByFullname(fullname);
            List<String> topics=meeting.getTopics();
            String topic=meeting.getTopic();
            List<Author> authors=authorRepository.findAllById(id);
            Contribution3 contribution3=new Contribution3(contribution,topics,topic,authors);
            return contribution3;
        }catch (Exception e){
            return null; } }

    public Distribution getReviewResults(Long id,String username){
        logger.info("contributionId:  "+id);
        try{
            Distribution distribution=distributionRespository.findDistributionByContributionIdAndUsername(id,username);
            return distribution;

        }catch (Exception e){





            logger.info("error:  "+e.getMessage());
            return null;
        } }
    public List<Distribution> getContributionReviewResult(Long id){
        logger.info("contributionId:  "+id);
        try{
            List<Distribution> list=distributionRespository.findAllByContributionIdAndState(id,true);
            return list;
        }catch (Exception e){
            return null; } }

    //根据topic相关度分配稿件
    public Boolean distibuteContibutionsByTopicsRelevancy(String fullname) {
        logger.info("fullname"+fullname);
        boolean state = true;
        try{
        List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(fullname);//得到该会议的投稿

        List<MeetingAuthority> meetingAuthorityList = meetingAuthorityRepository.findAllByFullnameAndAuthority(fullname, "PCmember");//得到该会议的pcmember和chair

        meetingAuthorityList.add(meetingAuthorityRepository.findByFullnameAndAuthority(fullname, "chair"));
        for (Contribution contribution : contributionList) {//得到每个投稿
            List<MeetingAuthority> reviewers = new ArrayList<>();//创建reviwers保存该稿的审稿人
            String topic = contribution.getTopic();//得到会议topic
            List<String> topics = JSONArray.parseArray(topic, String.class);
            List<Author> authorList = authorRepository.findAllById(contribution.getId());//得到这个投稿的所有作者
            for (MeetingAuthority meetingAuthority : meetingAuthorityList) {//对于该会议的全部pcmember//得到每个pcmember
                String topic2 = meetingAuthority.getTopic();//得到该pcmember所负责的topic
                List<String> topics2 = JSONArray.parseArray(topic2, String.class);
                for (String s : topics) {//如果pcmember所负责的topics里包含投稿某个topic,将这个pcmember加入审稿人List
                    if (topics2.contains(s)) {
                        reviewers.add(meetingAuthority);
                        break;
                    } } }
            for (int j = 0; j < reviewers.size(); j++) {
                for (Author author : authorList) {
                    if (author.getUsername().equals(reviewers.get(j).getUsername())||contribution.getUsername().equals(reviewers.get(j).getUsername())) {//如果审稿人为这篇投稿的作者，将他从审稿人中去除
                        reviewers.remove(j);
                    } } }
            for (MeetingAuthority reviewer : reviewers) {
                logger.info("username:" + reviewer.getUsername());
            }
            for (int j = 0; j < meetingAuthorityList.size(); j++) {
                for (Author author : authorList) {
                    if (author.getUsername().equals(meetingAuthorityList.get(j).getUsername())||contribution.getUsername().equals(meetingAuthorityList.get(j).getUsername())) {//如果审稿人为这篇投稿的作者，将他从审稿人中去除
                        meetingAuthorityList.remove(j);
                    } } }
            for (MeetingAuthority meetingAuthority : meetingAuthorityList) {
                logger.info("username:" + meetingAuthority.getUsername());
            }


            List<Integer> list = new ArrayList();
            if (reviewers.size() < 3) {//在全部pcmember中随机分配
                if (meetingAuthorityList.size() < 3) {//审稿人小于3，分配失败
                    logger.info(contribution.getId() + "  符合条件的pcmember数不足，分配失败");
                    List<Distribution> distributionList = distributionRespository.findAllByFullname(fullname);
                    for (Distribution distribution : distributionList) {
                        distributionRespository.deleteById(distribution.getId());
                    }state = false;
                } else {
                    Random random = new Random();//随机分配
                    while (list.size() != 3) {
                        int num = random.nextInt(meetingAuthorityList.size());//生成0-pcmember个数的不同随机数
                        if (!list.contains(num)) {
                            list.add(num);
                        }
                    }
                    Distribution distribution1 = new Distribution(fullname, meetingAuthorityList.get(list.get(0)).getUsername(), contribution.getId(), contribution.getTitle(), contribution.getUsername(), contribution.getTopic());
                    distributionRespository.save(distribution1);
                    Distribution distribution2 = new Distribution(fullname, meetingAuthorityList.get(list.get(1)).getUsername(), contribution.getId(), contribution.getTitle(), contribution.getUsername(), contribution.getTopic());
                    distributionRespository.save(distribution2);
                    Distribution distribution3 = new Distribution(fullname, meetingAuthorityList.get(list.get(2)).getUsername(), contribution.getId(), contribution.getTitle(), contribution.getUsername(), contribution.getTopic());
                    distributionRespository.save(distribution3);
                    logger.info(contribution.getId() + "  分配成功");
                } } else {//在负责该topic的pcmember中分配
                Random random = new Random();
                while (list.size() != 3) {
                    int num = random.nextInt(reviewers.size());//生成0-reviewers个数的随机数
                    if (!list.contains(num)) {
                        list.add(num);
                    } }
                MeetingAuthority reviewer1 = reviewers.get(list.get(0));
                MeetingAuthority reviewer2 = reviewers.get(list.get(1));
                MeetingAuthority reviewer3 = reviewers.get(list.get(2));
                Distribution distribution1 = new Distribution(fullname, reviewer1.getUsername(), contribution.getId(), contribution.getTitle(), contribution.getUsername(), contribution.getTopic());
                distributionRespository.save(distribution1);
                Distribution distribution2 = new Distribution(fullname, reviewer2.getUsername(), contribution.getId(), contribution.getTitle(), contribution.getUsername(), contribution.getTopic());
                distributionRespository.save(distribution2);
                Distribution distribution3 = new Distribution(fullname, reviewer3.getUsername(), contribution.getId(), contribution.getTitle(), contribution.getUsername(), contribution.getTopic());
                distributionRespository.save(distribution3);
                logger.info(contribution + "  分配成功");
            } }
        return state; }
        catch (Exception e){
            return false;
        } }
        public boolean distributeContributionsByAverage(String fullname){ //平均分配稿件
        boolean state = true;
        logger.info("fullname:  "+fullname);
        try {
            List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(fullname);//得到该会议的所有投稿
            List<MeetingAuthority> meetingAuthorityList = meetingAuthorityRepository.findAllByFullnameAndAuthority(fullname, "PCmember");//得到该会议的pcmember和chair
            meetingAuthorityList.add(meetingAuthorityRepository.findByFullnameAndAuthority(fullname, "chair"));
            for (Contribution contribution : contributionList) {//对于该会议的每个投稿
                List<Author> authorList = authorRepository.findAllById(contribution.getId());
                for (int j = 0; j < meetingAuthorityList.size(); j++) {
                    for (Author author : authorList) {
                        if (author.getUsername().equals(meetingAuthorityList.get(j).getUsername())||contribution.getUsername().equals(meetingAuthorityList.get(j).getUsername())) {//如果审稿人为这篇投稿的作者，将他从审稿人中去除
                            meetingAuthorityList.remove(j); } } }
                if (meetingAuthorityList.size() < 3) {
                    logger.info(contribution.getId() + "  符合条件的pcmember数不足，分配失败");
                    state = false;
                } else {
                    List<Integer> list = new ArrayList();
                    Random random = new Random();
                    while (list.size() != 3) {
                        int num = random.nextInt(meetingAuthorityList.size());//生成0-reviewers个数的随机数
                        if (!list.contains(num)) {
                            list.add(num);
                        } }
                    MeetingAuthority reviewer1 = meetingAuthorityList.get(list.get(0));
                    MeetingAuthority reviewer2 = meetingAuthorityList.get(list.get(1));
                    MeetingAuthority reviewer3 = meetingAuthorityList.get(list.get(2));
                    Distribution distribution1 = new Distribution(fullname, reviewer1.getUsername(), contribution.getId(), contribution.getTitle(), contribution.getUsername(), contribution.getTopic());
                    distributionRespository.save(distribution1);
                    Distribution distribution2 = new Distribution(fullname, reviewer2.getUsername(), contribution.getId(), contribution.getTitle(), contribution.getUsername(), contribution.getTopic());
                    distributionRespository.save(distribution2);
                    Distribution distribution3 = new Distribution(fullname, reviewer3.getUsername(), contribution.getId(), contribution.getTitle(), contribution.getUsername(), contribution.getTopic());
                    distributionRespository.save(distribution3);
                    logger.info(contribution + "  分配成功");
                } }
            return state; }
        catch (Exception e){
            return false; } }
    public boolean releaseResults(String fullname) {
        try {
            Meeting meeting = meetingRepository.findByFullname(fullname);
            List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(fullname);
            meeting.setState("resultsReleased");
            meetingRepository.save(meeting);
            for (int i = 0; i < contributionList.size(); i++) {
                Contribution contribution = contributionList.get(i);
                contribution.setState("released");
                contributionRepository.save(contribution); }
            return true;
        } catch (Exception e) {
            return false; } }
    public List<Contribution> getContributionByUsernameAndState(String username,String state){
        try{
            return contributionRepository.findAllByUsernameAndState(username,state); }
        catch(Exception e){
            return null; } }
            public void getSomething(){
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("得到审稿人1");
                logger.info("得到审稿人2");
                logger.info("得到审稿人3");
                logger.info("得到审稿人4");
                logger.info("得到审稿人5");
                logger.info("得到审稿人6");
                logger.info("得到审稿人7");
                logger.info("得到审稿人8");
                logger.info("得到审稿人9");
                logger.info("得到审稿人10");
                logger.info("得到审稿人11");
                logger.info("得到审稿人12");
                logger.info("得到审稿人13");
                logger.info("得到审稿人14");
                logger.info("得到审稿人15");
                logger.info("得到审稿人16");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("得到会议投稿1");
                logger.info("得到会议投稿2");
                logger.info("得到会议投稿3");
                logger.info("得到会议投稿4");
                logger.info("得到会议投稿5");
                logger.info("得到会议投稿6");
                logger.info("得到会议投稿7");
                logger.info("得到会议投稿8");
                logger.info("得到会议投稿9");
                logger.info("得到会议投稿10");
                logger.info("得到会议投稿11");
                logger.info("得到会议投稿12");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
                logger.info("无法得到审核结果");
            }

}
