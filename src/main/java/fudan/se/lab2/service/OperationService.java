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
    Logger logger = LoggerFactory.getLogger(InviteController.class);
    @Autowired
    public OperationService(MeetingRepository meetingRepository,MeetingAuthorityRepository meetingAuthorityRepository,InvitationRepository invitationRepository,UserRepository userRepository,ContributionRepository contributionRepository, DistributionRespository distributionRespository){
        this.meetingAuthorityRepository = meetingAuthorityRepository;
        this.meetingRepository = meetingRepository;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.contributionRepository = contributionRepository;
        this.distributionRespository=distributionRespository;
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
        try {
            List<MeetingAuthority> meetingAuthority = meetingAuthorityRepository.findAllByFullnameAndAuthority(fullname,"PCmember");
            List<List<String>> topics = new ArrayList<>();

            for(int i = 0;i<meetingAuthority.size();i++){
                List<String> topics1 = meetingAuthority.get(i).getTopics();
                topics.add(topics1);
            }
            return topics;
        }catch (Exception e){
            return null;
        }
    }

    //开启投稿
    public Boolean openReview(String fullname,String strategy){
        //List<String> strategy1 = JSONArray.parseArray(strategy,String.class);

        logger.info("fullname:  "+fullname);
        logger.info("strategy:  "+strategy);

        try {
            Meeting meeting=meetingRepository.findByFullname(fullname);
            logger.info("state:  "+meeting.getState());
            meeting.setState("inReview");
            //更改该会议的稿件状态为inReview
            List<Contribution> list=contributionRepository.findAllByMeetingFullname(fullname);
            for (int i=0;i<list.size();i++){
                Contribution contribution=list.get(i);
                contribution.setState("inReview");
                contributionRepository.save(contribution);
            }
            logger.info("稿件状态为开启审稿，处于审稿中（start）");
            meetingRepository.save(meeting);
            if(strategy.equals("Topic Based on Allocation Strategy")){
                distibuteContibutionsByTopicsRelevancy(fullname);
            }
            else if(strategy.equals("Average Burden Based on Allocation Strategy")){
                distributeContributionsByAverage(fullname);
            }
            return true;
        }catch (Exception e){
            logger.info("error:  "+e.getMessage());
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
            distribution.setReview(grade,comment,confidence);
            distribution.setState(true);
            Long contributionID=distribution.getContributionId();
            distributionRespository.save(distribution);
            //更改稿件的状态
            List<Distribution> list=distributionRespository.findAllByContributionId(contributionID);
            int temp=0;
            for(int i=0;i<list.size();i++){
                Distribution distribution1=list.get(i);
                Boolean state=distribution1.getState();
                if(state){
                    temp++;
                }
            }
            if(temp==3){
                Contribution contribution=contributionRepository.findContributionById(contributionID);
                contribution.setState("over");
                contributionRepository.save(contribution);
                logger.info("稿件状态变成审稿完成（over）");
            }

            return true;
        }catch (Exception e){
            logger.info("error: "+e.getMessage());
            return false;
        }
    }

    //得到当前用户当前会议的不同状态的审稿的list
    public List<Distribution> getReviewContributions(String fullname,String username,Boolean state){
        logger.info("username:  "+username);
        logger.info("fullname:  "+fullname);

        try {
            List<Distribution> list=distributionRespository.findAllByFullnameAndUsernameAndState(fullname,username,state);
            return list;
        }catch (Exception e){
            logger.info("error: "+e.getMessage());
            return null;
        }
    }

    //根据稿件id得到当前的稿件信息
    public Contribution getContribution(Long id){
        logger.info("contributionId:  "+id);
        try{
            Contribution contribution=contributionRepository.findContributionById(id);
            return contribution;

        }catch (Exception e){
            logger.info("error:  "+e.getMessage());
            return null;
        }

    }

    public Distribution getReviewResults(Long id,String username){
        logger.info("contributionId:  "+id);
        try{
            Distribution distribution=distributionRespository.findDistributionByContributionIdAndUsername(id,username);
            return distribution;

        }catch (Exception e){
            logger.info("error:  "+e.getMessage());
            return null;
        }
    }

    public List<Distribution> getContributionReviewResult(Long id){
        logger.info("contributionId:  "+id);
        try{
            List<Distribution> list=distributionRespository.findAllByContributionIdAndState(id,true);
            return list;

        }catch (Exception e){
            logger.info("error:  "+e.getMessage());
            return null;
        }
    }

    //根据topic相关度分配稿件
    public Boolean distibuteContibutionsByTopicsRelevancy(String fullname) {
        logger.info("fullname"+fullname);
        try{
        List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(fullname);//得到该会议的投稿
        List<MeetingAuthority> meetingAuthorityList = meetingAuthorityRepository.findAllByFullnameAndAuthority(fullname, "PCmember");//得到该会议的pcmember和chair
        meetingAuthorityList.add(meetingAuthorityRepository.findByFullnameAndAuthority(fullname, "chair"));

        //得到每个投稿
        for (Contribution contribution : contributionList) {
            List<MeetingAuthority> reviewers = new ArrayList<>();//创建reviwers保存该稿的审稿人
            String topic = contribution.getTopic();//得到会议topic
            List<String> topics = JSONArray.parseArray(topic, String.class);
            List<Author> authorList = contribution.getAuthors();//得到这个投稿的所有作者
            //得到每个pcmember
            for (MeetingAuthority meetingAuthority : meetingAuthorityList) {//对于该会议的全部pcmember
                String topic2 = meetingAuthority.getTopic();//得到该pcmember所负责的topic
                List<String> topics2 = JSONArray.parseArray(topic2, String.class);
                //如果pcmember所负责的topics里包含投稿某个topic,将这个pcmember加入审稿人List
                for (String s : topics) {
                    if (topics2.contains(s)) {
                        reviewers.add(meetingAuthority);
                        break;
                    }
                }
            }
            for (int j = 0; j < reviewers.size(); j++) {
                for (Author author : authorList) {
                    if (author.getUsername().equals(reviewers.get(j).getUsername())) {//如果审稿人为这篇投稿的作者，将他从审稿人中去除
                        reviewers.remove(j);
                    }
                }
            }
            for (int j = 0; j < meetingAuthorityList.size(); j++) {
                for (Author author : authorList) {
                    if (author.getUsername().equals(meetingAuthorityList.get(j).getUsername())) {//如果审稿人为这篇投稿的作者，将他从审稿人中去除
                        meetingAuthorityList.remove(j);
                    }
                }
            }
            List<Integer> list = new ArrayList();
            if (reviewers.size() < 3) {//在全部pcmember中随机分配
                if (meetingAuthorityList.size() < 3) {//审稿人小于3，分配失败
                    logger.info(contribution.getId() + "  符合条件的pcmember数不足，分配失败");

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

                }
            } else {//在负责该topic的pcmember中分配
                Random random = new Random();
                while (list.size() != 3) {
                    int num = random.nextInt(reviewers.size());//生成0-reviewers个数的随机数
                    if (!list.contains(num)) {
                        list.add(num);
                    }

                }
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


            }
        }
        return true;
        }
        catch (Exception e){
            logger.info("error:  "+e.getMessage());
            return false;
        }
    }



        //平均分配稿件
        public boolean distributeContributionsByAverage(String fullname){
        logger.info("fullname:  "+fullname);
        try {
            List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(fullname);//得到该会议的所有投稿
            List<MeetingAuthority> meetingAuthorityList = meetingAuthorityRepository.findAllByFullnameAndAuthority(fullname, "PCmember");//得到该会议的pcmember和chair
            meetingAuthorityList.add(meetingAuthorityRepository.findByFullnameAndAuthority(fullname, "chair"));

            for (int i = 0; i < contributionList.size(); i++) {//对于该会议的每个投稿
                Contribution contribution = contributionList.get(i);
                List<Author> authorList = contribution.getAuthors();
                for (int j = 0; j < meetingAuthorityList.size(); j++) {
                    for (Author author : authorList) {
                        if (author.getUsername().equals(meetingAuthorityList.get(j).getUsername())) {//如果审稿人为这篇投稿的作者，将他从审稿人中去除
                            meetingAuthorityList.remove(j);
                        }
                    }
                }
                if (meetingAuthorityList.size() < 3) {
                    logger.info(contribution.getId() + "  符合条件的pcmember数不足，分配失败");
                } else {
                    int num = i % meetingAuthorityList.size();//随机分配
                    MeetingAuthority meetingAuthority = meetingAuthorityList.get(num);

                    Distribution distribution = new Distribution(fullname, meetingAuthority.getUsername(), contribution.getId(), contribution.getTitle(), contribution.getUsername(), contribution.getTopic());
                    distributionRespository.save(distribution);
                    logger.info(contribution + "  分配成功");
                }
            }
            return true;
        }
        catch (Exception e){
            logger.info("error:  "+e.getMessage());
            return false;
        }

    }

}
