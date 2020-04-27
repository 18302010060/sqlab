package fudan.se.lab2.service;

import fudan.se.lab2.controller.InviteController;
import fudan.se.lab2.controller.request.InitRequest4;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationService {
    MeetingRepository meetingRepository;
    MeetingAuthorityRepository meetingAuthorityRepository;
    InvitationRepository invitationRepository;
    UserRepository userRepository;
    ContributionRepository contributionRepository;
    Logger logger = LoggerFactory.getLogger(InviteController.class);
    @Autowired
    public OperationService(MeetingRepository meetingRepository,MeetingAuthorityRepository meetingAuthorityRepository,InvitationRepository invitationRepository,UserRepository userRepository,ContributionRepository contributionRepository){
        this.meetingAuthorityRepository = meetingAuthorityRepository;
        this.meetingRepository = meetingRepository;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.contributionRepository = contributionRepository;
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
    public Boolean openReview(InitRequest4 initRequest4){
        String fullname=initRequest4.getFullname();
        String usrname=initRequest4.getUsername();
        logger.info("fullname:  "+fullname);
        logger.info("username:  "+usrname);

        try {
            Meeting meeting=meetingRepository.findByFullname(fullname);
            logger.info("state:  "+meeting.getState());
            meeting.setState("inReview");
            meetingRepository.save(meeting);
            return true;
        }catch (Exception e){
            logger.info("error:  "+e.getMessage());
            return false;
        }
    }

}
