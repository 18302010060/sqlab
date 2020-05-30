package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.controller.request.AuditRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class AdminAuditTest {//集成测试：管理员模块（会议申请、管理员对其进行审核）
    @Autowired
    private AuthService authService;
    @Autowired
    private MeetingService meetingService;

    @Test
    void test(){
        //管理员登录
        String token1=authService.login("admin", "password");
        assertNotNull(token1);

        //申请会议
        Boolean result1 = meetingService.apply(new ApplyRequest("Ics2020", "The SoftWare Meeting", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));
        assertTrue(result1);
        Boolean result2 = meetingService.apply(new ApplyRequest("Ics202000", "The SoftWare Meeting on", "shanghai", new Date(), new Date(), new Date(), "a", "['a','b','c']"));
        assertTrue(result2);
        Boolean result3 = meetingService.apply(new ApplyRequest("Ics2019", "The SoftWare Meeting on the", "shanghai", new Date(), new Date(), new Date(), "d", "['a','b','c']"));
        assertTrue(result3);

        //会议审核 通过/不通过

        Boolean result4 = meetingService.audit(new AuditRequest("The SoftWare Meeting", "passed"));
        assertTrue(result4);
        Boolean result5 = meetingService.audit(new AuditRequest("The SoftWare Meeting on", "rejected"));
        assertTrue(result5);
        Boolean result6 = meetingService.audit(new AuditRequest("The SoftWare Meeting on the", "passed"));
        assertTrue(result6);

    }


}
