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
        meetingRepository.save(new Meeting("Ics2020", "The SoftWare Meeting", new Date(), "shanghai", new Date(), new Date(), "aaaaa", list, "['a','b','c']"));

        Contribution contribution = new Contribution("bbbbb", "meeting", "title", "summary", "path", list, "['a','b','c']", "filename");
        contributionRepository.save(contribution);
        operationService.openReview("meeting", "Topic Based on Allocation Strategy");

        //注册用户
        authService.register(new RegisterRequest("Lucy123", "qwe123", "1@163.com", "fudan", "shanghai", "Lucy"));
        authService.register(new RegisterRequest("Jack000", "qwe123", "12@163.com", "fudan", "shanghai", "Lucy"));
        authService.register(new RegisterRequest("asdqwe", "qwe123", "145246@163.com", "fudan", "shanghai", "Lucy"));
        authService.register(new RegisterRequest("Lucy1234", "qwe123", "123@163.com", "fudan", "shanghai", "Lucy"));
        authService.register(new RegisterRequest("Jack0000", "qwe123", "1234@163.com", "fudan", "shanghai", "Lucy"));
        authService.register(new RegisterRequest("asdqwer", "qwe123", "12345@163.com", "fudan", "shanghai", "Lucy"));

        List<List<String>> topics3 = operationService.getTopicsByFullnameAndUsername("The SoftWare Meeting");
        assertNotNull(topics3);
        //审核会议通过
        Boolean result5 = meetingService.audit(new AuditRequest("The SoftWare Meeting", "passed"));
        assertTrue(result5);
        //返回会议topics
        List<String> topics1 = operationService.getTopicsByFullname("The SoftWare Meeting");
        assertNotNull(topics1);
        List<List<String>> topics2 = operationService.getTopicsByFullnameAndUsername("The SoftWare Meeting");
        assertNotNull(topics2);


        //开启投稿
        Boolean result = initService.openSubmissionn(new InitRequest4("", "The SoftWare Meeting"));
        assertTrue(result);

        //投稿
        List<String> list = new ArrayList<>();
        Contribution submission1 = contributionService.submit(new Contribution("Lucy123", "The SoftWare Meeting", "The SoftWare Meetingbcvbvbc", "The SoftWare cvbvcvb", "The SoftWare Meetingfgdg", list, "['a','b','c']", ""));
        Long id = submission1.getId();
        Boolean openResult4 = operationService.openReview("The SoftWare Meeting", "Topic Based on Allocation Strategy");
        assertFalse(openResult4);
        //邀请pcmember
        Boolean invite1 = inviteService.invite(new InviteRequest("The SoftWare Meeting", "Lucy123", "asdqwe"));
        assertTrue(invite1);
        Boolean invite2 = inviteService.invite(new InviteRequest("The SoftWare Meeting", "Jack000", "asdqwe"));
        assertTrue(invite2);
        Boolean invite3 = inviteService.invite(new InviteRequest("The SoftWare Meeting", "Lucy1234", "asdqwe"));
        assertTrue(invite3);

        //接受邀请
        Boolean accept1 = inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting", "accepted", "Jack000", "['a','b','c']"));
        assertTrue(accept1);

        Boolean accept2 = inviteService.acceptInvitation(new AcceptInviteRequest("The SoftWare Meeting", "accepted", "Lucy123", "['a','b','c']"));
        assertTrue(accept2);
        //开启审稿
        Boolean openResult3 = operationService.openReview("The SoftWare Meeting", "Topic Based on Allocation Strategy");
        assertTrue(openResult3);
        Boolean openResult1 = operationService.openReview("The SoftWare Meeting", "Topic Based on Allocation Strategy");
        assertTrue(openResult1);
        Boolean openResult2 = operationService.openReview("The SoftWare Meeting", "sdda");
        assertTrue(openResult2);

    }

}
