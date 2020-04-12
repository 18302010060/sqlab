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
public class InitService {
    MeetingRepository meetingRepository;
    MeetingAuthorityRepository meetingAuthorityRepository;
    InvitationRepository invitationRepository;
    UserRepository userRepository;
    ContributionRepository contributionRepository;
    Logger logger = LoggerFactory.getLogger(InviteController.class);
    @Autowired
    public InitService(MeetingRepository meetingRepository,MeetingAuthorityRepository meetingAuthorityRepository,InvitationRepository invitationRepository,UserRepository userRepository,ContributionRepository contributionRepository){
        this.meetingAuthorityRepository = meetingAuthorityRepository;
        this.meetingRepository = meetingRepository;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.contributionRepository = contributionRepository;
    }

    public List<Meeting> showDashboard() {
        try {
            return meetingRepository.findAllByStateEqualsOrStateEquals("passed", "inManuscript");
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<Meeting> showMeetingIAppliedFor(InitRequest1 initRequest) {
        try {
            String username = initRequest.getUsername();
            String state = initRequest.getState();
            logger.info("username  "+username);
            logger.info("state  "+state);
            return meetingRepository.findAllByChairEqualsAndStateEquals(username, state);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<MeetingAuthority> meetingIParticipatedIn(InitRequest initRequest) {
        try {
            String authority = initRequest.getAuthority();

            String username = initRequest.getUsername();
            logger.info("username  "+username);
            logger.info("authority  "+authority);
            return meetingAuthorityRepository.findAllByUsernameEqualsAndAuthorityEquals(username, authority);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<Invitations> invitationInformation(InitRequest1 initRequest) {
        try {
            String username = initRequest.getUsername();
            logger.info("username  "+username);

            return invitationRepository.findAllByUsernameEqualsAndInviteStateEquals(username, "invited");
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<User> PCMemberInvitations(InitRequest request) {
        try {
         /*   Iterable<User> it = userRepository.findAll();

            List<User> user = new ArrayList<>();
            while (it.iterator().hasNext()) {
                String username = ;
                user.add(it.iterator().next());
            }
            return user;*/
         String fullname = request.getFullname();
         List<User> user1 = userRepository.findAll();
         List<User> user = new ArrayList<>();
            for (User value : user1) {
                String username = value.getUsername();
                Optional<MeetingAuthority> meetingAuthority = Optional.ofNullable(meetingAuthorityRepository.findByUsernameAndFullname(username, fullname));

                if (!meetingAuthority.isPresent()) {
                    if(!value.getUsername().equals("admin") &&!value.getPassword().equals("password")) {
                        user.add(value);
                    }
                }
            }
        return user;
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<User> invitationsResult(InitRequest2 initRequest) {
        try {
            String fullname = initRequest.getFullname();
            String inviteState = initRequest.getInviteState();
            logger.info("fullname "+fullname);
            logger.info("inviteState  "+inviteState);
            List<Invitations> invitations = invitationRepository.findAllByFullnameAndInviteState(fullname,inviteState);
            List<User> users = new ArrayList<>();
            for (Invitations invitation : invitations) {
                String username = invitation.getUsername();
                User user = userRepository.findByUsername(username);
                users.add(user);
            }

            return users;
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<MeetingAuthority> PCMemberList(InitRequest initRequest) {
        try {
            String fullname = initRequest.getFullname();
            logger.info("fullname  "+fullname );
            return meetingAuthorityRepository.findAllByUsernameEqualsAndAuthorityEquals(fullname, "PCmember");
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<Meeting> meetingApplications() {
        try {
            return meetingRepository.findAllByStateEquals("inAudit");
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<Meeting> applicationHandled(InitRequest2 initRequest) {
        try {

            String state = initRequest.getState();

            logger.info("state  "+state);

            return meetingRepository.findAllByStateEquals(state);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    //其他
    /*public List<Contribution> getAllSubmissions() {
        try {
            Iterable<Contribution> it = contributionRepository.findAll();
            List<Contribution> result = new ArrayList<>();
            while (it.iterator().hasNext()) {
                result.add(it.iterator().next());
            }

            return result;
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }*/

    public List<Contribution> getAllSubmissions(InitRequest1 request) {
        try{
            String username=request.getUsername();
            logger.info("username  "+username);

            return contributionRepository.findAllByUsername(username);


        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }


    public User getPersonalInform(InitRequest1 initRequest) {

        try {
            String username = initRequest.getUsername();
            logger.info("username  "+username);

            return userRepository.findByUsername(username);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }

    }

    public List<Meeting> getMeetingInfo(InitRequest2 initRequest) {
        try {
            String fullname = initRequest.getFullname();
            logger.info("fullname "+fullname);

            return meetingRepository.findAllByFullname(fullname);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<Contribution> getAllArticle(InitRequest2 initRequest) {
        try {
            String fullname = initRequest.getFullname();
            logger.info("fullname "+fullname);

            return contributionRepository.findAllByMeetingFullname(fullname);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public Contribution getArticleDetail(InitRequest4 initRequest){
        try {
            String username=initRequest.getUsername();
            String fullname = initRequest.getUsername();
            logger.info("username "+username);

            logger.info("fullname "+fullname);

            return contributionRepository.findContributionByUsernameAndMeetingFullname(username,fullname);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public Boolean openSubmissionn(InitRequest4 initRequest){
        String fullname = initRequest.getFullname();
        String username=initRequest.getUsername();

        logger.info("fullname "+fullname);
        logger.info("username  "+username);

        try {
            Meeting meeting=meetingRepository.findByFullname(fullname);
            logger.info("state  "+meeting.getState());
            meeting.setState("inManuscript");
            meetingRepository.save(meeting);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    public Boolean judgeWhetherPcmemberr(InitRequest initRequest){
        String fullname = initRequest.getFullname();
        String username=initRequest.getUsername();

        logger.info("fullname "+fullname);
        logger.info("username  "+username);


        try {
            MeetingAuthority meetingAuthority=meetingAuthorityRepository.findByUsernameAndFullname(username,fullname);
            String authority=meetingAuthority.getAuthority();
            if(authority.equals("PCmemeber")){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public String getMeetingStatee(InitRequest initRequest) {
        String fullname = initRequest.getFullname();
        String username = initRequest.getUsername();

        logger.info("fullname " + fullname);
        logger.info("username  " + username);

        try {
            Meeting meeting=meetingRepository.findByFullname(fullname);
            logger.info("state  "+meeting.getState());
            return meeting.getState();
        }catch (Exception e){
            return "error";
        }
    }
    }
