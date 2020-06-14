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
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class DiscussServiceTest {
    String username1="aaaaa";
    String username2="bbbbb";
    String usernamec="ccccc";
    String username4="ddddd";
    String username5="eeeee";
    String meeting="meeting";
    String meeting2="meeting2";
    String meeting3="meeting3";
    String place="shanghai";
    String topics="['a','b','c']";
    String title="title";
    String summary="summary";
    String path="path";
    String filename="filename";
    String wrong="wrong";
    String topic="topic";
    String comment="comment";
    String grade="grade";
    String confidence="confidence";
    String firstConfirm="firstConfirm";
    String expect="修改成功";
    String secondConfirm="secondConfirm";
    String inSecondConfirm="inSecondConfirm";
    String firstDiscussionResultReleased="firstDiscussionResultReleased";
    String inFirstConfirm="inFirstConfirm";
    String nothing="";
    String subusername="subusername";
    List<String> list = new ArrayList<>();

    @Autowired
    private DiscussService discussService;


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
    void Test() {
        meetingRepository.save(new Meeting(meeting, meeting, new Date(), place, new Date(), new Date(), username1, list, topics));
        meetingRepository.save(new Meeting(meeting2, meeting2, new Date(), place, new Date(), new Date(), username1, list, topics));
        //审稿完成的稿件
        Contribution contribution = new Contribution(username2, meeting, title, summary, path, list, topics, filename);
        contribution.setState("over");
        contributionRepository.save(contribution);

        //未审稿完成的稿件
        Contribution contribution2 = new Contribution(username2, meeting2, title, summary, path, list, topics, filename);
        contribution.setState(nothing);
        contributionRepository.save(contribution2);
        assertTrue(discussService.openFirstDiscussion(meeting));
        assertFalse(discussService.openFirstDiscussion(meeting2));
        assertFalse(discussService.openFirstDiscussion(meeting3));

        contribution = contributionRepository.findContributionByUsernameAndMeetingFullname(username2, meeting);
        Long id = contribution.getId();
        Distribution distribution = new Distribution(meeting, usernamec, id, title, username2, topic);
        //discussionRepository.save(new Discussion())
        Distribution distribution2 = new Distribution(meeting, username4, id, title, username2, topic);
        Distribution distribution3 = new Distribution(meeting, username5, id, title, username2, topic);

        distributionRespository.save(distribution);
        distributionRespository.save(distribution2);
        distributionRespository.save(distribution3);

        //测试discuss
        assertTrue(discussService.discuss(id, usernamec, comment, "subusername", "subcomment", "responseUsername", "time", "subTime", "mainOrSub"));
        discussionRepository.save(new Discussion(meeting, username4, comment, title, id, contribution.getEmployState(), nothing, nothing, nothing, nothing, nothing, nothing, inFirstConfirm));
        discussionRepository.save(new Discussion(meeting, username5, comment, title, id, contribution.getEmployState(), nothing, nothing, nothing, nothing, nothing, nothing, inFirstConfirm));

        //
        assertEquals("只能修改一次评审结果", discussService.firstConfirm(id, usernamec, grade, comment, confidence, nothing));
        assertEquals(expect, discussService.firstConfirm(id, usernamec, grade, comment, confidence, firstConfirm));
        assertEquals(expect, discussService.firstConfirm(id, username4, grade, comment, confidence, firstConfirm));
        assertEquals(expect, discussService.firstConfirm(id, username5, grade, comment, confidence, firstConfirm));

        assertEquals("您还没有讨论过，无法修改评分", discussService.firstConfirm(id, usernamec, grade, comment, confidence, wrong));

        assertTrue(discussService.ifAllContributionHasBeenConfirmed(meeting));
        assertFalse(discussService.ifAllContributionHasBeenConfirmed(meeting2));
        assertFalse(discussService.ifAllContributionHasBeenConfirmed(meeting3));

        assertTrue(discussService.releaseFirstResult(meeting));
        assertFalse(discussService.releaseFirstResult(meeting3));


        meetingAuthorityRepository.save(new MeetingAuthority(username1, meeting, "chair", list, nothing));
        meetingAuthorityRepository.save(new MeetingAuthority(usernamec, meeting, "PCmember", list, nothing));
        assertNotEquals(null, discussService.showContributionByMeetingFullnameAndUsername(meeting, usernamec));
        assertNotEquals(null, discussService.showContributionByMeetingFullnameAndUsername(meeting, username1));
        assertNotEquals(null, discussService.showContributionByMeetingFullnameAndEmployState(meeting, false));
        assertNotEquals(null, discussService.showContributionByMeetingFullnameAndState(meeting, username1, firstDiscussionResultReleased));
        assertNotEquals(null, discussService.showContributionByMeetingFullnameAndState(meeting, usernamec, firstDiscussionResultReleased));

        assertNotEquals(null, discussService.showDiscussion(id, firstConfirm));
        assertNotEquals(null, discussService.showContributionByMeetingFullnameAndState(meeting, firstDiscussionResultReleased));


        //关于rebuttal部分
        assertTrue(discussService.rebuttal(id, "rebuttal"));
        assertNotEquals(null, discussService.showContributionsByUsernameAndRebuttalState(username2, true));
        assertNotEquals(null, discussService.showContributionsByMeetingfullnameAndRebuttalState(username1, meeting, true));
        assertNotEquals(null, discussService.showContributionsByMeetingfullnameAndRebuttalState(usernamec, meeting, true));

        assertNotEquals(null, discussService.getNonEditableContributions(username2));
        assertNotEquals(null, discussService.getInRebuttalContributions(username2));
        discussService.getNonEditableContributions(wrong);
        discussService.getInRebuttalContributions(wrong);
        //二轮讨论
        discussionRepository.save(new Discussion(meeting,username4,comment,title,id,contribution.getEmployState(),nothing,nothing,nothing,nothing,nothing,nothing,inSecondConfirm));
        discussionRepository.save(new Discussion(meeting,username5,comment,title,id,contribution.getEmployState(),nothing,nothing,nothing,nothing,nothing,nothing,inSecondConfirm));
        discussionRepository.save(new Discussion(meeting,usernamec,comment,title,id,contribution.getEmployState(),nothing,nothing,nothing,nothing,nothing,nothing,inSecondConfirm));

        assertEquals( expect,discussService.firstConfirm(id,usernamec,grade,comment,confidence,secondConfirm));
        assertEquals( expect,discussService.firstConfirm(id,username4,grade,comment,confidence,secondConfirm));
        assertEquals( expect,discussService.firstConfirm(id,username5,grade,comment,confidence,secondConfirm));


        assertTrue(discussService.releaseResults(meeting));
        assertNotEquals(null, discussService.showContributionsSecondConfirm(usernamec, meeting));
        assertNotEquals(null, discussService.showContributionsSecondConfirm(username1, meeting));

        contribution.setEmployState(true);
        contributionRepository.save(contribution);
        assertNotEquals(null, discussService.getEmployContributions(username2, true));


    }

}