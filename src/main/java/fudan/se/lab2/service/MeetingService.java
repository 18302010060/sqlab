package fudan.se.lab2.service;

import com.alibaba.fastjson.JSONArray;
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
import java.util.List;
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

        String fullname = request.getFullname();//会议全称
        String shortname = request.getShortname();//会议简称
        Date deadline = request.getDeadline();
        String place = request.getPlace();
        Date releasetime = request.getReleasetime();
        Date time = request.getTime();
        String chair = request.getChair();//主席
        String topics1 = request.getTopics();//jsonstring topics
        List<String> topics = JSONArray.parseArray(topics1,String.class);//list格式topics

        Meeting meeting = new Meeting(shortname, fullname, time, place, deadline, releasetime,chair,topics,topics1);//meeting字段：简称，全称，时间，地点，ddl，结果，主席，jsonstring topics

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
            String topic = meeting.getTopic();//得到jsonstring topic
            List<String> topics = meeting.getTopics();//得到会议topics
            if(state.equals("passed")){//如果会议通过
                MeetingAuthority meetingAuthority = new MeetingAuthority(chair,fullname,"chair",topics,topic);//创建meetingauthority，方便查找用户在不同会议中的身份
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

