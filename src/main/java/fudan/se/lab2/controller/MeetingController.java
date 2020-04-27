package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.controller.request.AuditRequest;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.service.AuthService;
import fudan.se.lab2.service.JwtUserDetailsService;
import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.service.MeetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LBW
 */
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private MeetingService meetingService;


    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }


    /*@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug("RegistrationForm: " + request.toString());

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.debug("LoginForm: " + request.toString());

        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }*/
     /* @PostMapping(value="/openmeeting")

    public ResponseEntity<?> openmeeting(@RequestParam("fullname")String fullname, @RequestParam("shortname")String shortname,
                                         @RequestParam("place")String place, @RequestParam("time") Date time,
                                         @RequestParam("deadline")Date deadline,@RequestParam("releasetime")Date releasetime,
                                         @RequestParam("chair")String chair,@RequestParam("topics")String topics){
        ApplyRequest request=new ApplyRequest(shortname,fullname,place,time,deadline,releasetime,chair,topics);
*/
    @PostMapping(value="/openmeeting")

    public ResponseEntity<?> openmeeting(@RequestBody ApplyRequest request){

        logger.debug("ApplicationForm: " + request.toString());
        logger.info("fullname:"+request.getFullname());
        logger.info("shortname:"+request.getShortname());
        logger.info("place:"+request.getPlace());
        logger.info("time:"+request.getTime());
        logger.info("deadline:"+request.getDeadline());
        logger.info("releasetime:"+request.getReleasetime());
        logger.info("chair: "+request.getChair());
        return ResponseEntity.ok(meetingService.apply(request));
    }
    @PostMapping(value="/auditmeeting")

    public ResponseEntity<?> auditmeeting(@RequestBody AuditRequest request){
        logger.debug(request.toString());
        logger.info("会议全称："+request.getFullname());
        logger.info("会议状态："+request.getState());
        return ResponseEntity.ok(meetingService.audit(request));
    }

    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */
    /*@PostMapping("/findAll/{page}/{size}")
    public Meeting findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        //PageRequest request = PageRequest.of(page,size);
        return (Meeting) meetingRepository.findAll();
    }



    @PostMapping("/findByState/{state}")
    public Meeting findByState(@PathVariable("state") String state){//查找以通过和待审核会议
        return meetingRepository.findByState(state);
    }*/



}



