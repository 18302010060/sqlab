package fudan.se.lab2.service;

import fudan.se.lab2.controller.DiscussController;
import fudan.se.lab2.controller.InviteController;
import fudan.se.lab2.controller.request.InitRequest;
import fudan.se.lab2.controller.request.InitRequest1;
import fudan.se.lab2.controller.request.InitRequest2;
import fudan.se.lab2.controller.request.InitRequest4;

import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiscussService {
    MeetingRepository meetingRepository;
    MeetingAuthorityRepository meetingAuthorityRepository;
    InvitationRepository invitationRepository;
    UserRepository userRepository;
    ContributionRepository contributionRepository;
    DiscussionRepository discussionRepository;
    DistributionRespository distributionRespository;
    Logger logger = LoggerFactory.getLogger(DiscussController.class);
    @Autowired
    public DiscussService(MeetingRepository meetingRepository,MeetingAuthorityRepository meetingAuthorityRepository,InvitationRepository invitationRepository,UserRepository userRepository,ContributionRepository contributionRepository,DiscussionRepository discussionRepository,DistributionRespository distributionRespository){
        this.meetingAuthorityRepository = meetingAuthorityRepository;
        this.meetingRepository = meetingRepository;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.contributionRepository = contributionRepository;
        this.discussionRepository = discussionRepository;
        this.distributionRespository = distributionRespository;
    }

    public boolean discuss(String username,String discussion,long contributionId){
        try{
            Contribution contribution = contributionRepository.findContributionById(contributionId);
            String meetingFullname = contribution.getMeetingFullname();
            String title = contribution.getTitle();
            Boolean contributionState = contribution.getEmployState();
            Discussion discussion1 = new Discussion(meetingFullname,username,discussion,title,contributionId,contributionState);
            discussionRepository.save(discussion1);
            return true;}
        catch(Exception e){
            return false;
        }
    }

    //初次确认 如果已确认过，无法再次确认，没有确认过则修改评价，distribution状态变为firstConfirm,
    // 如果3个distribution状态都为firstConfirm，则contribution状态变为firstConfirm，同时帖子状态变为firstConfirm
    // 如果会议的所有投稿状态都为firstConfirm，则会议状态变为firstConfirm，可以发布结果
    public boolean firstConfirm(Long contributionId,String username,String grade,String comment,String confidence){
        try {
            Distribution distribution = distributionRespository.findDistributionByContributionIdAndUsername(contributionId, username);
            List<Discussion> discussion = discussionRepository.findAllByContributionId(contributionId);
            String meetingFullname = distribution.getFullname();//会议全称
            if (distribution.getConfirmState().equals("firstConfirm")) {//如果已经确认过
                logger.info("只能修改一次评审结果");
                return false;
            } else {//没有确认过
                distribution.setComment(comment);//改变评分
                distribution.setConfidence(confidence);
                distribution.setGrade(grade);
                distribution.setConfirmState("firstConfirm");//状态变为初次确认
                distributionRespository.save(distribution);
                logger.info("已修改评审结果");
                List<Distribution> distributionList = distributionRespository.findAllByContributionId(contributionId);//得到该帖子的所有分配
                int flag1 = 0;
                int flag2 = 0;
                for (Distribution value : distributionList) {
                    if (value.getConfirmState().equals("firstConfirm")) {
                        flag1++;
                    }
                }
                if (flag1 == 3) {//如果该帖子所有负责人已确认结果,改变投稿状态为firstConfirm
                    Contribution contribution = contributionRepository.findContributionById(contributionId);
                    contribution.setState("firstConfirm");//初次确认
                    contributionRepository.save(contribution);
                    for (Discussion value : discussion) {
                        value.setDiscussionState("firstConfirm");
                    }
                    logger.info("改投稿全部审稿人已确认评审结果");
                }
                List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(meetingFullname);//得到该会议全部投稿
                for (Contribution contribution : contributionList) {
                    if (contribution.getState().equals("firstConfirm")) {
                        flag2++;
                    }
                }
                if (flag2 == contributionList.size()) {//如果该会议全部投稿已经确认结果，则改变会议状态为firstConfirmFinished
                    Meeting meeting = meetingRepository.findByFullname(meetingFullname);
                    meeting.setState("firstConfirm");

                    logger.info("会议全部投稿结果已确认，可以发布初次结果");
                }
                return true;
            }
        }
        catch(Exception e){
            logger.info("还未进行讨论，不可确认评分");
            return false;
        }
    }
    public Boolean ifAllContributionHasBeenConfirmed(String meetingFullname){
        try {
            Meeting meeting = meetingRepository.findByFullname(meetingFullname);
            String state = meeting.getState();
            return state.equals("firstConfirm");
        }
        catch(Exception e){
            return false;
        }
    }
    public Boolean releaseFirstResult(String meetingFullname){
        try{
        Meeting meeting = meetingRepository.findByFullname(meetingFullname);
        meeting.setState("firstDiscussionResultReleased");
        List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(meetingFullname);
            for (Contribution contribution : contributionList) {
                contribution.setState("firstDiscussionResultReleased");
            }
        return true;}
        catch(Exception e){
            return false;
        }
    }


   public Boolean openFirstDiscussion(String meetingFullname){
        try{
        Meeting meeting = meetingRepository.findByFullname(meetingFullname);
        List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(meetingFullname);
        int flag = 0;
       for (Contribution contribution : contributionList) {
           if (contribution.getState().equals("over")) {
               flag++;
           }
       }
       logger.info("flag:  "+flag);
        if(flag == contributionList.size()){
            logger.info("所有稿件已评分完毕，可以开启讨论");
            meeting.setState("inFirstDiscussion");//会议状态改变
            meetingRepository.save(meeting);
            for (Contribution contribution : contributionList) {//投稿状态改变
                contribution.setState("inFirstDiscussion");
                contributionRepository.save(contribution);
            }

            return true;
        }
        else{
            logger.info("有pcmember还未完成打分");
            return false;
        }}
        catch(Exception e){
            return false;
        }
   }

    public Boolean rebuttal(Long id,String rebuttal){
        try {
            Contribution contribution=contributionRepository.findContributionById(id);
            if(contribution.getRebuttalState()){
                logger.info("论文已经rebuttal");
                return false;
            }else {
                contribution.setRebuttal(rebuttal);
                contribution.setRebuttalState(true);
                contributionRepository.save(contribution);
                return true;

            }
        }catch (Exception e){
            logger.info("error信息："+e.getMessage());
            return false;
        }

    }

    public List<Contribution> showContributionByMeetingFullnameAndUsername(String meetingFullname,String username){
       List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(meetingFullname);
       List<Contribution> contributionList1 = new ArrayList<>();
        for (Contribution contribution : contributionList) {//对于所有投稿
            Long id = contribution.getId();
            List<Distribution> distributionList = distributionRespository.findAllByContributionId(id);//得到负责该投稿的人
            for (Distribution distribution : distributionList) {
                String username1 = distribution.getUsername();//得到负责人的用户名
                if (username.equals(username1)) {//如果username相等
                    contributionList1.add(contribution);//则可见该投稿
                }
            }
        }
       return contributionList1;

    }

    public List<Contribution> showContributionByMeetingFullnameAndEmployState(String meetingFullname,String username,Boolean employState){
       List<Contribution> contributionList = showContributionByMeetingFullnameAndUsername(meetingFullname,username);
       List<Contribution> contributionList1 = new ArrayList<>();
        for (Contribution contribution : contributionList) {
            if (contribution.getEmployState()==employState) {
                contributionList1.add(contribution);
            }
        }
        return contributionList1;

    }
    public List<Contribution> showContributionByMeetingFullnameAndState(String meetingFullname,String username,String state){
        List<Contribution> contributionList = showContributionByMeetingFullnameAndUsername(meetingFullname,username);
        List<Contribution> contributionList1 = new ArrayList<>();
        for (Contribution contribution : contributionList) {
            if (contribution.getState().equals(state)) {
                contributionList1.add(contribution);
            }
        }
        return contributionList1;

    }

    public List<Discussion> showDiscussion(Long contributionId){
        return discussionRepository.findAllByContributionId(contributionId);
    }
    public List<Contribution> showContributionByMeetingFullnameAndState(String meetingFullname,String state){
        return contributionRepository.findAllByMeetingFullnameAndState(meetingFullname,state);
    }





}
