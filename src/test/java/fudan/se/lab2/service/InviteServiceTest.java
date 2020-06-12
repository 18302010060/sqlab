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
    @Autowired
    private AuthService authService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private InviteService inviteService;

    @Test
    void invite() {
        String result = authService.register(new RegisterRequest("asdqwe", "qwe123", "145246@163.com", "fudan", "shanghai", "Lucy"));
        assertEquals("注册成功", result);
        //登录
        String token1 = authService.login("asdqwe", "qwe123");
        assertNotNull(token1);
        //会议申请
        Boolean result1 = meetingService.apply(new ApplyRequest("Ics2020", "The SoftWare Meeting", "shanghai", new Date(), new Date(), new Date(), "asdqwe", "['a','b','c']"));
        assertTrue(result1);
        //缩写、全称都不相同，申请成功
        Boolean result4 = meetingService.apply(new ApplyRequest("Ics202000", "The SoftWare Meeting on", "shanghai", new Date(), new Date(), new Date(), "asdqwe", "['a','b','c']"));
        assertTrue(result4);
        //会议通过
        Boolean result5 = meetingService.audit(new AuditRequest("The SoftWare Meeting", "passed"));
        assertTrue(result5);

        //注册
        authService.register(new RegisterRequest("Lucy123", "qwe123", "145246@163.com", "fudan", "shanghai", "Lucy"));
        authService.register(new RegisterRequest("Jack000", "qwe123", "145246@163.com", "fudan", "shanghai", "Lucy"));
        authService.register(new RegisterRequest("Jackjds000", "qwe123", "145246@163.com", "fudan", "shanghai", "Lucy"));

        //会议通过审核后邀请已经存在的用户
        Boolean invite = inviteService.invite(new InviteRequest("The SoftWare Meeting", "Lucy123", "asdqwe"));
        assertTrue(invite);

        //会议邀请已经邀请过得用户1
        Boolean invite1 = inviteService.invite(new InviteRequest("The SoftWare Meeting", "Lucy123", "asdqwe"));
        assertFalse(invite1);

        //邀请用户2
        Boolean invite2 = inviteService.invite(new InviteRequest("The SoftWare Meeting", "Jack000", "asdqwe"));
        assertTrue(invite2);
        //邀请用户3
        Boolean invite3 = inviteService.invite(new InviteRequest("The SoftWare Meeting", "Jackjds000", "asdqwe"));
        assertTrue(invite3);
        //用户拒接
        Boolean accept = inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting", "rejected", "Lucy123", "['a','b','c']"));
        assertTrue(accept);
        //用户接受
        Boolean accept3 = inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting", "accepted", "Jack000", "['a','b','c']"));
        assertTrue(accept3);

        //错误用户请求
        Boolean accept2 = inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting", "accepted", "Lfdsfsd", "['a','b','c']"));
        assertFalse(accept2);
        //用户接受
        Boolean accept4 = inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting", "accepted", "Jackjds000", "['a','b','c']"));
        assertTrue(accept4);



    }

}