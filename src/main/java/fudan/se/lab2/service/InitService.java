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

    //查询状态为passed和inManuscript的会议信息
    public List<Meeting> showDashboard() {
        try {
            return meetingRepository.findAllByStateEqualsOrStateEquals("passed", "inManuscript");
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    //根据username和state查询会议信息
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

    //根据username和会议身份查询会议信息
    public List<Meeting> meetingIParticipatedIn(InitRequest initRequest) {
        try {
            String authority = initRequest.getAuthority();
            String username = initRequest.getUsername();
            List<MeetingAuthority> meetingAuthority = meetingAuthorityRepository.findAllByUsernameAndAuthority(username,authority);
            List<Meeting> meetings = new ArrayList<>();
            for (MeetingAuthority value : meetingAuthority) {
                String fullname = value.getFullname();
                Meeting meeting = meetingRepository.findByFullname(fullname);
                meetings.add(meeting);

            }

            logger.info("username  "+username);
            logger.info("authority  "+authority);
            return meetings;
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    //根据username查询邀请状态为invited的会议
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

    //根据fullname得到所有的邀请信息 List<User>
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

    //根据fullname和邀请状态得到该会议的不同状态成员的信息
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

    //根据fullname得到当前会议中的PCmember List<MeetingAuthority>
    public List<MeetingAuthority> PCMemberList(InitRequest initRequest) {
        try {
            String fullname = initRequest.getFullname();
            logger.info("fullname  "+fullname );
            return meetingAuthorityRepository.findAllByFullnameAndAuthority(fullname, "PCmember");
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    //得到所有的状态为inAudit的会议
    public List<Meeting> meetingApplications() {
        try {
            return meetingRepository.findAllByStateEquals("inAudit");
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    //根据state 得到所有的状态为state的会议
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


    //根据username得到该会议的所有的投稿信息
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

    //根据username得到该用户信息
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

    //根据fullname得到该会议信息
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

    //根据fullname得到会议的全部投稿信息
    public List<Contribution> getAllArticle(InitRequest4 initRequest) {
        try {
            String fullname = initRequest.getFullname();
            String username=initRequest.getUsername();
            logger.info("fullname "+fullname);
            logger.info("username  "+username);

            return contributionRepository.findAllByMeetingFullname(fullname);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    //根据username和fullname得到具体投稿信息
    public List<Contribution> getArticleDetail(InitRequest4 initRequest){
        try {
            String username=initRequest.getUsername();
            String fullname = initRequest.getFullname();
            logger.info("username "+username);

            logger.info("fullname "+fullname);

            return contributionRepository.findAllContributionByUsernameAndMeetingFullname(username,fullname);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    //开启投稿
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

    //根据username和fullname得到是否为PCmember
    public Boolean judgeWhetherPcmemberr(InitRequest initRequest){
        String fullname = initRequest.getFullname();
        String username=initRequest.getUsername();

        logger.info("fullname "+fullname);
        logger.info("username  "+username);


        try {
            MeetingAuthority meetingAuthority=meetingAuthorityRepository.findByUsernameAndFullname(username,fullname);
            String authority=meetingAuthority.getAuthority();
            if(authority.equals("PCmember")){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    //根据username和fullname得到会议的状态
    public String getMeetingStatee(InitRequest initRequest) {
        String fullname = initRequest.getFullname();
        String username = initRequest.getUsername();

        logger.info("fullname " + fullname);
        logger.info("username  " + username);
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
        logger.info("无法得到审核结果");
        logger.info("无法得到审核结果");
        logger.info("无法得到审核结果");
        logger.info("无法得到审核结果");


        try {
            Meeting meeting=meetingRepository.findByFullname(fullname);
            logger.info("state  "+meeting.getState());
            return meeting.getState();
        }catch (Exception e){
            return "error";
        }
    }

}
