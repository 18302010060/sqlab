package fudan.se.lab2.service;

import fudan.se.lab2.controller.MeetingController;
import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.controller.request.AuditRequest;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtConfigProperties;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

//18302010060 黄怡清'part
@Service
public class MeetingService {
    Logger logger = LoggerFactory.getLogger(MeetingController.class);

     MeetingRepository meetingRepository;
     UserRepository userRepository;
     MeetingAuthorityRepository meetingAuthorityRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository,MeetingAuthorityRepository meetingAuthorityRepository) {

        this.meetingRepository = meetingRepository;
        this.meetingAuthorityRepository = meetingAuthorityRepository;
    }

    public Boolean apply(ApplyRequest request) {

        String fullname = request.getFullname();
        String shortname = request.getShortname();
        Date deadline = request.getDeadline();
        String place = request.getPlace();
        Date releasetime = request.getReleasetime();
        Date time = request.getTime();
        String token = request.getToken();

        Meeting meeting = new Meeting(shortname, fullname, time, place, deadline, releasetime);

        Optional<Meeting> meeting2 = Optional.ofNullable(meetingRepository.findByFullname(fullname));
        if (meeting2.isPresent()) {
            logger.info("注册失败 ");
            return false;
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Meeting has existed!" );
        } else {
            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(new JwtConfigProperties());
            String chair = jwtTokenUtil.getUsernameFromToken(token);

            logger.info("chair: " + chair);
            meeting.setChair(chair);

            meetingRepository.save(meeting);
            logger.info("注册成功 " );
            return true;
        }
    }
    public boolean audit(AuditRequest request){
        String fullname = request.getFullname();
        String state = request.getState();
        /*String token = request.getToken();
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(new JwtConfigProperties());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        long id = user.getId();*/

        Meeting meeting = meetingRepository.findByFullname(fullname);
        if(meeting.getState()=="passed"){
            logger.info("已通过审核");
            return false;
        }
        else {
            meeting.setState(state);
            meetingRepository.save(meeting);
            String chair = meeting.getChair();
            MeetingAuthority meetingAuthority = new MeetingAuthority(chair,fullname,"chair");
            meetingAuthorityRepository.save(meetingAuthority);
            return true;
        }
    }




}

