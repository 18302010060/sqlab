package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Contribution;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ContributionServiceTest {
    String username1 = "Lucy123";
    String username2="Jack000";
    String username3="asdqwe";
    String password="qwe123";
    String area="fudan";
    String unit="shanghai";
    String fullname="Lucy";
    String topics="['a','b','c']";
    String fullname1="The SoftWare Meeting";
    String title="The SoftWare Meetingbcvbvbc";
    String chair="asdqwe";
    String summary="The SoftWare cvbvcvb";
    String path="The SoftWare Meetingfgdg";
    String accepted="accepted";
    String filename="";
    @Autowired
    private AuthService authService;

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private InviteService inviteService;

    @Test
    void meetingApplyAndsubmitTest() {
        List<String> list = new ArrayList<>();
        //注册
        authService.register(new RegisterRequest(username1, password, "1@163.com", area, unit, fullname));
        authService.register(new RegisterRequest(username2, password, "12@163.com", area, unit, fullname));
        authService.register(new RegisterRequest(username3, password, "145246@163.com", area, unit, fullname));

        //登录
        String token1 = authService.login(username3, password);
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

        //情况一：chair不可以投稿
        Contribution submission = contributionService.submit(new Contribution(username3, fullname1, "The SoftWare Meetingvxv", "The SoftWare Meetingxbcxvc", "The SoftWare Meetinggfgds", list, topics, filename));
        //assertFalse(submission);

        //情况二：其余人可以投稿
        Contribution submission1 = contributionService.submit(new Contribution(username1, fullname1, title, summary, path, list, topics, filename));
        Long id = submission1.getId();

        //重复投稿
        contributionService.submit(new Contribution(username1, fullname1, title, summary, path, list, topics, filename));


        //会议通过审核后用户
        Boolean invite = inviteService.invite(new InviteRequest(fullname1, username1, username3));
        Boolean invite2 = inviteService.invite(new InviteRequest(fullname1, username2, username3));

        assertTrue(invite);
        //用户接受身份为chair
        Boolean accept = inviteService.acceptInvitation(new AcceptInviteRequest(fullname1, accepted, username1, topics));
        inviteService.acceptInvitation(new AcceptInviteRequest(fullname1, accepted, username2, topics));

        assertTrue(accept);
        //情况三：pcMember可以投稿，但是该成员已经提交过，投稿失败
        contributionService.submit(new Contribution(username1, fullname1, "The SoftWare Meetingfdsgsd", "The SoftWare Meetingfgfdg", "The SoftWare Meetingfgdsd", list, topics, filename));
        //assertTrue(submission3);

        //情况四：未投稿的PCmember，可以投稿
        contributionService.submit(new Contribution(username2, fullname1, "The SoftWare Meetinggfdfs", "The SoftWare Meetingfgdfgdf", "The SoftWare Meetingfdgdgd", list, topics, filename));


        Boolean change = contributionService.changeContribute((long) 12, "fdsfsd", "sdfsdd", "dsfsdfsd", "dsfsdsd", "fdssds", list, topics, filename);

        assertTrue(change);

        Boolean addResult = contributionService.addAuthor(id, "Mary", "Fudan", unit, "11111@163.com", (long) 1);
        assertTrue(addResult);


    }
}