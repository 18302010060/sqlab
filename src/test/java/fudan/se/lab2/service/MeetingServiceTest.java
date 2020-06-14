package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.controller.request.AuditRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class MeetingServiceTest {
    String unit="shanghai";
    String passed="passed";
    String shortname1="Ics2020";
    String shortname2="Ics202000";
    String fullname="The SoftWare Meeting";
    String fullname2="The SoftWare Meeting on";
    @Autowired
    private AuthService authService;

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private MeetingService meetingService;

    @Test
    void ApplyAndAduitTest() throws Exception {
        //登录
        String token1 = authService.login("asdqwe", "qwe123");
        assertNotNull(token1);
        //会议申请
        Boolean result1 = meetingService.apply(new ApplyRequest(shortname1, fullname, unit, new Date(), new Date(), new Date(), "asdqwe", "['a','b','c']"));
        assertTrue(result1);
        //全称相同，缩写不同，会议申请失败
        Boolean result2 = meetingService.apply(new ApplyRequest(shortname2, fullname, unit, new Date(), new Date(), new Date(), "asdqwe", "['a','b','c']"));
        assertFalse(result2);
        //缩写相同，全称不同，会议申请失败
        Boolean result3 = meetingService.apply(new ApplyRequest(shortname1, fullname2, unit, new Date(), new Date(), new Date(), "asdqwe", "['a','b','c']"));
        assertFalse(result3);
        //缩写、全称都不相同，申请成功
        Boolean result4 = meetingService.apply(new ApplyRequest(shortname2, fullname2, unit, new Date(), new Date(), new Date(), "asdqwe", "['a','b','c']"));
        assertTrue(result4);

        //会议通过
        Boolean result5 = meetingService.audit(new AuditRequest(fullname, passed));
        assertTrue(result5);

        Boolean result6 = meetingService.audit(new AuditRequest(fullname2, passed));
        assertTrue(result6);

        try {
            meetingService.audit(new AuditRequest("SoftWare Meeting on", passed));
        } catch (Exception e) {
            System.out.print("会议全称错误，查找不到会议!!!");
        }

    }

}