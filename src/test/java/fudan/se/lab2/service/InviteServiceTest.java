package fudan.se.lab2.service;


import fudan.se.lab2.controller.request.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class InviteServiceTest {
    String username1 = "Lucy123";
    String username2="Jack000";
    String username3="Jackjds000";

    String password="qwe123";
    String area="fudan";
    String unit="shanghai";
    String fullname="Lucy";
    String topics="['a','b','c']";
    String fullname1="The SoftWare Meeting";
    String chair="asdqwe";
    String accepted="accepted";
    @Autowired
    private AuthService authService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private InviteService inviteService;

    @Test
    void invite() {
        String result = authService.register(new RegisterRequest(chair, password, "14zx5246@163.com", area, unit, fullname));
        assertEquals("注册成功", result);
        //登录
        String token1 = authService.login(chair, password);
        assertNotNull(token1);
        //会议申请
        Boolean result1 = meetingService.apply(new ApplyRequest("Ics2020", fullname1, unit, new Date(), new Date(), new Date(), chair, topics));
        assertTrue(result1);
        //缩写、全称都不相同，申请成功
        Boolean result4 = meetingService.apply(new ApplyRequest("Ics202000", "The SoftWare Meeting on", unit, new Date(), new Date(), new Date(), chair, topics));
        assertTrue(result4);
        //会议通过
        Boolean result5 = meetingService.audit(new AuditRequest(fullname1, "passed"));
        assertTrue(result5);

        //注册p
        authService.register(new RegisterRequest(username1, password, "14jh5246@163.com", area, unit, fullname));
        authService.register(new RegisterRequest(username2, password, "145fd246@163.com", area, unit, fullname));
        authService.register(new RegisterRequest(username3, password, "145lky246@163.com", area, unit, fullname));

        //会议通过审核后邀请已经存在的用户
        Boolean invite = inviteService.invite(new InviteRequest(fullname1, username1, chair));
        assertTrue(invite);

        //会议邀请已经邀请过得用户1
        Boolean invite1 = inviteService.invite(new InviteRequest(fullname1, username1, chair));
        assertFalse(invite1);

        //邀请用户2
        Boolean invite2 = inviteService.invite(new InviteRequest(fullname1, username2, chair));
        assertTrue(invite2);
        //邀请用户3
        Boolean invite3 = inviteService.invite(new InviteRequest(fullname1, username3, chair));
        assertTrue(invite3);
        //用户拒接
        Boolean accept = inviteService.acceptInvitation(new AcceptInviteRequest(fullname1, "rejected", username1, topics));
        assertTrue(accept);
        //用户接受
        Boolean accept3 = inviteService.acceptInvitation(new AcceptInviteRequest(fullname1, accepted, username2, topics));
        assertTrue(accept3);

        //错误用户请求
        Boolean accept2 = inviteService.acceptInvitation(new AcceptInviteRequest(fullname1, accepted, "Lfdsfsd", topics));
        assertFalse(accept2);
        //用户接受
        Boolean accept4 = inviteService.acceptInvitation(new AcceptInviteRequest(fullname1, accepted, username3, topics));
        assertTrue(accept4);



    }

}