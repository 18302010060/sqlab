package fudan.se.lab2.service;

import fudan.se.lab2.controller.InviteController;
import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
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
    String username1 = "Lucy123";
    String username2="Jack000";
    String email="145246@163.com";
    String password="qwe123";
    String area="fudan";
    String unit="shanghai";
    String pass="passed";
    String meeting="meeting";
    String meeting1="The SoftWare Meeting1";
    String meeting2="The SoftWare Meeting2";
    String meeting3="The SoftWare Meeting3";
    String meeting4="The SoftWare Meeting4";
    String meeting5="The SoftWare Meeting5";
    String meeting6="The SoftWare Meeting6";
    String meeting7="The SoftWare Meeting7";
    String meeting8="The SoftWare Meeting8";
    String rejected="rejected";
    String inManuscript="inManuscript";
    String aa="aaaaaa";
    String a="aaa";
    String b="bbb";
    String c="ccc";
    String e="eee";
    String f="fff";
    String i="iii";
    String j="jjj";
    String PCmember="PCmember";
    String o="0";
    String Jack="Jack";
    String Lucy1="Lucy1";
    String chairState="chair";
    String fullname="Lucy";
    String topics="['a','b','c']";
    String chair="asdqwe";
    String accepted="accepted";
    String nothing="";
    private Logger logger = LoggerFactory.getLogger(InviteController.class);
    List<String> list = new ArrayList<>();
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
    @Autowired
    private MeetingAuthorityRepository meetingAuthorityRepository;

    @Test
    void Test() {

        initService.showDashboard();
        prepare();

        //????????????username?????????????????????
        getPersonalInform();


        //??????????????????
        openSubmission();

        //?????? ???????????????passed???inManuscript???????????????
        showDashboard();
        //?????? ??????username???state??????????????????
        showMeetingIAppliedFor();

        //??????????????????????????????inAudit?????????
        meetingApplications();

        //????????????fullname?????????????????????
        getMeetingStatee();

        //????????????fullname?????????????????????
        getMeetingInfo();

        //????????????
        invite();

        //????????????username?????????????????????invited?????????
        invitationInformation();

        //????????????????????????
        acceptOrRejectInvitations();

        //????????????username?????????????????????????????????
        meetingIParticipatedIn();

        //??????username???fullname???????????????PCmember
        judgeWhetherPcmemberr();

        //??????fullname??????????????????????????? List<User>
        PCMemberInvitations();

        //??????fullname????????????????????????????????????????????????????????????
        invitationsResult();

        //??????state ????????????????????????state?????????
        applicationHandled();

        //??????fullname????????????????????????PCmember List<MeetingAuthority>
        PCMemberList();


        authService.register(new RegisterRequest(username1, password, email, area, unit, fullname));
        authService.register(new RegisterRequest(username2, password, email, area, unit, fullname));
        contributionService.submit(new Contribution(username1, meeting1, meeting1, "The SoftWare Meeting", "The SoftWare Meeting", list, nothing, nothing));
        contributionService.submit(new Contribution(username2, meeting1, meeting2, "The SoftWare Meeting", "The SoftWare Meeting", list, nothing, nothing));
        //??????username???????????????????????????????????????
        getAllSubmissions();

        //??????fullname?????????????????????????????????
        getAllArticle();

        //??????username???fullname????????????????????????
        getArticleDetail();

    }

    //?????????????????????????????????inManuscript????????????passed ????????? rejected???????????????inAudit
    //????????????"aaa","bbb"???"ccc","eee"
    //m???????????????fff??????"iii","jjj"
    //????????????eee?????????jjj????????????????????????????????????PCmember
    //???????????????????????????????????????chair
    void prepare() {
        //??????????????????
        authService.register(new RegisterRequest(chair, password, email, area, unit, Lucy1));
        authService.register(new RegisterRequest(a, password, "1145246dd@163.com", area, unit, "Lucy2"));
        authService.register(new RegisterRequest(b, password, "2145246a@163.com", area, unit, "Lucy3"));
        authService.register(new RegisterRequest(c, password, "3145246b@163.com", area, unit, "Lucy4"));
        authService.register(new RegisterRequest(e, password, "4145246c@163.com", area, unit, fullname));
        authService.register(new RegisterRequest(f, password, "5145246d@163.com", area, unit, Jack));
        authService.register(new RegisterRequest(i, password, "6145246e@163.com", area, unit, fullname));
        authService.register(new RegisterRequest(j, password, "7145246f@163.com", area, unit, fullname));

        //??????????????????
        meetingService.apply(new ApplyRequest("Ics2020", meeting1, unit, new Date(), new Date(), new Date(), chair, topics));
        meetingService.apply(new ApplyRequest("Ics2021", meeting2, unit, new Date(), new Date(), new Date(), chair, topics));
        meetingService.apply(new ApplyRequest("Ics2022", meeting3, unit, new Date(), new Date(), new Date(), chair, topics));
        meetingService.apply(new ApplyRequest("Ics2023", meeting4, unit, new Date(), new Date(), new Date(), chair, topics));
        meetingService.apply(new ApplyRequest("Ics2024", meeting5, unit, new Date(), new Date(), new Date(), chair, topics));
        meetingService.apply(new ApplyRequest("Ics2025", meeting6, unit, new Date(), new Date(), new Date(), chair, topics));
        meetingService.apply(new ApplyRequest("Ics2026", meeting7, unit, new Date(), new Date(), new Date(), chair, topics));
        meetingService.apply(new ApplyRequest("Ics2027", meeting8, unit, new Date(), new Date(), new Date(), chair, topics));
        meetingService.apply(new ApplyRequest("Ics2028", "The SoftWare Meeting9", unit, new Date(), new Date(), new Date(), chair, topics));
        meetingService.apply(new ApplyRequest("Ics2029", "The SoftWare Meeting0", unit, new Date(), new Date(), new Date(), chair, topics));

        meetingService.audit(new AuditRequest(meeting1, pass));
        meetingService.audit(new AuditRequest(meeting2, pass));
        meetingService.audit(new AuditRequest(meeting3, pass));
        meetingService.audit(new AuditRequest(meeting4, pass));
        meetingService.audit(new AuditRequest(meeting5, pass));
        meetingService.audit(new AuditRequest(meeting6, rejected));
        meetingService.audit(new AuditRequest(meeting7, rejected));

    }

    //?????????????????????
    void invite() {
        inviteService.invite(new InviteRequest(meeting1, a, chair));
        inviteService.invite(new InviteRequest(meeting2, a, chair));

        inviteService.invite(new InviteRequest(meeting1, b, chair));
        inviteService.invite(new InviteRequest(meeting1, c, chair));
        inviteService.invite(new InviteRequest(meeting1, e, chair));
        inviteService.invite(new InviteRequest(meeting2, f, chair));
        inviteService.invite(new InviteRequest(meeting2, i, chair));
        inviteService.invite(new InviteRequest(meeting2, j, chair));


    }

    //????????????????????????
    void acceptOrRejectInvitations() {
        inviteService.acceptInvitation(new AcceptInviteRequest(meeting1, accepted, a, topics));
        inviteService.acceptInvitation(new AcceptInviteRequest(meeting2, accepted, a, topics));

        inviteService.acceptInvitation(new AcceptInviteRequest(meeting1, accepted, b, topics));
        inviteService.acceptInvitation(new AcceptInviteRequest(meeting1, accepted, c, topics));
        inviteService.acceptInvitation(new AcceptInviteRequest(meeting1, rejected, e, topics));
        inviteService.acceptInvitation(new AcceptInviteRequest(meeting2, accepted, f, topics));
        inviteService.acceptInvitation(new AcceptInviteRequest(meeting2, accepted, i, topics));
        inviteService.acceptInvitation(new AcceptInviteRequest(meeting2, rejected, j, topics));


    }


    //??????????????????
    void openSubmission() {
        //?????????????????? ????????????
        Boolean result = initService.openSubmissionn(new InitRequest4(nothing, meeting1));
        assertTrue(result);
        result = initService.openSubmissionn(new InitRequest4(nothing, meeting2));
        assertTrue(result);
        //?????????????????? ??????????????????
        result = initService.openSubmissionn(new InitRequest4(nothing, "Thezxxc SoftWare Meeting1"));
        assertFalse(result);
    }

    //?????? ???????????????passed???inManuscript???????????????
    void showDashboard() {
        List<Meeting> list = initService.showDashboard();
        assertEquals(5, list.size());
    }


    //?????? ??????username???state??????????????????
    void showMeetingIAppliedFor() {
        List<Meeting> list = initService.showMeetingIAppliedFor(new InitRequest1(chair, "inAudit", nothing));
        List<Meeting> list1 = initService.showMeetingIAppliedFor(new InitRequest1(chair, pass, nothing));
        List<Meeting> list2 = initService.showMeetingIAppliedFor(new InitRequest1(chair, rejected, nothing));
        List<Meeting> list3 = initService.showMeetingIAppliedFor(new InitRequest1(chair, inManuscript, nothing));
        assertEquals(3, list.size());
        assertEquals(3, list1.size());
        assertEquals(2, list2.size());
        assertEquals(2, list3.size());
        //?????????
        assertEquals(0, initService.showMeetingIAppliedFor(new InitRequest1(o, "inAudit", nothing)).size());
    }

    //????????????username?????????????????????????????????
    void meetingIParticipatedIn() {
        List<Meeting> list = initService.meetingIParticipatedIn(new InitRequest(PCmember, a));
        assertEquals(2, list.size());
        List<Meeting> list1 = initService.meetingIParticipatedIn(new InitRequest(chairState, chair));
        assertEquals(5, list1.size());
        List<Meeting> list2 = initService.meetingIParticipatedIn(new InitRequest(PCmember, b));
        assertEquals(1, list2.size());
//?????????
        assertEquals(0, initService.meetingIParticipatedIn(new InitRequest(PCmember, o)).size());


    }

    //????????????username?????????????????????invited?????????
    void invitationInformation() {
        List<Invitations> list = initService.invitationInformation(new InitRequest1(a, nothing, nothing));
        assertEquals(2, list.size());
        List<Invitations> list1 = initService.invitationInformation(new InitRequest1(b, nothing, nothing));
        assertEquals(1, list1.size());
        //?????????
        assertEquals(0, initService.invitationInformation(new InitRequest1(o, nothing, nothing)).size());

    }

    //??????fullname??????????????????????????? List<User>
    void PCMemberInvitations() {
        InitRequest initRequest = new InitRequest(nothing, nothing);
        initRequest.setFullname(meeting1);
        List<User> list = initService.PCMemberInvitations(initRequest);
        assertEquals(4, list.size());

        initRequest.setFullname(meeting2);
        List<User> list1 = initService.PCMemberInvitations(initRequest);
        assertEquals(4, list1.size());
        //?????????
        initRequest.setFullname(o);
        assertEquals(8, initService.PCMemberInvitations(initRequest).size());


    }

    //??????fullname????????????????????????????????????????????????????????????
    void invitationsResult() {
        List<User> list = initService.invitationsResult(new InitRequest2(meeting1, accepted));
        List<User> list1 = initService.invitationsResult(new InitRequest2(meeting1, rejected));
        List<User> list2 = initService.invitationsResult(new InitRequest2(meeting2, accepted));
        List<User> list3 = initService.invitationsResult(new InitRequest2(meeting2, rejected));
        assertEquals(3, list.size());
        assertEquals(1, list1.size());
        assertEquals(3, list2.size());
        assertEquals(1, list3.size());
        //?????????
        assertEquals(0, initService.invitationsResult(new InitRequest2(o, rejected)).size());


    }

    //??????fullname????????????????????????PCmember List<MeetingAuthority>
    void PCMemberList() {
        InitRequest initRequest = new InitRequest(nothing, nothing);
        initRequest.setFullname(meeting1);
        List<MeetingAuthority> list = initService.PCMemberList(initRequest);
        assertEquals(3, list.size());
        //?????????
        initRequest.setFullname(o);
        assertEquals(0, initService.PCMemberList(initRequest).size());


    }

    //????????????????????????inAudit?????????
    void meetingApplications() {
        List<Meeting> list = initService.meetingApplications();
        assertEquals(3, list.size());
    }

    //??????state ????????????????????????state?????????  ??????
    void applicationHandled() {
        List<Meeting> list1 = initService.applicationHandled(new InitRequest2(nothing, pass));
        List<Meeting> list2 = initService.applicationHandled(new InitRequest2(nothing, rejected));
        List<Meeting> list3 = initService.applicationHandled(new InitRequest2(nothing, inManuscript));
        //   assertEquals(3,list.size());
        assertEquals(0, list1.size());
        assertEquals(0, list2.size());
        assertEquals(0, list3.size());
        //?????????
        assertEquals(0, initService.applicationHandled(new InitRequest2(nothing, "errorState")).size());


    }


    //????????????username?????????????????????
    void getPersonalInform() {

        User user = initService.getPersonalInform(new InitRequest1(chair, nothing, nothing));
        assertEquals(Lucy1, user.getFullname());
        User user1 = initService.getPersonalInform(new InitRequest1(f, nothing, nothing));
        System.out.print(user1.getFullname());
        assertEquals(Jack, user1.getFullname());

        //??????????????????
        assertNull(initService.getPersonalInform(new InitRequest1(o, nothing, nothing)));
    }

    //????????????fullname?????????????????????
    void getMeetingInfo() {
        Meeting meeting = initService.getMeetingInfo(new InitRequest2(meeting8, nothing)).get(0);
        assertEquals(meeting8, meeting.getFullname());

        //fullname????????????
        assertEquals(0, initService.getMeetingInfo(new InitRequest2("The SoftWare Meeting8cvx", nothing)).size());


    }


    //??????username???fullname???????????????PCmember
    void judgeWhetherPcmemberr() {
        List<String> list=new ArrayList<>();
        meetingAuthorityRepository.save(new MeetingAuthority(aa,meeting,chairState,list,nothing));
        InitRequest initRequest4 = new InitRequest(nothing, aa);
        initRequest4.setFullname(meeting);
        assertFalse(initService.judgeWhetherPcmemberr(initRequest4));
        //????????????
        InitRequest initRequest = new InitRequest(nothing, a);
        initRequest.setFullname(meeting2);
        Boolean result = initService.judgeWhetherPcmemberr(initRequest);
        MeetingAuthority meetingAuthority = meetingService.meetingAuthorityRepository.findByUsernameAndFullname(a, meeting2);
        String authority = meetingAuthority.getAuthority();
        logger.info(authority);

        assertTrue(result);
        //????????????PCmember?????????
        InitRequest initRequest2 = new InitRequest(nothing, e);
        initRequest2.setFullname(meeting1);
        Boolean result2 = initService.judgeWhetherPcmemberr(initRequest2);
        assertFalse(result2);
        //????????????????????????????????????????????????
        InitRequest initRequest3 = new InitRequest(nothing, "eeedd");
        initRequest3.setFullname("The SoftWare Meeting1dff");
        Boolean result3 = initService.judgeWhetherPcmemberr(initRequest3);
        assertFalse(result3);
    }


    void getMeetingStatee() {
        InitRequest initRequest = new InitRequest(nothing, nothing);
        initRequest.setFullname(meeting1);
        String state = initService.getMeetingStatee(initRequest);
        assertEquals(inManuscript, state);
        initRequest.setFullname(meeting4);
        state = initService.getMeetingStatee(initRequest);
        assertEquals(pass, state);

        //?????????????????????
        initRequest.setFullname("The SoftWare Meeting4sdssd");
        state = initService.getMeetingStatee(initRequest);
        assertEquals("error", state);
    }


    //??????username???????????????????????????????????????
    void getAllSubmissions() {
        List<Contribution> list = initService.getAllSubmissions(new InitRequest1(username1, nothing, nothing));
        assertEquals(1, list.size());
        List<Contribution> list1 = initService.getAllSubmissions(new InitRequest1("asdqwee", nothing, nothing));
        assertEquals(0, list1.size());
    }

    //??????fullname?????????????????????????????????
    void getAllArticle() {
        List<Contribution> list = initService.getAllArticle(new InitRequest4(nothing, meeting1));
        assertEquals(2, list.size());

    }

    //??????username???fullname????????????????????????
    void getArticleDetail() {
        List<Contribution> list = initService.getArticleDetail(new InitRequest4(username1, meeting1));

        assertEquals(meeting1, list.get(0).getTitle());
        List<Contribution> list1 = initService.getArticleDetail(new InitRequest4(username2, meeting1));
        assertEquals(meeting2, list1.get(0).getTitle());

    }


}