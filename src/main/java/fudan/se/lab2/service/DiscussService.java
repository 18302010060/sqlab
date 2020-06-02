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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
//public Discussion(String meetingFullname, String username, String comment,String title,Long contributionId,Boolean employState,String subusername,Date time,String subcomment,String responseUsername,Date subtime)
    public boolean discuss(Long contributionId,String username,String comment,String subusername,String subcomment,String responseUsername,String time,String subtime){
        try{
            Contribution contribution = contributionRepository.findContributionById(contributionId);
            String meetingFullname = contribution.getMeetingFullname();
            String title = contribution.getTitle();
            Boolean contributionState = contribution.getEmployState();

            Discussion discussion1 = new Discussion(meetingFullname,username,comment,title,contributionId,contributionState,subusername,time,subcomment,responseUsername,subtime);
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
                        discussionRepository.save(value);
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
                    meetingRepository.save(meeting);

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
        meetingRepository.save(meeting);
        List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(meetingFullname);
            for (Contribution contribution : contributionList) {
                int flag = 0;
                contribution.setState("firstDiscussionResultReleased");
                contributionRepository.save(contribution);
                Long id = contribution.getId();
                List<Distribution> distributionList = distributionRespository.findAllByContributionId(id);
                for (Distribution distribution : distributionList) {
                    if (distribution.getGrade().equals("1") || distribution.getGrade().equals("2")) {
                        flag++;
                    }
                }
                if(flag==3){
                    contribution.setEmployState(true);
                }
                else{
                    contribution.setEmployState(false);
                }
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


    public List<Contribution> showContributionByMeetingFullnameAndUsername(String meetingFullname,String username){
       List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(meetingFullname);
       List<Contribution> contributionList1 = new ArrayList<>();
       MeetingAuthority meetingAuthority = meetingAuthorityRepository.findByUsernameAndFullname(username,meetingFullname);
        for (Contribution contribution : contributionList) {//对于所有投稿
            Long id = contribution.getId();
            List<Distribution> distributionList = distributionRespository.findAllByContributionId(id);//得到负责该投稿的人
            for (Distribution distribution : distributionList) {
                String username1 = distribution.getUsername();//得到负责人的用户名
                if (username.equals(username1)&&!meetingAuthority.getAuthority().equals("chair")) {//如果username相等   或当前用户是chair
                    contributionList1.add(contribution);//则可见该投稿
                }
            }
            if(meetingAuthority.getAuthority().equals("chair")){
                contributionList1.add(contribution);
            }
        }
       return contributionList1;

    }

    public List<Contribution> showContributionByMeetingFullnameAndEmployState(String meetingFullname,Boolean employState){
       /*List<Contribution> contributionList = showContributionByMeetingFullnameAndUsername(meetingFullname,username);
       List<Contribution> contributionList1 = new ArrayList<>();
        for (Contribution contribution : contributionList) {
            if (contribution.getEmployState()==employState) {
                contributionList1.add(contribution);
            }
        }
        return contributionList1;*/
        return contributionRepository.findAllByMeetingFullnameAndEmployState(meetingFullname,employState);


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

    public List<List> showDiscussion(Long contributionId){
        List<Discussion> discussionList = discussionRepository.findAllBySubcomment("");//找出所有主贴，即回复内容为空的帖子

        List<List> allDiscussion1 = new ArrayList<List>();
        for (Discussion discussion : discussionList) {//遍历主贴
            List allDiscussion = new ArrayList<>();//创建一个新的list以保存主贴和主贴的回复list
            String time = discussion.getTime();//得到主贴时间
            String username = discussion.getUsername();//得到主贴发帖人
            List<Discussion> discussionList1 = discussionRepository.findAllByContributionIdAndUsernameAndTime(contributionId, username, time);//找到当前讨论下，该用户在当前时间下的所有回帖
            allDiscussion.add(discussion);
            allDiscussion.add(discussionList1);
            allDiscussion1.add(allDiscussion);


        }

        return allDiscussion1;
    }
    public List<Contribution> showContributionByMeetingFullnameAndState(String meetingFullname,String state){
        return contributionRepository.findAllByMeetingFullnameAndState(meetingFullname,state);
    }


    //关于rebuttal
    public Boolean rebuttal(Long id,String rebuttal){
        Contribution contribution=contributionRepository.findContributionById(id);
        contribution.setRebuttal(rebuttal);
        contribution.setRebuttalState(true);
        contributionRepository.save(contribution);
        return true;
    }
    public List<Contribution> showContributionsByUsernameAndRebuttalState(String username,Boolean rebuttalState){
        try {
            List<Contribution> contributionList=contributionRepository
                    .findContributionsByUsernameAndStateAndEmployStateAndRebuttalState(username,"firstDiscussionResultReleased",false,rebuttalState);
            return contributionList;
        }catch (Exception e){
            logger.info("error信息："+e.getMessage());
            return null;
        }

    }


    public List<Contribution> showContributionsByMeetingfullnameAndRebuttalState(String username,String meetingFullname,Boolean rebuttalState){
        try {
            Optional<Meeting> meeting = Optional.ofNullable(meetingRepository.findMeetingByFullnameAndChair(meetingFullname,username));
            if(meeting.isPresent()){//对于chair返回所有未录取稿件
                List<Contribution> contributionList=contributionRepository
                        .findContributionsByMeetingFullnameAndStateAndEmployStateAndRebuttalState(meetingFullname,"firstDiscussionResultReleased",false,rebuttalState);
                return contributionList;
            }
            else {//对该会议的PCmember返回审稿的
                List<Distribution> distributionList=distributionRespository.findAllByFullnameAndUsername(meetingFullname,username);
                List<Contribution> contributions=new ArrayList<>();
                Contribution contribution;
                for (Distribution distribution:distributionList) {
                    Long contributionId=distribution.getContributionId();
                    contribution=contributionRepository.findContributionById(contributionId);
                    if(contribution.getState().equals("firstDiscussionResultReleased")&&contribution.getRebuttalState()==rebuttalState&&!contribution.getEmployState())
                    contributions.add(contribution);
                }
                return contributions;
            }

        }catch (Exception e){
            logger.info("error信息："+e.getMessage());
            return null;
        }

    }
    public Boolean releaseResults(String meetingFullname){
        List<Contribution> contributionList=contributionRepository.findContributionsByMeetingFullnameAndRebuttalState(meetingFullname,true);
        for(Contribution contribution:contributionList){
            if(contribution.getState().equals("secondConfirm")){
                logger.info("该稿件已经二次讨论完成");
            }else {
                logger.info("该稿件二次讨论未完成");
                return false;
            }
        }
        logger.info("可以进行结果发布");
        Meeting meeting=meetingRepository.findByFullname(meetingFullname);
        meeting.setState("secondDiscussionResultReleased");
        meetingRepository.save(meeting);
        for(Contribution contribution:contributionList){
            contribution.setState("secondDiscussionResultReleased");
            contributionRepository.save(contribution);
        }
        return true;

    }

    public List<Contribution> getEmployContributions(String username,Boolean employState){
        try {
            if(employState){
            List<Contribution> contributionList=contributionRepository.findContributionsByUsernameAndEmployState(username,true);
            return contributionList;
            }
            else {
                List<Contribution> contributionList=contributionRepository.findContributionsByUsernameAndStateAndEmployState(username,"secondDiscussionResultReleased",false);
                return contributionList;
            }
        }catch (Exception e){
            logger.info("error信息："+e.getMessage());
            return null;
        }

    }

    public List<Contribution> getInRebuttalContributions(String username){
        try {
            List<Contribution> contributionList=contributionRepository
                    .findContributionsByUsernameAndStateAndEmployState(username,"firstDiscussionResultReleased",false);
            return contributionList;
        }catch (Exception e){
            logger.info("error信息："+e.getMessage());
            return null;
        }

    }






}
