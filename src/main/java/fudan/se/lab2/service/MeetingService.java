package fudan.se.lab2.service;

import fudan.se.lab2.controller.MeetingController;
import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.controller.request.AuditRequest;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.repository.MeetingRepository;
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

    private MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository) {

        this.meetingRepository = meetingRepository;
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
            logger.info("注册失败 "     );
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
        int id = request.getId();
        String state = request.getState();
        Meeting meeting = meetingRepository.findById(id);
        meeting.setState(state);
        meetingRepository.save(meeting);
        return true;
    }




}

