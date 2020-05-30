package fudan.se.lab2.service;

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
    Logger logger = LoggerFactory.getLogger(InviteController.class);
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

    public boolean discuss(String meetingFullname,String username,String discussion,long contributionId){
        try{
            Contribution contribution = contributionRepository.findContributionById(contributionId);
            String title = contribution.getTitle();
            Boolean contributionState = contribution.getEmployState();//待修改为录用结果
            Discussion discussion1 = new Discussion(meetingFullname,username,discussion,title,contributionId,contributionState);
            discussionRepository.save(discussion1);
            return true;}
        catch(Exception e){
            return false;
        }
    }
    public List<Discussion> showDiscussion(Long contributionId){
        return discussionRepository.findAllByContributionId(contributionId);


    }
    public boolean firstConfirm(Long contributionId,String username,String grade,String comment,String confidence){
        try {
            Distribution distribution = distributionRespository.findDistributionByContributionIdAndUsername(contributionId, username);
            Discussion discussion = discussionRepository.findDiscussionByContributionId(contributionId);
            String meetingFullname = distribution.getFullname();
            distribution.setComment(comment);
            distribution.setConfidence(confidence);
            distribution.setGrade(grade);
            distribution.setConfirmState("firstConfirm");
            distributionRespository.save(distribution);
            List<Distribution> distributionList = distributionRespository.findAllByContributionId(contributionId);
            int flag1 = 0;
            int flag2 = 0;
            for (Distribution value : distributionList) {
                if (value.getConfirmState().equals("firstConfirm")) {
                    flag1++;
                }
            }
            if (flag1 == 3) {
                contributionRepository.findContributionById(contributionId).setState("firstConfirm");
            }
            List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(meetingFullname);
            for (Contribution contribution : contributionList) {
                if (contribution.getState().equals("firstConfirm")) {
                    flag2++;
                }
            }
            if(flag2==contributionList.size()){
                discussion.setDiscussionState("firstDiscussionFinished");
            }
            return true;
        }
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





}
