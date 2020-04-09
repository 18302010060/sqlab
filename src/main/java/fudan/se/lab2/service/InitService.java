package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.InitRequest;
import fudan.se.lab2.domain.Invitations;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.repository.UserRepository;

public class InitService {
    MeetingRepository meetingRepository;
    MeetingAuthorityRepository meetingAuthorityRepository;
    InvitationRepository invitationRepository;
    UserRepository userRepository;
    public Meeting showDashboard(){;
        return meetingRepository.findAllByStateEqualsAndStateEquals("以通过","投稿中");
    }
    public Meeting shouMeetingIAppliedFor(InitRequest initRequest){
        String username = initRequest.getUsername();
        String state = initRequest.getState();
        return meetingRepository.findAllByChairEqualsAndStateEquals(username,state);
    }
    public MeetingAuthority meetingIParticipatedIn(InitRequest initRequest){
        String authority  = initRequest.getAuthority();
        String username = initRequest.getUsername();
        return meetingAuthorityRepository.findAllByUsernameEqualsAndAuthorityEquals(username,authority);
    }
    public Invitations invitationInformation(InitRequest initRequest){
        String username = initRequest.getUsername();
        return invitationRepository.findAllByUsernameEqualsAndInviteStateEquals(username,"已邀请");
    }
    public User PCMemberInvitations(){
        return (User)userRepository.findAll();
    }
    public Invitations invitationsResult(InitRequest initRequest){
        String username = initRequest.getUsername();
        String inviteState = initRequest.getInviteState();
        return invitationRepository.findAllByUsernameEqualsAndInviteStateEquals(username,inviteState);

    }
    public MeetingAuthority PCMemberList(InitRequest initRequest){
        String fullname = initRequest.getFullname();
        return meetingAuthorityRepository.findAllByUsernameEqualsAndAuthorityEquals(fullname,"PCMember");
    }

}
