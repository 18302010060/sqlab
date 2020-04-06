package fudan.se.lab2.service;

import fudan.se.lab2.controller.MeetingController;
import fudan.se.lab2.controller.request.ContributionRequest;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.repository.ContributionRepository;
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

    @Autowired
    public ContributionService(ContributionRepository contributionRepository){
        this.contributionRepository=contributionRepository;
    }

    public Boolean submit(ContributionRequest request){
        String meetingFullname=request.getMeetingFullname();
        String title=request.getTitle();
        String summary=request.getSummary();
        String path=request.getPath();
        String token=request.getToken();

        //通过前端返回的token得到当前用户名username
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(new JwtConfigProperties());
        String username = jwtTokenUtil.getUsernameFromToken(token);

        Contribution contribution=new Contribution(username,meetingFullname,title,summary,path);

        Optional<Contribution> contribution1=Optional
                .ofNullable(contributionRepository.findContributionsByUsernameAndMeetingFullname(username,meetingFullname));


        if(contribution1.isPresent()){
            logger.info("论文已经提交过,不可以再次提交");
            return false;
        }
        else {
            contributionRepository.save(contribution);
            logger.info("论文提交成功" );
            return true;
        }

    }
}
