package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.InitRequest;
import fudan.se.lab2.controller.request.InitRequest1;
import fudan.se.lab2.controller.request.InitRequest2;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
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

    public List<Meeting> showDashboard() {
        try {
            return meetingRepository.findAllByStateEqualsAndStateEquals("passed", "inManuscript");
        } catch (Exception e) {
            return null;
        }
    }

    public List<Meeting> showMeetingIAppliedFor(InitRequest1 initRequest) {
        try {
            String username = initRequest.getUsername();

            String state = initRequest.getState();
            return meetingRepository.findAllByChairEqualsAndStateEquals(username, state);
        } catch (Exception e) {
            return null;
        }
    }

    public List<MeetingAuthority> meetingIParticipatedIn(InitRequest initRequest) {
        try {
            String authority = initRequest.getAuthority();

            String username = initRequest.getUsername();
            return meetingAuthorityRepository.findAllByUsernameEqualsAndAuthorityEquals(username, authority);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Invitations> invitationInformation(InitRequest1 initRequest) {
        try {
            String username = initRequest.getUsername();

            return invitationRepository.findAllByUsernameEqualsAndInviteStateEquals(username, "invited");
        } catch (Exception e) {
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
            return null;
        }
    }

    public List<Invitations> invitationsResult(InitRequest2 initRequest) {
        try {
            String username = initRequest.getUsername();

            String inviteState = initRequest.getInviteState();
            return invitationRepository.findAllByUsernameEqualsAndInviteStateEquals(username, inviteState);
        } catch (Exception e) {
            return null;
        }
    }

    public List<MeetingAuthority> PCMemberList(InitRequest initRequest) {
        try {
            String fullname = initRequest.getFullname();

            return meetingAuthorityRepository.findAllByUsernameEqualsAndAuthorityEquals(fullname, "PCmember");
        } catch (Exception e) {
            return null;
        }
    }

    public List<Meeting> meetingApplications() {
        try {
            return meetingRepository.findAllByStateEquals("inAudit");
        } catch (Exception e) {
            return null;
        }
    }

    public List<Meeting> applicationHandled(InitRequest2 initRequest) {
        try {
            String state = initRequest.getState();

            return meetingRepository.findAllByStateEquals(state);
        } catch (Exception e) {
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
            return null;
        }
    }

    public User getPersonalInform(InitRequest initRequest) {
        try {
            String username = initRequest.getUsername();
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            return null;
        }

    }

    public Meeting getMeetingInfo(InitRequest initRequest) {
        try {
            String fullname = initRequest.getFullname();
            return meetingRepository.findByFullname(fullname);
        } catch (Exception e) {

            return null;
        }
    }

    public List<Contribution> getAllArticle(InitRequest initRequest) {
        try {


            String fullname = initRequest.getFullname();
            return contributionRepository.findAllByMeetingFullname(fullname);
        } catch (Exception e) {
            return null;
        }
    }


}
