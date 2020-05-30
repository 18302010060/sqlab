package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.controller.request.AuditRequest;
import fudan.se.lab2.controller.request.InitRequest1;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class MeetingApplyTest {//集成测试：会议申请管理模块
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private InitService initService;

    @Test
    void test(){
        //用户会议申请时出现的情况
        meetingApply();

        //管理员通过会议
        meetingService.audit(new AuditRequest("a", "passed"));
        meetingService.audit(new AuditRequest("b", "passed"));
        meetingService.audit(new AuditRequest("c", "passed"));
        meetingService.audit(new AuditRequest("d", "passed"));
        //管理员拒绝通过的会议
        meetingService.audit(new AuditRequest("e", "rejected"));
        meetingService.audit(new AuditRequest("f", "rejected"));
        meetingService.audit(new AuditRequest("g", "rejected"));
        //管理员未审核的会议

        //所有会议一览界面
        assertEquals(4,initService.showDashboard().size());
        //我申请的会议一览界面 Meetings I Applied for  对应于不同的会议申请状态（通过/不通过/申请中）
        assertEquals(4,initService.showMeetingIAppliedFor(new InitRequest1("a","passed","")).size());
        assertEquals(3,initService.showMeetingIAppliedFor(new InitRequest1("a","rejected","")).size());
        assertEquals(3,initService.showMeetingIAppliedFor(new InitRequest1("a","inAudit","")).size());


    }

    public void meetingApply(){
        //正确会议申请
        Boolean result1 = meetingService.apply(new ApplyRequest("a", "a", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));

        Boolean result2 = meetingService.apply(new ApplyRequest("b", "b", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));

        Boolean result3 = meetingService.apply(new ApplyRequest("c", "c", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));

        Boolean result4 = meetingService.apply(new ApplyRequest("d", "d", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));

        Boolean result5 = meetingService.apply(new ApplyRequest("e", "e", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));

        Boolean result6 = meetingService.apply(new ApplyRequest("f", "f", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));

        Boolean result7 = meetingService.apply(new ApplyRequest("g", "g", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));

        Boolean result8 = meetingService.apply(new ApplyRequest("h", "h", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));
        Boolean result9 = meetingService.apply(new ApplyRequest("i", "i", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));

        Boolean result10 = meetingService.apply(new ApplyRequest("j", "j", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));

        //错误会议申请：fullname重复
        Boolean result11 = meetingService.apply(new ApplyRequest("a", "aaa", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));
        assertFalse(result11);
        //错误会议申请：shortname重复
        Boolean result12 = meetingService.apply(new ApplyRequest("aaaaa", "a", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));
        assertFalse(result12);
        //错误会议申请：fullname和shortname都重复
        Boolean result13 = meetingService.apply(new ApplyRequest("a", "a", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));
        assertFalse(result13);



    }
}
