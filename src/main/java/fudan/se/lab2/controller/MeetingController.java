package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.service.AuthService;
import fudan.se.lab2.service.JwtUserDetailsService;
import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.service.MeetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LBW
 */
@RestController
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
    @PostMapping(value="/openmeeting")
    @ResponseBody
    public ResponseEntity<?> openmeeting(@RequestBody ApplyRequest request){

        logger.debug("ApplicationForm: " + request.toString());
        logger.info("fullname:"+request.getFullname());
        logger.info("shortname:"+request.getShortname());
        logger.info("place:"+request.getPlace());
        logger.info("time:"+request.getTime());
        logger.info("deadline:"+request.getDeadline());
        logger.info("releasetime:"+request.getReleasetime());

        return ResponseEntity.ok(meetingService.apply(request));
    }

    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */


}



