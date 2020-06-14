package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;


import fudan.se.lab2.domain.*;
import fudan.se.lab2.service.InitService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LBW
 */
@RestController
public class InitController {
    private InitService initService;

    Logger logger = LoggerFactory.getLogger(InitController.class);

    @Autowired
    public InitController(InitService initService) {
        this.initService = initService;
    }



    @PostMapping("/menu/meetings")
    @ResponseBody
    public ResponseEntity<List<Meeting>> showDashboard(@RequestBody InitRequest request) {
        logger.info("");
        return ResponseEntity.ok(initService.showDashboard());
    }

    @PostMapping("/menu/applyMeetings")
    @ResponseBody
    public ResponseEntity<List<Meeting>> showMeetingIAppliedFor(@RequestBody InitRequest1 request) {
        logger.info("username:"+request.getUsername());
        logger.info("state:"+request.getState());

        return ResponseEntity.ok(initService.showMeetingIAppliedFor(request));
    }

    @PostMapping("/menu/participateMeetings")
    @ResponseBody
    public ResponseEntity<List<Meeting>> meetingIParticipatedIn(@RequestBody InitRequest request) {
        logger.info("authority:"+request.getAuthority());
        logger.info("username:"+request.getUsername());
        return ResponseEntity.ok(initService.meetingIParticipatedIn(request));
    }

    @PostMapping("/menu/invitations")
    @ResponseBody
    public ResponseEntity<List<Invitations>> invitationInformation(@RequestBody InitRequest1 request) {
        logger.info("username"+request.getUsername());
        return ResponseEntity.ok(initService.invitationInformation(request));
    }

    @PostMapping("/menuOfMeeting/chairInvitation")
    @ResponseBody
    public ResponseEntity<List<User>> PCMemberInvitations(@RequestBody InitRequest request) {

        return ResponseEntity.ok(initService.PCMemberInvitations(request));
    }

    @PostMapping("/menuOfMeeting/invitationResults")
    @ResponseBody
    public ResponseEntity<List<User>> invitationsResult(@RequestBody InitRequest2 request) {
        return ResponseEntity.ok(initService.invitationsResult(request));
    }

    @PostMapping("/menuOfMeeting/memberList")
    @ResponseBody
    public ResponseEntity<List<MeetingAuthority>> PCMemberList(@RequestBody InitRequest request) {
        return ResponseEntity.ok(initService.PCMemberList(request));
    }

    @PostMapping("/adminMenu/adminMeetings")
    @ResponseBody
    public ResponseEntity<List<Meeting>> meetingApplications() {
        return ResponseEntity.ok(initService.meetingApplications());
    }

    @PostMapping("/adminMenu/adminMeetingsHandled")
    @ResponseBody
    public ResponseEntity<List<Meeting>> applicationHandled(InitRequest2 request) {
        return ResponseEntity.ok(initService.applicationHandled(request));
    }

    //其他查询方法   //前端现在传参正常
    @PostMapping("/menu/submissions")
    @ResponseBody
    public ResponseEntity<List<Contribution>> allSubmission(@RequestBody InitRequest1 request) {
        return ResponseEntity.ok(initService.getAllSubmissions(request));
    }

    @PostMapping("/menu/setting")
    @ResponseBody
    public ResponseEntity<User> getPersonalInfo(@RequestBody InitRequest1 request) {
        return ResponseEntity.ok(initService.getPersonalInform(request));
    }

    //前端现在传参正常
    @PostMapping("/menuOfMeeting/detailsOfMeeting")
    @ResponseBody
    public ResponseEntity<List<Meeting>> getMeetingInform(@RequestBody InitRequest2 request) {
        return ResponseEntity.ok(initService.getMeetingInfo(request));
    }

    //前端现在传参正常
    @PostMapping("/menuOfMeeting/allArticles")
    @ResponseBody
    public ResponseEntity<List<Contribution>> getAllArticles(@RequestBody InitRequest4 request) {
        return ResponseEntity.ok(initService.getAllArticle(request));
    }
    //前端现在传参
    @PostMapping("/menuOfMeeting/detailsOfContribute")
    @ResponseBody
    public ResponseEntity<List<Contribution>> getArticleDetails(@RequestBody InitRequest4 request) {
        return ResponseEntity.ok(initService.getArticleDetail(request));
    }

    @PostMapping("/menuOfMeeting/openSubmission")
    @ResponseBody
    public ResponseEntity<Boolean> openSubmission(@RequestBody InitRequest4 request){
        logger.info("fullname  "+request.getFullname());
        return ResponseEntity.ok(initService.openSubmissionn(request));
    }

    @PostMapping("/menu/whetherPCmember")
    @ResponseBody
    public ResponseEntity<Boolean> judgeWhetherPcmember(@RequestBody InitRequest request){
        logger.info("fullname  "+request.getFullname());
        logger.info("username  "+request.getUsername());
        return ResponseEntity.ok(initService.judgeWhetherPcmemberr(request));
    }

    @PostMapping("/menu/meetingState")
    public ResponseEntity<String> getMeetingState(@RequestBody InitRequest request){
        logger.info("fullname  "+request.getFullname());
        logger.info("username  "+request.getUsername());
        return ResponseEntity.ok(initService.getMeetingStatee(request));
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



