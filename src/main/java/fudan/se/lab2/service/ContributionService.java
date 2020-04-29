package fudan.se.lab2.service;

import fudan.se.lab2.controller.MeetingController;
import fudan.se.lab2.domain.Author;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.repository.AuthorRepository;
import fudan.se.lab2.repository.ContributionRepository;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
import fudan.se.lab2.repository.MeetingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContributionService {
    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    private ContributionRepository contributionRepository;
    private MeetingAuthorityRepository meetingAuthorityRepository;
    private MeetingRepository meetingRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public ContributionService(ContributionRepository contributionRepository, MeetingAuthorityRepository meetingAuthorityRepository,MeetingRepository meetingRepository,AuthorRepository authorRepository) {
        this.contributionRepository = contributionRepository;
        this.meetingAuthorityRepository = meetingAuthorityRepository;
        this.meetingRepository=meetingRepository;
        this.authorRepository=authorRepository;
    }


    public Contribution submit(Contribution contribution) {
        String meetingFullname = contribution.getMeetingFullname();
        String title = contribution.getTitle();
        String summary = contribution.getSummary();
        String path = contribution.getPath();
        String username = contribution.getUsername();

        Optional<Meeting> meeting=Optional.ofNullable(meetingRepository.findMeetingByFullnameAndChair(meetingFullname,username));
        if(meeting.isPresent()){
            logger.info("投稿人是会议的chair，投稿失败");
            return null;
        }else {
            Contribution contribution1=contributionRepository.save(contribution);
            Long id=contribution1.getId();
            logger.info("论文提交成功");
            return contribution1;
        }
    }

    public Boolean changeContribute(Long id, String path, String title, String summary, String username, String meetingFullname, List<String>topics,String topic){
        Contribution contribution=new Contribution(username,meetingFullname,title,summary,path,topics,topic);
        contribution.setId(id);
        contribution.setAuthors(new ArrayList<>());
        contributionRepository.save(contribution);
        logger.info("更新成功！");
        return true;
    }

    public Boolean addAuthor(Long id,String username,String unit,String area,String email,Long index){

        logger.info("ID:"+id);
        logger.info("username:"+username);
        logger.info("unit:"+unit);
        logger.info("area:"+area);
        logger.info("email:"+email);

        Author author=new Author(id,username,unit,area,email,index);
        authorRepository.save(author);
        Contribution contribution=contributionRepository.findContributionById(id);
        List<Author> list=contribution.getAuthors();
        list.add(author);
        //List<Author> list=authorRepository.findAllById(id);
        contribution.setAuthors(list);
        try {
            contributionRepository.save(contribution);
            logger.info("add成功");
            return true;
        }catch (Exception e){
            return false;
        }
    }







}
