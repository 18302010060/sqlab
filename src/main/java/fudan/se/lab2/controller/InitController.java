package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.service.AuthService;
import fudan.se.lab2.service.InitService;
import fudan.se.lab2.service.JwtUserDetailsService;
import fudan.se.lab2.service.MeetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LBW
 */
@RestController
public class InitController {
    private InitService initService;

    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    public InitController(InitService initService) {
        this.initService = initService;
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
   /* @GetMapping(value="/openmeeting")
    @ResponseBody
    public ResponseEntity<?> openmeeting(@RequestBody ApplyRequest request){

        logger.debug("ApplicationForm: " + request.toString());
        logger.info("fullname:"+request.getFullname());
        logger.info("shortname:"+request.getShortname());
        logger.info("place:"+request.getPlace());
        logger.info("time:"+request.getTime());
        logger.info("deadline:"+request.getDeadline());
        logger.info("releasetime:"+request.getReleasetime());
        logger.info("token: "+request.getToken());

        return ResponseEntity.ok(meetingService.apply(request));
    }*/
    @PostMapping("/menu/meetings")
    @ResponseBody
    public ResponseEntity<?> showDashboard(@RequestBody InitRequest request){
        return ResponseEntity.ok(initService.showDashboard());
    }
    @PostMapping("/menu/applyMeetings")
    @ResponseBody
    public ResponseEntity<?> showMeetingIAppliedFor(@RequestBody InitRequest1 request){
        return ResponseEntity.ok(initService.showMeetingIAppliedFor(request));
    }
    @PostMapping("/menu/participateMeetings")
    @ResponseBody
    public ResponseEntity<?> meetingIParticipatedIn(@RequestBody InitRequest request){
        return ResponseEntity.ok(initService.meetingIParticipatedIn(request));
    }
    @GetMapping("/menu/invitations")
    @ResponseBody
    public ResponseEntity<?> invitationInformation(@RequestBody InitRequest1 request){
        return ResponseEntity.ok(initService.invitationInformation(request));
    }
    @PostMapping("/menuOfMeeting/chairInvitation")
    @ResponseBody
    public ResponseEntity<?> PCMemberInvitations(@RequestBody InitRequest request){
        return ResponseEntity.ok(initService.PCMemberInvitations());
    }
    @PostMapping("/menuOfMeeting/invitationResults")
    @ResponseBody
    public ResponseEntity<?> invitationsResult(@RequestBody InitRequest2 request){
        return ResponseEntity.ok(initService.invitationsResult(request));
    }
    @PostMapping("/menuOfMeeting/memberList")
    @ResponseBody
    public ResponseEntity<?> PCMemberList(@RequestBody InitRequest request){
        return ResponseEntity.ok(initService.PCMemberList(request));
    }
    @PostMapping("/adminMenu/adminMeetings")
    @ResponseBody
    public ResponseEntity<?> meetingApplications(){
        return ResponseEntity.ok(initService.meetingApplications());
    }
    @PostMapping("/adminMenu/adminMeetingsHandled")
    @ResponseBody
    public ResponseEntity<?> applicationHandled(InitRequest2 request){
        return ResponseEntity.ok(initService.applicationHandled(request));
    }


    /*@PostMapping(value="/auditmeeting")
    @ResponseBody
    public ResponseEntity<?> auditmeeting(@RequestBody AuditRequest request){
        logger.debug(request.toString());
        logger.info("会议id"+request.getFullname());
        logger.info("会议状态"+request.getState());
        return ResponseEntity.ok(meetingService.audit(request));
    }
*/
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



