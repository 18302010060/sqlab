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
class ContributionServiceTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private InitService initService;


    @Autowired
    private InviteService inviteService;
    @Test
    void meetingApplyAndsubmitTest() {
        //注册
        authService.register(new RegisterRequest("Lucy123","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("Jack000","qwe123","145246@163.com","fudan","shanghai","Lucy"));

        Boolean result=authService.register(new RegisterRequest("asdqwe","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        assertTrue(result);
        //登录
        String token1=authService.login("asdqwe","qwe123");
        assertNotNull(token1);
        //会议申请
        Boolean result1=meetingService.apply(new ApplyRequest("Ics2020","The SoftWare Meeting","shanghai",new Date(),new Date(),new Date(), "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rxd2UiLCJpYXQiOjE1ODY2NzgzNDIsImV4cCI6MTU4NjY5NjM0Mn0.NtVBnj3oq3cmNnMalO71sWUrzZFhGh5t-eN9gY8DDwPmLdwsWJtLDff0uq64c3JxTajDhjzenkvSbI0dqJ4Xtg"));
        assertTrue(result1);
        //缩写、全称都不相同，申请成功
        Boolean result4=meetingService.apply(new ApplyRequest("Ics202000","The SoftWare Meeting on","shanghai",new Date(),new Date(),new Date(),"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rxd2UiLCJpYXQiOjE1ODY2NzgzNDIsImV4cCI6MTU4NjY5NjM0Mn0.NtVBnj3oq3cmNnMalO71sWUrzZFhGh5t-eN9gY8DDwPmLdwsWJtLDff0uq64c3JxTajDhjzenkvSbI0dqJ4Xtg"));
        assertTrue(result4);
       //会议通过
        Boolean result5=meetingService.audit(new AuditRequest("The SoftWare Meeting","passed"));
        assertTrue(result5);

        //情况一：chair不可以投稿
        Boolean submission=contributionService.submit(new ContributionRequest("The SoftWare Meeting", "The SoftWare Meeting", "The SoftWare Meeting", "The SoftWare Meeting","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rxd2UiLCJpYXQiOjE1ODY2NzgzNDIsImV4cCI6MTU4NjY5NjM0Mn0.NtVBnj3oq3cmNnMalO71sWUrzZFhGh5t-eN9gY8DDwPmLdwsWJtLDff0uq64c3JxTajDhjzenkvSbI0dqJ4Xtg"));
        assertFalse(submission);

        //情况二：其余人可以投稿
        Boolean submission1=contributionService.submit(new ContributionRequest("The SoftWare Meeting", "The SoftWare Meeting", "The SoftWare Meeting", "The SoftWare Meeting","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJMdWN5MTIzIiwiaWF0IjoxNTg2Njg4OTAyLCJleHAiOjE1ODY3MDY5MDJ9.yavPLfjJYi3Uc7-fdjmOHjV5l_6Y1brCzoPpKg-cBaeNPaIeUuFxTd30mH-uvbd8yOBVZOo2-iZRDu8BWk462g"));
        assertTrue(submission1);

        //会议通过审核后用户
        Boolean invite=inviteService.invite(new InviteRequest("The SoftWare Meeting", "Lucy123","asdqwe"));
        Boolean invite2=inviteService.invite(new InviteRequest("The SoftWare Meeting", "Jack000","asdqwe"));

        assertTrue(invite);
        //用户接受身份为chair
        Boolean accept=inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting", "accepted","Lucy123"));
        Boolean accept3=inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting", "accepted","Jack000"));

        assertTrue(accept);
        //情况三：pcMember可以投稿，但是该成员已经提交过，投稿失败
        Boolean submission3=contributionService.submit(new ContributionRequest("The SoftWare Meeting", "The SoftWare Meeting", "The SoftWare Meeting", "The SoftWare Meeting","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJMdWN5MTIzIiwiaWF0IjoxNTg2Njg4OTAyLCJleHAiOjE1ODY3MDY5MDJ9.yavPLfjJYi3Uc7-fdjmOHjV5l_6Y1brCzoPpKg-cBaeNPaIeUuFxTd30mH-uvbd8yOBVZOo2-iZRDu8BWk462g"));
        assertFalse(submission3);

        //情况四：未投稿的PCmember，可以投稿
        Boolean submission4=contributionService.submit(new ContributionRequest("The SoftWare Meeting", "The SoftWare Meeting", "The SoftWare Meeting", "The SoftWare Meeting","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKYWNrMDAwIiwiaWF0IjoxNTg2Njg5NTg4LCJleHAiOjE1ODY3MDc1ODh9.V0Xpfog6eK2CLZL89LfaN3k5Ombf82IJPhFp8cfmnJskzhGK5NK-g_VVFm3eoSSjKMJsNtHmjUlIbJbhDAEIzA"));
        assertTrue(submission4);


    }
}