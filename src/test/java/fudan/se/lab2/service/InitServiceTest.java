package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Meeting;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class InitServiceTest {
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


   //一共有是个会议，前两个inManuscript，再三个passed 再两个 rejected，最后三个inAudit
    @Test
    void openSubmissionnTest() {

        //测试showDashboard()失败的案例
        List<Meeting> list= initService.showDashboard();
        for(int i=1;i<10;i++){
            assertNotEquals(i,list.size());
        }

        //测试用户注册
        authService.register(new RegisterRequest("asdqwe","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("aaaaa","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("bbbbb","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("ccc","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("eee","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("fff","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("iii","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("jjj","qwe123","145246@163.com","fudan","shanghai","Lucy"));

        //测试会议申请
        meetingService.apply(new ApplyRequest("Ics2020","The SoftWare Meeting1","shanghai",new Date(),new Date(),new Date(), "asdqwe"));
        meetingService.apply(new ApplyRequest("Ics2021","The SoftWare Meeting2","shanghai",new Date(),new Date(),new Date(), "asdqwe"));
        meetingService.apply(new ApplyRequest("Ics2022","The SoftWare Meeting3","shanghai",new Date(),new Date(),new Date(), "asdqwe"));
        meetingService.apply(new ApplyRequest("Ics2023","The SoftWare Meeting4","shanghai",new Date(),new Date(),new Date(), "asdqwe"));
        meetingService.apply(new ApplyRequest("Ics2024","The SoftWare Meeting5","shanghai",new Date(),new Date(),new Date(), "asdqwe"));
        meetingService.apply(new ApplyRequest("Ics2025","The SoftWare Meeting6","shanghai",new Date(),new Date(),new Date(), "asdqwe"));
        meetingService.apply(new ApplyRequest("Ics2026","The SoftWare Meeting7","shanghai",new Date(),new Date(),new Date(), "asdqwe"));
        meetingService.apply(new ApplyRequest("Ics2027","The SoftWare Meeting8","shanghai",new Date(),new Date(),new Date(), "asdqwe"));
        meetingService.apply(new ApplyRequest("Ics2028","The SoftWare Meeting9","shanghai",new Date(),new Date(),new Date(), "asdqwe"));
        meetingService.apply(new ApplyRequest("Ics2029","The SoftWare Meeting0","shanghai",new Date(),new Date(),new Date(), "asdqwe"));

        meetingService.audit(new AuditRequest("The SoftWare Meeting1","passed"));
        meetingService.audit(new AuditRequest("The SoftWare Meeting2","passed"));
        meetingService.audit(new AuditRequest("The SoftWare Meeting3","passed"));
        meetingService.audit(new AuditRequest("The SoftWare Meeting4","passed"));
        meetingService.audit(new AuditRequest("The SoftWare Meeting5","passed"));
        meetingService.audit(new AuditRequest("The SoftWare Meeting6","rejected"));
        meetingService.audit(new AuditRequest("The SoftWare Meeting7","rejected"));

        inviteService.invite(new InviteRequest("The SoftWare Meeting", "Lucy123","asdqwe"));

        inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting", "accepted","Jack000"));
        inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting", "rejected","Lucy123"));
        //会议审核成功 开启投稿
        Boolean result=initService.openSubmissionn(new InitRequest4("","The SoftWare Meeting1"));
        assertTrue(result);
        result=initService.openSubmissionn(new InitRequest4("","The SoftWare Meeting2"));
        assertTrue(result);
        //未申请的会议 无法开启投稿
        result=initService.openSubmissionn(new InitRequest4("","Thezxxc SoftWare Meeting1"));
        assertFalse(result);
    }

    @Test
    void showDashboard() {
        List<Meeting> list= initService.showDashboard();
        assertEquals(5,list.size());
    }

    @Test
    void showMeetingIAppliedFor() {
        List<Meeting> list=initService.showMeetingIAppliedFor(new InitRequest1("asdqwe","inAudit",""));
        List<Meeting> list1=initService.showMeetingIAppliedFor(new InitRequest1("asdqwe","passed",""));
        List<Meeting> list2=initService.showMeetingIAppliedFor(new InitRequest1("asdqwe","rejected",""));
        List<Meeting> list3=initService.showMeetingIAppliedFor(new InitRequest1("asdqwe","inManuscript",""));
        assertEquals(3,list.size());
        assertEquals(3,list1.size());
        assertEquals(2,list2.size());
        assertEquals(2,list3.size());
    }

    @Test
    void meetingIParticipatedIn() {
    }

    @Test
    void invitationInformation() {
    }

    @Test
    void PCMemberInvitations() {
    }

    @Test
    void invitationsResult() {
    }

    @Test
    void PCMemberList() {
    }

    @Test
    void meetingApplications() {
    }

    @Test
    void applicationHandled() {
    }

    @Test
    void getAllSubmissions() {
    }

    @Test
    void getPersonalInform() {
    }

    @Test
    void getMeetingInfo() {
    }

    @Test
    void getAllArticle() {
    }

    @Test
    void getArticleDetail() {
    }


    @Test
    void judgeWhetherPcmemberr() {
    }

    @Test
    void getMeetingStatee() {
    }
}