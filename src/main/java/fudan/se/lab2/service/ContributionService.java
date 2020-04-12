package fudan.se.lab2.service;

import fudan.se.lab2.controller.MeetingController;
import fudan.se.lab2.controller.request.ContributionRequest;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.repository.ContributionRepository;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
import fudan.se.lab2.security.jwt.JwtConfigProperties;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContributionService {
    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    private ContributionRepository contributionRepository;
    private MeetingAuthorityRepository meetingAuthorityRepository;

    @Autowired
    public ContributionService(ContributionRepository contributionRepository, MeetingAuthorityRepository meetingAuthorityRepository) {
        this.contributionRepository = contributionRepository;
        this.meetingAuthorityRepository = meetingAuthorityRepository;
    }


    public Boolean submit(ContributionRequest request) {
        String meetingFullname = request.getMeetingFullname();
        String title = request.getTitle();
        String summary = request.getSummary();
        String path = request.getPath();
        String username = request.getUsername();

        //通过前端返回的token得到当前用户名username
       // JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(new JwtConfigProperties());
        //String username = jwtTokenUtil.getUsernameFromToken(token);

        Contribution contribution = new Contribution(username, meetingFullname, title, summary, path);

        Optional<Contribution> contribution1 = Optional
                .ofNullable(contributionRepository.findContributionByUsernameAndMeetingFullname(username, meetingFullname));


        if (contribution1.isPresent()) {
            logger.info("论文已经提交过,不可以再次提交");
            return false;
        } else {
            Optional<MeetingAuthority> meetingAuthority = Optional
                    .ofNullable(meetingAuthorityRepository.findByUsernameAndFullname(username,meetingFullname));
            //不是该会议PCmemeber author chair
            if (!meetingAuthority.isPresent()) {
                MeetingAuthority meetingAuthority1 = new MeetingAuthority(username, meetingFullname, "author");
                meetingAuthorityRepository.save(meetingAuthority1);
                contributionRepository.save(contribution);
                logger.info("论文提交成功");
                return true;

            }
            else{
                //在该会议中，身份不是chair，可以投稿，并更改身份为author
                if(!meetingAuthority.get().getAuthority().equals("chair")){
                    MeetingAuthority meetingAuthority1=meetingAuthorityRepository.findByUsername(username);
                    logger.info("身份 "+meetingAuthority1.getAuthority());
                    meetingAuthority1.setAuthority("author");
                    meetingAuthorityRepository.save(meetingAuthority1);
                    contributionRepository.save(contribution);
                    logger.info("论文提交成功");
                    return true;
                }
                //身份为chair，不可投稿
                else{
                    logger.info("提交失败");
                    return false;
                }


            }

        }
    }



}
