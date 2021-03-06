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
    public boolean discuss(Long contributionId,String username,String comment,String subusername,String subcomment,String responseUsername,String time,String subtime,String mainOrSub){
        try{
            Contribution contribution = contributionRepository.findContributionById(contributionId);
            String meetingFullname = contribution.getMeetingFullname();
            String title = contribution.getTitle();
            Boolean contributionState = contribution.getEmployState();
            String discussionState = "inFirstConfirm";
            if(contribution.getRebuttalState()){
                discussionState = "inSecondConfirm";
            }

            Discussion discussion1 = new Discussion(meetingFullname,username,comment,title,contributionId,contributionState,subusername,time,subcomment,responseUsername,subtime,mainOrSub,discussionState);
            discussionRepository.save(discussion1);
            return true;}
        catch(Exception e){
            return false;
        }
    }

    //???????????? ???????????????????????????????????????????????????????????????????????????distribution????????????firstConfirm,
    // ??????3???distribution????????????firstConfirm??????contribution????????????firstConfirm???????????????????????????firstConfirm
    // ???????????????????????????????????????firstConfirm????????????????????????firstConfirm?????????????????????
    public String firstConfirm(Long contributionId,String username,String grade,String comment,String confidence,String discussionState){
        try {
            Distribution distribution = distributionRespository.findDistributionByContributionIdAndUsername(contributionId, username);
            String meetingFullname = distribution.getFullname();//????????????
            if (distribution.getConfirmState().equals(discussionState)) {//?????????????????????
                logger.info("??????????????????????????????");
                return "??????????????????????????????";
            }
            else{
                if(discussionState.equals("firstConfirm")&&ifConfirmIsAvailable(username,contributionId,"inFirstConfirm")){
                    confirm(contributionId,grade,comment,confidence,discussionState,distribution);
                    changeMeetingStateToFirstConfirm(discussionState,meetingFullname);
                    return "????????????";
                }
                else if(discussionState.equals("secondConfirm")&&ifConfirmIsAvailable(username,contributionId,"inSecondConfirm")){
                    confirm(contributionId,grade,comment,confidence,discussionState,distribution);
                    return "????????????";
                }
                else{
                    logger.info("????????????????????????????????????????????????");
                    return "??????????????????????????????????????????";
                }
            }

        }
        catch(Exception e){
            logger.info("???????????????????????????????????????");
            return "????????????";
        }
    }

    public void changeMeetingStateToFirstConfirm(String discussionState, String meetingFullname) {
        int flag2 = 0;
        List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(meetingFullname);//???????????????????????????
        for (Contribution contribution : contributionList) {
            if (contribution.getState().equals(discussionState)) {
                flag2++;
            }
        }
        if (flag2 == contributionList.size()) {//????????????????????????????????????????????????????????????????????????firstConfirmFinished
            Meeting meeting = meetingRepository.findByFullname(meetingFullname);
            meeting.setState(discussionState);
            meetingRepository.save(meeting);

            logger.info("????????????????????????????????????????????????????????????");
        }
    }

    public void confirm(Long contributionId, String grade, String comment, String confidence, String discussionState, Distribution distribution) {
        distribution.setComment(comment);//????????????
        distribution.setConfidence(confidence);
        distribution.setGrade(grade);
        distribution.setConfirmState(discussionState);//????????????????????????
        distributionRespository.save(distribution);
        logger.info("?????????????????????");
        List<Distribution> distributionList = distributionRespository.findAllByContributionId(contributionId);//??????????????????????????????
        int flag1 = 0;
        for (Distribution value : distributionList) {
            if (value.getConfirmState().equals(discussionState)) {
                flag1++;
            }
        }
        if (flag1 == 3) {//?????????????????????????????????????????????,?????????????????????firstConfirm
            Contribution contribution = contributionRepository.findContributionById(contributionId);
            contribution.setState(discussionState);//????????????
            contributionRepository.save(contribution);
            int flag2 = 0;
            for (Distribution value : distributionList) {
                if (value.getGrade().equals("2 points (accept)") || value.getGrade().equals("1 point (weak-accept)")) {
                    flag2++;
                }
            }
            if(flag2==3){
                contribution.setEmployState(true);
                contributionRepository.save(contribution);
            }
            else{
                contribution.setEmployState(false);
                contributionRepository.save(contribution);
            }
            logger.info("?????????????????????????????????????????????");
        }
    }


    public boolean ifConfirmIsAvailable(String username,Long contributionId,String discussionState){

        List<Discussion> discussionList = discussionRepository.findAllByContributionIdAndUsernameAndDiscussionState(contributionId,username,discussionState);
        List<Discussion> discussionList1 = discussionRepository.findAllByContributionIdAndSubusernameAndAndDiscussionState(contributionId,username,discussionState);
        discussionList.addAll(discussionList1);

        return discussionList != null && !discussionList.isEmpty();
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

                contribution.setState("firstDiscussionResultReleased");
                contributionRepository.save(contribution);


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
            logger.info("????????????????????????????????????????????????");
            meeting.setState("inFirstDiscussion");//??????????????????
            meetingRepository.save(meeting);
            for (Contribution contribution : contributionList) {//??????????????????
                contribution.setState("inFirstDiscussion");
                contributionRepository.save(contribution);
            }

            return true;
        }
        else{
            logger.info("???pcmember??????????????????");
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
        for (Contribution contribution : contributionList) {//??????????????????
            Long id = contribution.getId();
            List<Distribution> distributionList = distributionRespository.findAllByContributionId(id);//???????????????????????????
            for (Distribution distribution : distributionList) {
                String username1 = distribution.getUsername();//???????????????????????????
                if (username.equals(username1)&&!meetingAuthority.getAuthority().equals("chair")) {//??????username??????   ??????????????????chair
                    contributionList1.add(contribution);//??????????????????
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

    public List<List<Discussion>> showDiscussion(Long contributionId,String discussionState){
        List<Discussion> discussionList = discussionRepository.findAllByContributionIdAndMainSubAndDiscussionState(contributionId,"1",discussionState);//???????????????????????????????????????????????????
        List<List<Discussion>> allDiscussion1 = new ArrayList<>();
        for (Discussion discussion : discussionList) {//????????????
            //List allDiscussion = new ArrayList<>();//??????????????????list?????????????????????????????????list
            String time = discussion.getTime();//??????????????????

            String username = discussion.getUsername();//?????????????????????

            List<Discussion> discussionList1 = discussionRepository.findAllByContributionIdAndUsernameAndTime(contributionId,username,time);

            //allDiscussion.add(discussionList1);//?????????list??????list
            allDiscussion1.add(discussionList1);//?????????+List<??????>?????????????????????list
            for (List<Discussion> list : allDiscussion1) {
                for (Discussion discussion1 : list) {
                    logger.info("????????? " + discussion1.getComment());
                    logger.info("???????????? " + discussion1.getUsername());
                    logger.info("????????? " + discussion1.getTime());
                    logger.info("????????? " + discussion1.getSubcomment());
                    logger.info("???????????? " + discussion1.getSubusername());
                    logger.info("??????????????? " + discussion1.getSubtime());

                }
            }




        }

        return allDiscussion1;
    }

    public List<Contribution> showContributionByMeetingFullnameAndState(String meetingFullname,String state){
        return contributionRepository.findAllByMeetingFullnameAndState(meetingFullname,state);
    }


    //??????rebuttal
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
            logger.info("error?????????"+e.getMessage());
            return null;
        }

    }


    public List<Contribution> showContributionsByMeetingfullnameAndRebuttalState(String username,String meetingFullname,Boolean rebuttalState){
        try {
            Optional<Meeting> meeting = Optional.ofNullable(meetingRepository.findMeetingByFullnameAndChair(meetingFullname,username));
            if(meeting.isPresent()){//??????chair???????????????????????????
                List<Contribution> contributionList=contributionRepository
                        .findContributionsByMeetingFullnameAndStateAndEmployStateAndRebuttalState(meetingFullname,"firstDiscussionResultReleased",false,rebuttalState);
                return contributionList;
            }
            else {//???????????????PCmember???????????????
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
            logger.info("error?????????"+e.getMessage());
            return null;
        }

    }
    public List<Contribution> showContributionsSecondConfirm(String username,String meetingFullname){
        try {
            Optional<Meeting> meeting = Optional.ofNullable(meetingRepository.findMeetingByFullnameAndChair(meetingFullname,username));
            if(meeting.isPresent()){//??????chair????????????????????????????????????
                List<Contribution> contributionList=contributionRepository.findContributionsByMeetingFullnameAndState(meetingFullname,"secondConfirm");
                return contributionList;
            }
            else {//???????????????PCmember??????
                List<Distribution> distributionList=distributionRespository.findAllByFullnameAndUsername(meetingFullname,username);
                List<Contribution> contributions=new ArrayList<>();
                Contribution contribution;
                for (Distribution distribution:distributionList) {
                    Long contributionId=distribution.getContributionId();
                    contribution=contributionRepository.findContributionById(contributionId);
                    if(contribution.getState().equals("secondConfirm"))
                        contributions.add(contribution);
                }
                return contributions;
            }

        }catch (Exception e){
            logger.info("error?????????"+e.getMessage());
            return null;
        }

    }
    public Boolean releaseResults(String meetingFullname){
        List<Contribution> contributionList=contributionRepository.findContributionsByMeetingFullnameAndRebuttalState(meetingFullname,true);
        for(Contribution contribution:contributionList){
            if(contribution.getState().equals("secondConfirm")){
                logger.info("?????????????????????????????????");
            }else {
                logger.info("??????????????????????????????");
                return false;
            }
        }
        logger.info("????????????????????????");
        Meeting meeting=meetingRepository.findByFullname(meetingFullname);
        meeting.setState("secondDiscussionResultReleased");
        meetingRepository.save(meeting);
        List<Contribution> contributionList1=contributionRepository.findAllByMeetingFullname(meetingFullname);
        for(Contribution contribution:contributionList1){
            contribution.setState("secondDiscussionResultReleased");
            contributionRepository.save(contribution);
        }
        return true;

    }

    public List<Contribution> getEmployContributions(String username,Boolean employState){
        try {
            if(employState){
                List<Contribution> contributionList=contributionRepository.findContributionsByUsernameAndEmployState(username,true);
                List<Contribution> contributionList1= new ArrayList<>();
                for(Contribution contribution:contributionList){
                    if(contribution.getState().equals("secondDiscussionResultReleased")||contribution.getState().equals("firstDiscussionResultReleased")){
                        contributionList1.add(contribution);

                    }
                }
                return contributionList1;
            }
            else {
                List<Contribution> contributionList=contributionRepository.findContributionsByUsernameAndStateAndEmployState(username,"secondDiscussionResultReleased",false);
                return contributionList;
            }
        }catch (Exception e){
            logger.info("error?????????"+e.getMessage());
            return null;
        }

    }

    public List<Contribution> getInRebuttalContributions(String username){
        try {
            List<Contribution> contributionList=contributionRepository
                    .findContributionsByUsernameAndStateAndEmployState(username,"firstDiscussionResultReleased",false);
            return contributionList;
        }catch (Exception e){
            logger.info("error?????????"+e.getMessage());
            return null;
        }

    }

    public List<Contribution> getNonEditableContributions(String username){
        try {
            List<Contribution> contributionList=contributionRepository.findAllByUsername(username);
            List<Contribution> contributions=new ArrayList<Contribution>();
            for (Contribution contribution:contributionList){
                if(!contribution.getState().equals("wait")){
                    contributions.add(contribution);
                }
            }
            return contributions;
        }catch (Exception e){
            logger.info("error?????????"+e.getMessage());
            return null;
        }
    }




}
