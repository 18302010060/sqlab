package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.domain.Contribution3;
import fudan.se.lab2.domain.Distribution;
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
class OperationServiceTest {
    String username1 = "Lucy123";
    String username2="Jack000";
    String username3="asdqwe";
    String password="qwe123";
    String area="fudan";
    String unit="shanghai";
    String chair="Lucy";
    String a="['a','b','c']";
    String strategy="Topic Based on Allocation Strategy";
    String accepted="accepted";
    String nothing="";
    String fullname1="The SoftWare Meeting";
    @Autowired
    private OperationService operationService;

    @Autowired
    private InviteService inviteService;

    @Autowired
    private  InitService initService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private MeetingService meetingService;

    @Test
    void getTopics() {
        //注册用户
        authService.register(new RegisterRequest(username1, password, "1@163.com", area, unit, chair));
        authService.register(new RegisterRequest(username2, password, "12@163.com", area, unit, chair));
        authService.register(new RegisterRequest(username3, password, "145246@163.com", area, unit, chair));
        authService.register(new RegisterRequest("Lucy1234", password, "123@163.com", area, unit, chair));
        authService.register(new RegisterRequest("Jack0000", password, "1234@163.com", area, unit, chair));
        authService.register(new RegisterRequest("asdqwer", password, "12345@163.com", area, unit, chair));
        List<String> topics = operationService.getTopicsByFullname(fullname1);
        assertNull(topics);
        List<List<String>> topics3 = operationService.getTopicsByFullnameAndUsername(fullname1);
        assertNotNull(topics3);
        //用户登陆
        String token1 = authService.login(username3, password);
        assertNotNull(token1);
        //申请会议
        Boolean result1 = meetingService.apply(new ApplyRequest("Ics2020", fullname1, unit, new Date(), new Date(), new Date(), username3, a));
        assertTrue(result1);
        //审核会议通过
        Boolean result5 = meetingService.audit(new AuditRequest(fullname1, "passed"));
        assertTrue(result5);
        //返回会议topics
        List<String> topics1 = operationService.getTopicsByFullname(fullname1);
        assertNotNull(topics1);
        List<List<String>> topics2 = operationService.getTopicsByFullnameAndUsername(fullname1);
        assertNotNull(topics2);

        //邀请pcmember
        Boolean invite1 = inviteService.invite(new InviteRequest(fullname1, username1, username3));
        assertTrue(invite1);
        Boolean invite2 = inviteService.invite(new InviteRequest(fullname1, username2, username3));
        assertTrue(invite2);
        Boolean invite3 = inviteService.invite(new InviteRequest(fullname1, "Lucy1234", username3));
        assertTrue(invite3);

        //接受邀请
        Boolean accept1 = inviteService.acceptInvitation(new AcceptInviteRequest(fullname1, accepted, username2, a));
        assertTrue(accept1);

        Boolean accept2 = inviteService.acceptInvitation(new AcceptInviteRequest(fullname1, accepted, username1, a));
        assertTrue(accept2);

        //开启投稿
        Boolean result = initService.openSubmissionn(new InitRequest4(nothing, fullname1));
        assertTrue(result);

        //投稿
        List<String> list = new ArrayList<>();
        Contribution submission1 = contributionService.submit(new Contribution(username1, fullname1, "The SoftWare Meetingbcvbvbc", "The SoftWare cvbvcvb", "The SoftWare Meetingfgdg", list, a, nothing));
        Long id = submission1.getId();

        //开启投稿
        Boolean openResult3 = operationService.openReview(fullname1,strategy);
        assertTrue(openResult3);
        Boolean openResult1 = operationService.openReview(fullname1,strategy);
        assertTrue(openResult1);
        operationService.getSomething();
        Boolean openResult2 = operationService.openReview(fullname1,"sdda");
        assertTrue(openResult2);

        //审核稿件
        Boolean setReviewResult1 = operationService.setReview(id,"2","sdasdas","dasdsad");
        assertFalse(setReviewResult1);

        Boolean meetingOverResult = operationService.meetingReviewIsOver(fullname1);
        assertFalse(meetingOverResult);

        List<Distribution> distributionList = operationService.getReviewContributions(fullname1,username1,true);
        assertNotNull(distributionList);

        Contribution contribution = operationService.getContribution(id);
        assertNotNull(contribution);

        Contribution3 contribution3 = operationService.getContributionAndMeetingTopics(id);
        assertNotNull(contribution3);

        Distribution distribution = operationService.getReviewResults(id,username1);
        assertNull(distribution);

        List<Distribution> distributionList1 = operationService.getContributionReviewResult(id);
        assertNotNull(distributionList1);

        Boolean releaseResult = operationService.releaseResults(fullname1);
        assertTrue(releaseResult);

        List<Contribution> contributionList = operationService.getContributionByUsernameAndState(username1,"inReview");
        assertNotNull(contributionList);



    }




}