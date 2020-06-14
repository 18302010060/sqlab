package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class DistributeTest {//集成测试：开启审稿分配稿件策略是否成功，对不同情况进行测试
    String username1 = "Lucy123";
    String username2="Jack000";
    String username3="asdqwe";
    String password="qwe123";
    String area="fudan";
    String unit="shanghai";
    String fullname="Lucy";
    String topics="['a','b','c']";
    String fullname1="The SoftWare Meeting";
    String meeting="meeting";
    String accepted="accepted";
    String nothing="";
    List<String> list = new ArrayList<>();
    @Autowired
    private OperationService operationService;
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private InviteService inviteService;

    @Autowired
    private InitService initService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private MeetingService meetingService;


    @Test
    void Test() {
        meetingRepository.save(new Meeting("Ics2020", fullname1, new Date(), unit, new Date(), new Date(), "aaaaa", list, topics));

        Contribution contribution = new Contribution("bbbbb", meeting, "title", "summary", "path", list, topics, "filename");
        contributionRepository.save(contribution);
        operationService.openReview(meeting, "Topic Based on Allocation Strategy");

        //注册用户
        authService.register(new RegisterRequest(username1, password, "1@163.com", area, unit, fullname));
        authService.register(new RegisterRequest(username2, password, "12@163.com", area, unit, fullname));
        authService.register(new RegisterRequest(username3, password, "145246@163.com", area, unit, fullname));
        authService.register(new RegisterRequest("Lucy1234", password, "123@163.com", area, unit, fullname));
        authService.register(new RegisterRequest("Jack0000", password, "1234@163.com", area, unit, fullname));
        authService.register(new RegisterRequest("asdqwer", password, "12345@163.com", area, unit, fullname));

        List<List<String>> topics3 = operationService.getTopicsByFullnameAndUsername(fullname1);
        assertNotNull(topics3);
        //审核会议通过
        Boolean result5 = meetingService.audit(new AuditRequest(fullname1, "passed"));
        assertTrue(result5);
        //返回会议topics
        List<String> topics1 = operationService.getTopicsByFullname(fullname1);
        assertNotNull(topics1);
        List<List<String>> topics2 = operationService.getTopicsByFullnameAndUsername(fullname1);
        assertNotNull(topics2);


        //开启投稿
        Boolean result = initService.openSubmissionn(new InitRequest4(nothing, fullname1));
        assertTrue(result);

        //投稿
        List<String> list = new ArrayList<>();
        Contribution submission1 = contributionService.submit(new Contribution(username1, fullname1, "The SoftWare Meetingbcvbvbc", "The SoftWare cvbvcvb", "The SoftWare Meetingfgdg", list, topics, nothing));
        Long id = submission1.getId();
        Boolean openResult4 = operationService.openReview(fullname1, "Topic Based on Allocation Strategy");
        assertFalse(openResult4);
        //邀请pcmember
        Boolean invite1 = inviteService.invite(new InviteRequest(fullname1, username1, username3));
        assertTrue(invite1);
        Boolean invite2 = inviteService.invite(new InviteRequest(fullname1, username2, username3));
        assertTrue(invite2);
        Boolean invite3 = inviteService.invite(new InviteRequest(fullname1, "Lucy1234", username3));
        assertTrue(invite3);

        //接受邀请
        Boolean accept1 = inviteService.acceptInvitation(new AcceptInviteRequest(fullname1, accepted, username2, topics));
        assertTrue(accept1);

        Boolean accept2 = inviteService.acceptInvitation(new AcceptInviteRequest(fullname1, accepted, username1, topics));
        assertTrue(accept2);
        //开启审稿
        Boolean openResult3 = operationService.openReview(fullname1, "Topic Based on Allocation Strategy");
        assertTrue(openResult3);
        Boolean openResult1 = operationService.openReview(fullname1, "Topic Based on Allocation Strategy");
        assertTrue(openResult1);
        Boolean openResult2 = operationService.openReview(fullname1, "sdda");
        assertTrue(openResult2);

    }

}
