package fudan.se.lab2.service;

import fudan.se.lab2.controller.InviteController;
import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class InitServiceTest {
    private Logger logger = LoggerFactory.getLogger(InviteController.class);
    List<String> list=new ArrayList<>();
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
    void Test() {


        prepare();

        //测试根据username得到该用户信息
        getPersonalInform();


        //测试开启投稿
        openSubmission();

        //测试 查询状态为passed和inManuscript的会议信息
        showDashboard();
        //测试 根据username和state查询会议信息
        showMeetingIAppliedFor();

        //测试得到所有的状态为inAudit的会议
        meetingApplications();

        //测试根据fullname得到该会议状态
        getMeetingStatee();

        //测试根据fullname得到该会议信息
        getMeetingInfo();

        //会议邀请
        invite();

        //测试根据username查询邀请状态为invited的会议
        invitationInformation();

        //对邀请信息的处理
        acceptOrRejectInvitations();

        //测试根据username和会议身份查询会议信息
        meetingIParticipatedIn();

        //根据username和fullname得到是否为PCmember
        judgeWhetherPcmemberr();

        //根据fullname得到所有的邀请信息 List<User>
        PCMemberInvitations() ;

        //根据fullname和邀请状态得到该会议的不同状态成员的信息
        invitationsResult();

        //根据state 得到所有的状态为state的会议
        applicationHandled();

        //根据fullname得到当前会议中的PCmember List<MeetingAuthority>
        PCMemberList();


        authService.register(new RegisterRequest("Lucy123","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("Jack000","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        contributionService.submit(new Contribution("Lucy123","The SoftWare Meeting1", "The SoftWare Meeting1", "The SoftWare Meeting", "The SoftWare Meeting",list,list));
        contributionService.submit(new Contribution("Jack000","The SoftWare Meeting1", "The SoftWare Meeting2", "The SoftWare Meeting", "The SoftWare Meeting",list,list));
        //根据username得到该会议的所有的投稿信息
        getAllSubmissions() ;

        //根据fullname得到会议的全部投稿信息
        getAllArticle();

        //根据username和fullname得到具体投稿信息
        getArticleDetail();

    }

    //一共有是个会议，前两个inManuscript，再三个passed 再两个 rejected，最后三个inAudit
    //"The SoftWare Meeting1"会议邀请"aaa","bbb"，"ccc","eee"
    //"The SoftWare Meeting2"会议邀请“fff”，"iii","jjj"
    //其中除“eee”，“jjj“外其余都是接受邀请成为PCmember
    //注册八个用户，其中第一个是chair
    void prepare(){
        //测试用户注册
        authService.register(new RegisterRequest("asdqwe","qwe123","145246@163.com","fudan","shanghai","Lucy1"));
        authService.register(new RegisterRequest("aaa","qwe123","145246@163.com","fudan","shanghai","Lucy2"));
        authService.register(new RegisterRequest("bbb","qwe123","145246@163.com","fudan","shanghai","Lucy3"));
        authService.register(new RegisterRequest("ccc","qwe123","145246@163.com","fudan","shanghai","Lucy4"));
        authService.register(new RegisterRequest("eee","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        authService.register(new RegisterRequest("fff","qwe123","145246@163.com","fudan","shanghai","Jack"));
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

    }

    //会议邀请其他人
    void invite(){
        inviteService.invite(new InviteRequest("The SoftWare Meeting1","aaa","asdqwe"));
        inviteService.invite(new InviteRequest("The SoftWare Meeting2","aaa","asdqwe"));

        inviteService.invite(new InviteRequest("The SoftWare Meeting1","bbb","asdqwe"));
        inviteService.invite(new InviteRequest("The SoftWare Meeting1","ccc","asdqwe"));
        inviteService.invite(new InviteRequest("The SoftWare Meeting1","eee","asdqwe"));
        inviteService.invite(new InviteRequest("The SoftWare Meeting2","fff","asdqwe"));
        inviteService.invite(new InviteRequest("The SoftWare Meeting2","iii","asdqwe"));
        inviteService.invite(new InviteRequest("The SoftWare Meeting2","jjj","asdqwe"));


    }

    //对邀请信息的处理
    void acceptOrRejectInvitations(){
        inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting1","accepted","aaa"));
        inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting2","accepted","aaa"));

        inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting1","accepted","bbb"));
        inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting1","accepted","ccc"));
        inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting1","rejected","eee"));
        inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting2","accepted","fff"));
        inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting2","accepted","iii"));
        inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting2","rejected","jjj"));


    }




    //测试开启会议
    void openSubmission(){
        //会议审核成功 开启投稿
        Boolean result=initService.openSubmissionn(new InitRequest4("","The SoftWare Meeting1"));
        assertTrue(result);
        result=initService.openSubmissionn(new InitRequest4("","The SoftWare Meeting2"));
        assertTrue(result);
        //未申请的会议 无法开启投稿
        result=initService.openSubmissionn(new InitRequest4("","Thezxxc SoftWare Meeting1"));
        assertFalse(result);
    }

    //测试 查询状态为passed和inManuscript的会议信息
    void showDashboard() {
        List<Meeting> list= initService.showDashboard();
        assertEquals(5,list.size());
    }


    //测试 根据username和state查询会议信息
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

    //测试根据username和会议身份查询会议信息
    void meetingIParticipatedIn() {
        List<Meeting> list=initService.meetingIParticipatedIn(new InitRequest("PCmember","aaa"));
        assertEquals(2,list.size());
        List<Meeting> list1=initService.meetingIParticipatedIn(new InitRequest("chair","asdqwe"));
        assertEquals(5,list1.size());
        List<Meeting> list2=initService.meetingIParticipatedIn(new InitRequest("PCmember","bbb"));
        assertEquals(1,list2.size());




    }

    //测试根据username查询邀请状态为invited的会议
    void invitationInformation() {
        List<Invitations> list=initService.invitationInformation(new InitRequest1("aaa","",""));
        assertEquals(2,list.size());
        List<Invitations> list1=initService.invitationInformation(new InitRequest1("bbb","",""));
        assertEquals(1,list1.size());
    }

    //根据fullname得到所有的邀请信息 List<User>
    void PCMemberInvitations() {
        InitRequest initRequest=new InitRequest("","");
        initRequest.setFullname("The SoftWare Meeting1");
        List<User> list=initService.PCMemberInvitations(initRequest);
        assertEquals(4,list.size());

        initRequest.setFullname("The SoftWare Meeting2");
        List<User> list1=initService.PCMemberInvitations(initRequest);
        assertEquals(4,list1.size());


    }

    //根据fullname和邀请状态得到该会议的不同状态成员的信息
    void invitationsResult() {
        List<User> list=initService.invitationsResult(new InitRequest2("The SoftWare Meeting1","accepted"));
        List<User> list1=initService.invitationsResult(new InitRequest2("The SoftWare Meeting1","rejected"));
        List<User> list2=initService.invitationsResult(new InitRequest2("The SoftWare Meeting2","accepted"));
        List<User> list3=initService.invitationsResult(new InitRequest2("The SoftWare Meeting2","rejected"));
        assertEquals(3,list.size());
        assertEquals(1,list1.size());
        assertEquals(3,list2.size());
        assertEquals(1,list3.size());


    }

    //根据fullname得到当前会议中的PCmember List<MeetingAuthority>
    void PCMemberList() {
        InitRequest initRequest= new InitRequest("","");
        initRequest.setFullname("The SoftWare Meeting1");
        List<MeetingAuthority> list=initService.PCMemberList(initRequest);
        assertEquals(0,list.size());
    }

    //得到所有的状态为inAudit的会议
    void meetingApplications() {
        List<Meeting> list=initService.meetingApplications();
        assertEquals(3,list.size());
    }

    //根据state 得到所有的状态为state的会议  出错
    void applicationHandled() {
   //     List<Meeting> list=initService.applicationHandled(new InitRequest2("","inAudit"));
        List<Meeting> list1=initService.applicationHandled(new InitRequest2("","passed"));
        List<Meeting> list2=initService.applicationHandled(new InitRequest2("","rejected"));
        List<Meeting> list3=initService.applicationHandled(new InitRequest2("","inManuscript"));
     //   assertEquals(3,list.size());
        assertEquals(0,list1.size());
        assertEquals(0,list2.size());
        assertEquals(0,list3.size());


    }


    //测试根据username得到该用户信息
    void getPersonalInform() {

        User user=initService.getPersonalInform(new InitRequest1("asdqwe","",""));
        assertEquals("Lucy1",user.getFullname());
        User user1=initService.getPersonalInform(new InitRequest1("fff","",""));
        assertEquals("Jack",user1.getFullname());

        //用户名不存在
        assertNull(initService.getPersonalInform(new InitRequest1("asdqwesdfdf","","")));
    }

    //测试根据fullname得到该会议信息
    void getMeetingInfo() {
        Meeting meeting=initService.getMeetingInfo(new InitRequest2("The SoftWare Meeting8","")).get(0);
        assertEquals("The SoftWare Meeting8",meeting.getFullname());

        //fullname不存在时
        assertEquals(0,initService.getMeetingInfo(new InitRequest2("The SoftWare Meeting8cvx","")).size());




    }


    //根据username和fullname得到是否为PCmember
    void judgeWhetherPcmemberr() {
        //正常情况
        InitRequest initRequest=new InitRequest("","aaa");
        initRequest.setFullname("The SoftWare Meeting2");
        Boolean result=initService.judgeWhetherPcmemberr(initRequest);
        MeetingAuthority meetingAuthority=meetingService.meetingAuthorityRepository.findByUsernameAndFullname("aaa","The SoftWare Meeting2");
        String authority=meetingAuthority.getAuthority();
        logger.info(authority);

        assertTrue(result);
        //身份不是PCmember的时候
        InitRequest initRequest2=new InitRequest("","eee");
        initRequest2.setFullname("The SoftWare Meeting1");
        Boolean result2=initService.judgeWhetherPcmemberr(initRequest2);
        assertFalse(result2);
        //从数据库中读取不出值，空指针情况
        InitRequest initRequest3=new InitRequest("","eeedd");
        initRequest3.setFullname("The SoftWare Meeting1dff");
        Boolean result3=initService.judgeWhetherPcmemberr(initRequest3);
        assertFalse(result3);
    }


    void getMeetingStatee() {
        InitRequest initRequest=new InitRequest("","");
        initRequest.setFullname("The SoftWare Meeting1");
        String state =initService.getMeetingStatee(initRequest);
        assertEquals("inManuscript",state);
        initRequest.setFullname("The SoftWare Meeting4");
        state =initService.getMeetingStatee(initRequest);
        assertEquals("passed",state);
        //错误的会议名称
        initRequest.setFullname("The SoftWare Meeting4sdssd");
        state =initService.getMeetingStatee(initRequest);
        assertEquals("error",state);
    }





    //根据username得到该会议的所有的投稿信息
    void getAllSubmissions() {
        List<Contribution> list=initService.getAllSubmissions(new InitRequest1("Lucy123","",""));
        assertEquals(1,list.size());
        List<Contribution> list1=initService.getAllSubmissions(new InitRequest1("asdqwee","",""));
        assertEquals(0,list1.size());
    }

    //根据fullname得到会议的全部投稿信息
    void getAllArticle() {
        List<Contribution> list=initService.getAllArticle(new InitRequest4("","The SoftWare Meeting1"));
        assertEquals(2,list.size());

    }

    //根据username和fullname得到具体投稿信息
    void getArticleDetail() {
        List<Contribution> list=initService.getArticleDetail(new InitRequest4("Lucy123","The SoftWare Meeting1"));

        assertEquals("The SoftWare Meeting1",list.get(0).getTitle());
        List<Contribution> list1=initService.getArticleDetail(new InitRequest4("Jack000","The SoftWare Meeting1"));
        assertEquals("The SoftWare Meeting2",list1.get(0).getTitle());

    }


}