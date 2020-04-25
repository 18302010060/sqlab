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
        List<String> list=new ArrayList<>();
        //注册
        authService.register(new RegisterRequest("Lucy123","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("Jack000","qwe123","145246@163.com","fudan","shanghai","Lucy"));

        Boolean result=authService.register(new RegisterRequest("asdqwe","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        assertTrue(result);
        //登录
        String token1=authService.login("asdqwe","qwe123");
        assertNotNull(token1);
        //会议申请
        Boolean result1=meetingService.apply(new ApplyRequest("Ics2020","The SoftWare Meeting","shanghai",new Date(),new Date(),new Date(), "asdqwe"));
        assertTrue(result1);
        //缩写、全称都不相同，申请成功
        Boolean result4=meetingService.apply(new ApplyRequest("Ics202000","The SoftWare Meeting on","shanghai",new Date(),new Date(),new Date(),"asdqwe"));
        assertTrue(result4);
       //会议通过
        Boolean result5=meetingService.audit(new AuditRequest("The SoftWare Meeting","passed"));
        assertTrue(result5);

        //情况一：chair不可以投稿
        Boolean submission=contributionService.submit(new Contribution("asdqwe","The SoftWare Meeting", "The SoftWare Meetingvxv", "The SoftWare Meetingxbcxvc", "The SoftWare Meetinggfgds",list,list));
        assertFalse(submission);

        //情况二：其余人可以投稿
        Boolean submission1=contributionService.submit(new Contribution("Lucy123","The SoftWare Meeting", "The SoftWare Meetingbcvbvbc", "The SoftWare cvbvcvb", "The SoftWare Meetingfgdg",list,list));
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
        Boolean submission3=contributionService.submit(new Contribution("Lucy123","The SoftWare Meeting", "The SoftWare Meetingfdsgsd", "The SoftWare Meetingfgfdg", "The SoftWare Meetingfgdsd",list,list));
        assertTrue(submission3);

        //情况四：未投稿的PCmember，可以投稿
        Boolean submission4=contributionService.submit(new Contribution("Jack000","The SoftWare Meeting", "The SoftWare Meetinggfdfs", "The SoftWare Meetingfgdfgdf", "The SoftWare Meetingfdgdgd",list,list));


        Boolean change =contributionService.changeContribute((long)12,"fdsfsd","sdfsdd","dsfsdfsd","dsfsdsd","fdssds",list,list);

        assertTrue(change);


    }
}