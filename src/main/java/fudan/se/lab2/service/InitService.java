package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.InitRequest;
import fudan.se.lab2.controller.request.InitRequest1;
import fudan.se.lab2.controller.request.InitRequest2;
import fudan.se.lab2.domain.Invitations;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitService {
    MeetingRepository meetingRepository;
    MeetingAuthorityRepository meetingAuthorityRepository;
    InvitationRepository invitationRepository;
    UserRepository userRepository;
    public List<Meeting> showDashboard(){;
        return meetingRepository.findAllByStateEqualsAndStateEquals("passed","inManuscript");
    }
    public List<Meeting> showMeetingIAppliedFor(InitRequest1 initRequest){
        String username = initRequest.getUsername();
        String state = initRequest.getState();
        return meetingRepository.findAllByChairEqualsAndStateEquals(username,state);
    }
    public List<MeetingAuthority> meetingIParticipatedIn(InitRequest initRequest){
        String authority  = initRequest.getAuthority();
        String username = initRequest.getUsername();
        return meetingAuthorityRepository.findAllByUsernameEqualsAndAuthorityEquals(username,authority);
    }
    public List<Invitations> invitationInformation(InitRequest1 initRequest){
        String username = initRequest.getUsername();
        return invitationRepository.findAllByUsernameEqualsAndInviteStateEquals(username,"invited");
    }
    public List<User> PCMemberInvitations(){
        Iterable<User> it = userRepository.findAll();
        List<User> user = new ArrayList<>();
        while (it.iterator().hasNext()){
            user.add(it.iterator().next());
        }
        return user;
    }
    public List<Invitations> invitationsResult(InitRequest2 initRequest){
        String username = initRequest.getUsername();
        String inviteState = initRequest.getInviteState();
        return invitationRepository.findAllByUsernameEqualsAndInviteStateEquals(username,inviteState);

    }
    public List<MeetingAuthority> PCMemberList(InitRequest initRequest){
        String fullname = initRequest.getFullname();
        return meetingAuthorityRepository.findAllByUsernameEqualsAndAuthorityEquals(fullname,"PCmember");
    }
    public List<Meeting> meetingApplications(){
        return meetingRepository.findAllByStateEquals("inAudit");
    }
    public List<Meeting> applicationHandled(InitRequest2 initRequest){
        String state = initRequest.getState();
        return meetingRepository.findAllByStateEquals(state);
    }

}
