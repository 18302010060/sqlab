package fudan.se.lab2.service;

import fudan.se.lab2.controller.InviteController;
import fudan.se.lab2.controller.request.AcceptInviteRequest;
import fudan.se.lab2.controller.request.InviteRequest;
import fudan.se.lab2.domain.Invitations;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.security.jwt.JwtConfigProperties;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//18302010060 黄怡清'part
@Service
public class InviteService {
    Logger logger = LoggerFactory.getLogger(InviteController.class);

    private MeetingRepository meetingRepository;
    private InvitationRepository invitationRepository;
    private MeetingAuthorityRepository meetingAuthorityRepository;

    @Autowired
    public InviteService(MeetingRepository meetingRepository,InvitationRepository invitationRepository,MeetingAuthorityRepository meetingAuthorityRepository) {

        this.meetingRepository = meetingRepository;
        this.invitationRepository = invitationRepository;
        this.meetingAuthorityRepository = meetingAuthorityRepository;
    }


    public boolean invite(InviteRequest request){
        String fullname = request.getFullname();//得到会议全称
        String username = request.getUserName();//得到邀请者姓名
        String token = request.getToken();

        Invitations invitation  = new Invitations(fullname,username);//创建邀请
        Optional<Invitations> invitation2 = Optional.ofNullable(invitationRepository.findByUsernameAndAndFullname(username,fullname));
        if(invitation2.isPresent()){//如果邀请信息已存在
            logger.info("已邀请");
            return false;
        }
        else{
            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(new JwtConfigProperties());
            String chair = jwtTokenUtil.getUsernameFromToken(token);//根据token得到chair姓名
            logger.info("chair:"+chair);
            invitation.setChair(chair);
            invitationRepository.save(invitation);
            logger.info("邀请成功");
            return true;
        }

    }
    public boolean acceptInvitation(AcceptInviteRequest request){
        //TODO:将接受邀请的用户加入人-身份-成员数据库
        String fullname = request.getFullname();
        String inviteState = request.getInviteState();
        String token = request.getToken();
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(new JwtConfigProperties());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Invitations invitation = invitationRepository.findByUsernameAndAndFullname(username,fullname);
        if(invitation.getInviteState()=="接受邀请"){
            logger.info("已接受邀请");
            return false;
        }
        else{
            if(inviteState=="拒绝邀请"){
                logger.info("已拒绝邀请");

            }
            else if(inviteState=="接受邀请"){
                logger.info("接受邀请成功");
                MeetingAuthority meetingAuthority = new MeetingAuthority(username,fullname,"PC Member");
                meetingAuthorityRepository.save(meetingAuthority);
            }
        }
        invitation.setInviteState(inviteState);
        invitationRepository.save(invitation);

        return true;


    }




}

