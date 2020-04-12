package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.controller.request.AuditRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
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
    @Autowired
    private AuthService authService;

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private MeetingService meetingService;

    @Test
    void ApplyAndAduitTest() throws Exception {
        //注册
        Boolean result=authService.register(new RegisterRequest("asdqwe","qwe123","145246@163.com","fudan","shanghai","Lucy"));
        assertTrue(result);
        //登录
        String token1=authService.login("asdqwe","qwe123");
        assertNotNull(token1);
        //会议申请
        Boolean result1=meetingService.apply(new ApplyRequest("Ics2020","The SoftWare Meeting","shanghai",new Date(),new Date(),new Date(), "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rxd2UiLCJpYXQiOjE1ODY2NzgzNDIsImV4cCI6MTU4NjY5NjM0Mn0.NtVBnj3oq3cmNnMalO71sWUrzZFhGh5t-eN9gY8DDwPmLdwsWJtLDff0uq64c3JxTajDhjzenkvSbI0dqJ4Xtg"));
        assertTrue(result1);
        //全称相同，缩写不同，会议申请失败
        Boolean result2=meetingService.apply(new ApplyRequest("Ics202000","The SoftWare Meeting","shanghai",new Date(),new Date(),new Date(),"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rxd2UiLCJpYXQiOjE1ODY2NzgzNDIsImV4cCI6MTU4NjY5NjM0Mn0.NtVBnj3oq3cmNnMalO71sWUrzZFhGh5t-eN9gY8DDwPmLdwsWJtLDff0uq64c3JxTajDhjzenkvSbI0dqJ4Xtg"));
        assertFalse(result2);
        //缩写相同，全称不同，会议申请失败
        Boolean result3=meetingService.apply(new ApplyRequest("Ics2020","The SoftWare Meeting on","shanghai",new Date(),new Date(),new Date(),"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rxd2UiLCJpYXQiOjE1ODY2NzgzNDIsImV4cCI6MTU4NjY5NjM0Mn0.NtVBnj3oq3cmNnMalO71sWUrzZFhGh5t-eN9gY8DDwPmLdwsWJtLDff0uq64c3JxTajDhjzenkvSbI0dqJ4Xtg"));
        assertFalse(result3);
        //缩写、全称都不相同，申请成功
        Boolean result4=meetingService.apply(new ApplyRequest("Ics202000","The SoftWare Meeting on","shanghai",new Date(),new Date(),new Date(),"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rxd2UiLCJpYXQiOjE1ODY2NzgzNDIsImV4cCI6MTU4NjY5NjM0Mn0.NtVBnj3oq3cmNnMalO71sWUrzZFhGh5t-eN9gY8DDwPmLdwsWJtLDff0uq64c3JxTajDhjzenkvSbI0dqJ4Xtg"));
        assertTrue(result4);

        //会议通过
        Boolean result5=meetingService.audit(new AuditRequest("The SoftWare Meeting","passed"));
        assertTrue(result5);

        Boolean result6=meetingService.audit(new AuditRequest("The SoftWare Meeting on","passed"));
        assertTrue(result6);

        try {
            meetingService.audit(new AuditRequest("SoftWare Meeting on","passed"));
        }catch (Exception e){
            System.out.print("会议全称错误，查找不到会议!!!");
        }

    }

}