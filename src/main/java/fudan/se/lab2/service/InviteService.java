package fudan.se.lab2.service;

import fudan.se.lab2.controller.InviteController;
import fudan.se.lab2.controller.request.AcceptInviteRequest;
import fudan.se.lab2.controller.request.InviteRequest;
import fudan.se.lab2.domain.Invitations;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.security.jwt.JwtConfigProperties;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//18302010060 黄怡清'part
@Service
public class InviteService {
    Logger logger = LoggerFactory.getLogger(InviteController.class);

    private MeetingRepository meetingRepository;
    private InvitationRepository invitationRepository;

    @Autowired
    public InviteService(MeetingRepository meetingRepository,InvitationRepository invitationRepository) {

        this.meetingRepository = meetingRepository;
        this.invitationRepository = invitationRepository;
    }


    public boolean invite(InviteRequest request){
        String fullname = request.getFullname();
        String username = request.getUserName();
        String token = request.getToken();
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(new JwtConfigProperties());
        String chair = jwtTokenUtil.getUsernameFromToken(token);
        Invitations invitation  = new Invitations(fullname,username);
        invitation.setChair(chair);

        invitationRepository.save(invitation);

        return true;

    }
    public boolean acceptInvitation(AcceptInviteRequest request){
        String fullname = request.getFullname();
        String inviteState = request.getInviteState();
        String token = request.getToken();
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(new JwtConfigProperties());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Invitations invitation = invitationRepository.findByUsernameAndAndFullname(username,fullname);
        invitation.setInviteState(inviteState);
        invitationRepository.save(invitation);

        return true;


    }




}

