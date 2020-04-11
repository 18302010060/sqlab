package fudan.se.lab2.service;

import fudan.se.lab2.controller.InviteController;
import fudan.se.lab2.controller.request.InitRequest;
import fudan.se.lab2.controller.request.InitRequest1;
import fudan.se.lab2.controller.request.InitRequest2;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<User> PCMemberInvitations() {
        try {
            Iterable<User> it = userRepository.findAll();

            List<User> user = new ArrayList<>();
            while (it.iterator().hasNext()) {
                user.add(it.iterator().next());
            }
            return user;
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<Invitations> invitationsResult(InitRequest2 initRequest) {
        try {
            String username = initRequest.getUsername();

            String inviteState = initRequest.getInviteState();
            logger.info("username  "+username);
            logger.info("inviteState  "+inviteState);

            return invitationRepository.findAllByUsernameEqualsAndInviteStateEquals(username, inviteState);
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
    public List<Contribution> getAllSubmissions() {
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

    public List<Meeting> getMeetingInfo(InitRequest1 initRequest) {
        try {
            String fullname = initRequest.getUsername();
            logger.info("username表示fullname "+fullname);

            return meetingRepository.findByFullname(fullname);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public List<Contribution> getAllArticle(InitRequest1 initRequest) {
        try {
            String fullname = initRequest.getUsername();
            logger.info("username表示fullname "+fullname);

            return contributionRepository.findAllByMeetingFullname(fullname);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }

    public Contribution getArticleDetail(InitRequest1 initRequest){
        try {
            String fullname = initRequest.getUsername();
            String username=initRequest.getUsername();
            logger.info("username表示fullname "+fullname);

            return contributionRepository.findContributionByUsernameAndMeetingFullname(username,fullname);
        } catch (Exception e) {
            logger.info("空指针错误！！");
            return null;
        }
    }


}
