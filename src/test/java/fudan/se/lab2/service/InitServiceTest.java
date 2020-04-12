package fudan.se.lab2.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class InitServiceTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private InitService initService;

    @Autowired
    private InviteService inviteService;
    @Test
    void showDashboard() {
    }

    @Test
    void showMeetingIAppliedFor() {
    }

    @Test
    void meetingIParticipatedIn() {
    }

    @Test
    void invitationInformation() {
    }

    @Test
    void PCMemberInvitations() {
    }

    @Test
    void invitationsResult() {
    }

    @Test
    void PCMemberList() {
    }

    @Test
    void meetingApplications() {
    }

    @Test
    void applicationHandled() {
    }

    @Test
    void getAllSubmissions() {
    }

    @Test
    void getPersonalInform() {
    }

    @Test
    void getMeetingInfo() {
    }

    @Test
    void getAllArticle() {
    }

    @Test
    void getArticleDetail() {
    }

    @Test
    void openSubmissionn() {
    }

    @Test
    void judgeWhetherPcmemberr() {
    }

    @Test
    void getMeetingStatee() {
    }
}