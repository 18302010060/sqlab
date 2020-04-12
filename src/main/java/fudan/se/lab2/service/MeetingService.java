package fudan.se.lab2.service;

import fudan.se.lab2.controller.MeetingController;
import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.controller.request.AuditRequest;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
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

     MeetingRepository meetingRepository;

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

        Optional<Meeting> meeting1 = Optional.ofNullable(meetingRepository.findByShortname(shortname));
        if (meeting1.isPresent()) {
            logger.info("注册失败 ");
            return false;
        }
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
        String fullname = request.getFullname();//得到request中传来的参数
        String state = request.getState();


        try {//使用try catch 避免空指针异常
            Meeting meeting = meetingRepository.findByFullname(fullname);//根据会议全称查找会议
            if(state.equals("passed")){
                logger.info("审核通过");//使用logger，方便调试
            }
            meeting.setState(state);//设置meeting的状态
            meetingRepository.save(meeting);//更新会议
            String chair = meeting.getChair();//得到meeting的申请人
            if(state.equals("passed")){//如果会议通过
                MeetingAuthority meetingAuthority = new MeetingAuthority(chair,fullname,"chair");//创建meetingauthority，方便查找用户在不同会议中的身份
                meetingAuthorityRepository.save(meetingAuthority);//保存meetingAuthority
            }

            return true;
        }catch (Exception e){
            return false;
        }
        //String username = request.getUsername();
        /*String token = request.getToken();
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(new JwtConfigProperties());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        long id = user.getId();*/

       /* Meeting meeting = meetingRepository.findByFullname(fullname);
        if(state =="passed"){
            logger.info("审核通过");
        }
        meeting.setState(state);
        meetingRepository.save(meeting);
        String chair = meeting.getChair();
        MeetingAuthority meetingAuthority = new MeetingAuthority(chair,fullname,"chair");
        meetingAuthorityRepository.save(meetingAuthority);
        return true;*/

    }




}

