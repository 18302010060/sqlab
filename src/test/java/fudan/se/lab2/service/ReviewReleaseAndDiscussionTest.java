package fudan.se.lab2.service;

import fudan.se.lab2.domain.*;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ReviewReleaseAndDiscussionTest {//集成测试：审稿发布以及讨论过程测试
    List<String> list=new ArrayList<>();

    @Autowired
    private  DiscussService discussService;


    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private DistributionRespository distributionRespository;

    @Autowired
    private DiscussionRepository discussionRepository;
    @Autowired
    private MeetingAuthorityRepository meetingAuthorityRepository;

    @Test
    void Test(){
        meetingRepository.save(new Meeting("meeting","meeting",new Date(),"shanghai",new Date(),new Date(),"aaaaa",list,"['a','b','c']"));
        meetingRepository.save(new Meeting("meeting2","meeting2",new Date(),"shanghai",new Date(),new Date(),"aaaaa",list,"['a','b','c']"));
        //审稿完成的稿件
        Contribution contribution=new Contribution("bbbbb","meeting","title","summary","path",list,"['a','b','c']","filename");
        contribution.setState("over");
        contributionRepository.save(contribution);

        //未审稿完成的稿件
        Contribution contribution2=new Contribution("bbbbb","meeting2","title","summary","path",list,"['a','b','c']","filename");
        contribution.setState("");
        contributionRepository.save(contribution2);
        assertTrue(discussService.openFirstDiscussion("meeting"));
        assertFalse(discussService.openFirstDiscussion("meeting2"));
        assertFalse(discussService.openFirstDiscussion("meeting3"));

        contribution=contributionRepository.findContributionByUsernameAndMeetingFullname("bbbbb","meeting");
        Long id=contribution.getId();
        //审稿信息
        Distribution distribution=new Distribution("meeting","ccccc",id,"title","bbbbb","topic");
        Distribution distribution2=new Distribution("meeting","ddddd",id,"title","bbbbb","topic");
        Distribution distribution3=new Distribution("meeting","eeeee",id,"title","bbbbb","topic");

        distributionRespository.save(distribution);
        distributionRespository.save(distribution2);
        distributionRespository.save(distribution3);

        //测试开启discuss
        assertTrue(discussService.discuss(id,"ccccc","comment","subusername","subcomment","responseUsername","time","subTime","mainOrSub"));
        discussionRepository.save(new Discussion("meeting","ddddd","comment","title",id,contribution.getEmployState(),"","","","","","","inFirstConfirm"));
        discussionRepository.save(new Discussion("meeting","eeeee","comment","title",id,contribution.getEmployState(),"","","","","","","inFirstConfirm"));

        //讨论后是否修改结果
        assertEquals("只能修改一次评审结果",discussService.firstConfirm(id,"ccccc","grade","comment","confidence",""));
        assertEquals( "修改成功",discussService.firstConfirm(id,"ccccc","grade","comment","confidence","firstConfirm"));
        assertEquals( "修改成功",discussService.firstConfirm(id,"ddddd","grade","comment","confidence","firstConfirm"));
        assertEquals( "修改成功",discussService.firstConfirm(id,"eeeee","grade","comment","confidence","firstConfirm"));

        assertEquals( "您还没有讨论过，无法修改评分",discussService.firstConfirm(id,"ccccc","grade","comment","confidence","wrong"));

        //判断一轮讨论是否完成
        assertTrue(discussService.ifAllContributionHasBeenConfirmed("meeting"));
        assertFalse(discussService.ifAllContributionHasBeenConfirmed("meeting2"));
        assertFalse(discussService.ifAllContributionHasBeenConfirmed("meeting3"));

        assertTrue(discussService.releaseFirstResult("meeting"));
        assertFalse(discussService.releaseFirstResult("meeting3"));


        meetingAuthorityRepository.save(new MeetingAuthority("aaaaa","meeting","chair",list,""));
        meetingAuthorityRepository.save(new MeetingAuthority("ccccc","meeting","PCmember",list,""));
        //检测稿件的state
        assertNotEquals(null,discussService.showContributionByMeetingFullnameAndUsername("meeting","ccccc"));
        assertNotEquals(null,discussService.showContributionByMeetingFullnameAndUsername("meeting","aaaaa"));
        assertNotEquals(null,discussService.showContributionByMeetingFullnameAndEmployState("meeting",false));
        assertNotEquals(null,discussService.showContributionByMeetingFullnameAndState("meeting","aaaaa","firstDiscussionResultReleased"));
        assertNotEquals(null,discussService.showContributionByMeetingFullnameAndState("meeting","ccccc","firstDiscussionResultReleased"));

        assertNotEquals(null,discussService.showDiscussion(id,"firstConfirm"));
        assertNotEquals(null,discussService.showContributionByMeetingFullnameAndState("meeting","firstDiscussionResultReleased"));


        //关于rebuttal部分
        assertTrue(discussService.rebuttal(id,"rebuttal"));
        assertFalse(discussService.releaseResults("meeting"));
        //二轮讨论
        discussionRepository.save(new Discussion("meeting","ddddd","comment","title",id,contribution.getEmployState(),"","","","","","","inSecondConfirm"));
        discussionRepository.save(new Discussion("meeting","eeeee","comment","title",id,contribution.getEmployState(),"","","","","","","inSecondConfirm"));
        discussionRepository.save(new Discussion("meeting","ccccc","comment","title",id,contribution.getEmployState(),"","","","","","","inSecondConfirm"));

        assertEquals( "修改成功",discussService.firstConfirm(id,"ccccc","grade","comment","confidence","secondConfirm"));
        assertEquals( "修改成功",discussService.firstConfirm(id,"ddddd","grade","comment","confidence","secondConfirm"));
        assertEquals( "修改成功",discussService.firstConfirm(id,"eeeee","grade","comment","confidence","secondConfirm"));

        //二轮结果发布
        assertTrue(discussService.releaseResults("meeting"));




    }
}
