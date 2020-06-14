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
    String place="shanghai";
    String chair="a";
    String topics="['a','b','c']";
    String fullname1="The SoftWare Meeting";
    String fullname2="The SoftWare Meeting on";
    String fullname3="The SoftWare Meeting on the";
    String pass="passed";
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
        Boolean result1 = meetingService.apply(new ApplyRequest("Ics2020", fullname1, place, new Date(), new Date(), new Date(), chair, topics));
        assertTrue(result1);
        Boolean result2 = meetingService.apply(new ApplyRequest("Ics202000", fullname2, place, new Date(), new Date(), new Date(), chair, topics));
        assertTrue(result2);
        Boolean result3 = meetingService.apply(new ApplyRequest("Ics2019", fullname3, place, new Date(), new Date(), new Date(), chair, topics));
        assertTrue(result3);

        //会议审核 通过/不通过

        Boolean result4 = meetingService.audit(new AuditRequest(fullname1, pass));
        assertTrue(result4);
        Boolean result5 = meetingService.audit(new AuditRequest(fullname2, "rejected"));
        assertTrue(result5);
        Boolean result6 = meetingService.audit(new AuditRequest(fullname3, pass));
        assertTrue(result6);

    }


}
