package fudan.se.lab2.service;

import fudan.se.lab2.controller.MeetingController;
import fudan.se.lab2.controller.request.ContributionRequest;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.repository.ContributionRepository;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.security.jwt.JwtConfigProperties;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContributionService {
    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    private ContributionRepository contributionRepository;
    private MeetingAuthorityRepository meetingAuthorityRepository;
    private MeetingRepository meetingRepository;

    @Autowired
    public ContributionService(ContributionRepository contributionRepository, MeetingAuthorityRepository meetingAuthorityRepository,MeetingRepository meetingRepository) {
        this.contributionRepository = contributionRepository;
        this.meetingAuthorityRepository = meetingAuthorityRepository;
        this.meetingRepository=meetingRepository;
    }


    public Boolean submit(Contribution contribution) {
        String meetingFullname = contribution.getMeetingFullname();
        String title = contribution.getTitle();
        String summary = contribution.getSummary();
        String path = contribution.getPath();
        String username = contribution.getUsername();

        Optional<Meeting> meeting=Optional.ofNullable(meetingRepository.findMeetingByFullnameAndChair(meetingFullname,username));
        if(meeting.isPresent()){
            logger.info("投稿人是会议的chair，投稿失败");
            return false;
        }else {
            contributionRepository.save(contribution);
            logger.info("论文提交成功");
            return true;
        }
    }

    public Boolean changeContribute(Long id, String path, String title, String summary, String username, String meetingFullname, List<String>topics,List<String> authors){
        Contribution contribution=new Contribution(username,meetingFullname,title,summary,path,topics,authors);
        contribution.setId(id);
        contributionRepository.save(contribution);
        logger.info("更新成功！");
        return true;
    }


}
