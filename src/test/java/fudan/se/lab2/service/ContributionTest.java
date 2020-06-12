package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.controller.request.AuditRequest;
import fudan.se.lab2.domain.Contribution;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ContributionTest {//集成测试：论⽂投稿和修改模块
    @Autowired
    private ContributionService contributionService;

    @Autowired
    private MeetingService meetingService;

    @Test
    void test(){
        //申请一个会议
        meetingService.apply(new ApplyRequest("a", "a", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));
        meetingService.audit(new AuditRequest("a", "passed"));
        //chair投稿
        assertNull(contributionService.submit(new Contribution("a", "a", "The SoftWare Meetingvxv", "The SoftWare Meetingxbcxvc", "The SoftWare Meetinggfgds",new ArrayList<>(), "['a','b','c']", "")));
        //正确投稿
        Contribution contribution=contributionService.submit(new Contribution("b", "a", "The SoftWare Meetingvxv", "The SoftWare Meetingxbcxvc", "The SoftWare Meetinggfgds",new ArrayList<>(), "['a','b','c']", ""));
        assertNotNull(contribution);
        assertTrue(contributionService.addAuthor(contribution.getId(),"asdqr","Fudan University","Shanghai","12ewr3456@dfsd.sdd",(long)2));
        assertTrue(contributionService.addAuthor(contribution.getId(),"asd","Fudan University","Shanghai","123456@dfsd.sdd",(long)1));

        //更改稿件信息
        Boolean result=contributionService.changeContribute(contribution.getId(),"newPath","newTitle","new summary","b","a", new ArrayList<>(), "['a','b','c']", "");
        assertTrue(result);

    }
}
