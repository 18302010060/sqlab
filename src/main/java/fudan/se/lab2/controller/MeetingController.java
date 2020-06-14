package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.controller.request.AuditRequest;

import fudan.se.lab2.service.MeetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



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



    @PostMapping(value="/openmeeting")

    public ResponseEntity<Boolean> openmeeting(@RequestBody ApplyRequest request){

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

    public ResponseEntity<Boolean> auditmeeting(@RequestBody AuditRequest request){
        logger.debug(request.toString());
        logger.info("会议全称："+request.getFullname());
        logger.info("会议状态："+request.getState());
        return ResponseEntity.ok(meetingService.audit(request));
    }




}



