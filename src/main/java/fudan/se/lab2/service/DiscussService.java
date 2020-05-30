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

    public boolean discuss(String meetingFullname,String username,String discussion){
        try{
        Discussion discussion1 = new Discussion(meetingFullname,username,discussion);
        discussionRepository.save(discussion1);
        return true;}
        catch(Exception e){
            return false;
        }
    }
    public boolean firstConfirm(Long contributionId,String username ,String meetingFullname,String grade,String comment,String confidence){
        try {
            Distribution distribution = distributionRespository.findDistributionByContributionIdAndUsername(contributionId, username);
            distribution.setComment(comment);
            distribution.setConfidence(confidence);
            distribution.setGrade(grade);
            distribution.setConfirmState("firstConfirm");
            distributionRespository.save(distribution);
            List<Distribution> distributionList = distributionRespository.findAllByContributionId(contributionId);
            int flag = 0;
            for (Distribution value : distributionList) {
                if (value.getConfirmState().equals("firstConfirm")) {
                    flag++;
                }
            }
            if (flag == 3) {
                contributionRepository.findContributionById(contributionId).setState("firstConfirm");
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public boolean allContributionHasBeenConfirmed(String meetingFullname){
        try {
            boolean state = true;
            List<Contribution> contributionList = contributionRepository.findAllByMeetingFullname(meetingFullname);
            for (Contribution contribution : contributionList) {
                if (!contribution.getState().equals("firstConfirm")) {
                    state = false;
                    break;
                }
            }
            return state;
        }
        catch(Exception e){
            return false;
        }

    }





}
