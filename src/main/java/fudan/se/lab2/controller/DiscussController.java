package fudan.se.lab2.controller;


import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Discussion;
import fudan.se.lab2.domain.Invitations;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.DiscussionRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.service.DiscussService;
import fudan.se.lab2.service.InviteService;


import fudan.se.lab2.service.DiscussService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * @author LBW
 */
@RestController
@RequestMapping
public class DiscussController {

    private DiscussService discussService;
    private DiscussionRepository discussionRepository;
    private InvitationRepository invitationRepository;
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(DiscussController.class);

    @Autowired
    public DiscussController(DiscussService discussService) {
        this.discussService = discussService;
    }


    @PostMapping(value="/discuss")
    @ResponseBody
    public ResponseEntity<?> discuss(@RequestParam("contributionId")Long contributionId,
                                     @RequestParam("username")String username,
                                     @RequestParam("comment")String comment,
                                     @RequestParam("subusername")String subusername,
                                     @RequestParam("subcomment")String subcomment,
                                     @RequestParam("responseUsername")String responseUsername,
                                     @RequestParam("time")Date time,
                                     @RequestParam("subtime")Date subtime){
        //public boolean discuss(Long contributionId,String username,String comment,String subusername,String subcomment,String responseUsername,Date time,Date subtime)
        logger.info("contributionId:"+contributionId);
        logger.info("username:"+username);
        logger.info("comment:"+comment);
        logger.info("subusername:"+subusername);
        logger.info("subcomment:"+subcomment);
        logger.info("responseUsername:"+responseUsername);
        logger.info("time:"+time);
        logger.info("subtime:"+subtime);


        return ResponseEntity.ok(discussService.discuss(contributionId,username,comment,subusername,subcomment,responseUsername,time,subtime));
    }

    @PostMapping(value="/showDiscussionByMeetingFullnameAndUsername")
    @ResponseBody
    public ResponseEntity<?> showDiscussion(@RequestParam("meetingFullname")String meetingFullname,
                                            @RequestParam("username")String username){
        logger.info("meetingFullname:"+meetingFullname);
        logger.info("username:"+username);
        return ResponseEntity.ok(discussService.showContributionByMeetingFullnameAndUsername(meetingFullname,username));
    }

    @PostMapping(value = "/showDiscussionByMeetingFullnameAndEmployState")
    @ResponseBody
    public ResponseEntity<?> showDiscussionByMeetingFullnameAndEmployState(@RequestParam("meetingFullname")String meetingFullname,
                                                             @RequestParam("state")Boolean employState){
        logger.info("meetingFullname:"+meetingFullname);
        logger.info("state:"+employState);
        return ResponseEntity.ok(discussService.showContributionByMeetingFullnameAndEmployState(meetingFullname,employState));
    }
    @PostMapping(value = "/showDiscussionByMeetingFullnameAndState")
    @ResponseBody
    public ResponseEntity<?> showDiscussionByMeetingFullnameAndState(@RequestParam("meetingFullname")String meetingFullname,
                                                             @RequestParam("username")String username,
                                                             @RequestParam("state")String state){
        logger.info("meetingFullname:"+meetingFullname);
        logger.info("username:"+username);
        logger.info("state:"+state);
        return ResponseEntity.ok(discussService.showContributionByMeetingFullnameAndState(meetingFullname,username,state));
    }

    @PostMapping(value = "/firstConfirm")
    @ResponseBody
    public ResponseEntity<?> firstConfirm(@RequestParam("contributionId")Long contributionId,
                                          @RequestParam("username")String username,
                                          @RequestParam("grade")String grade,
                                          @RequestParam("comment")String comment,
                                          @RequestParam("confidence")String confidence){
        //Long contributionId,String username,String grade,String comment,String confidence
        logger.info("id:"+contributionId);
        logger.info("comment:"+ comment);
        logger.info("username:"+username);
        logger.info("grade:"+grade);
        logger.info("confidence:"+confidence);
        return ResponseEntity.ok(discussService.firstConfirm(contributionId,username,grade,comment,confidence));
    }

    @PostMapping(value = "/openFirstDiscussion")
    @ResponseBody
    public ResponseEntity<?> openFirstDiscussion(@RequestParam("meetingFullname")String meetingFullname) {
        logger.info("会议全称：  "+meetingFullname);

        return ResponseEntity.ok(discussService.openFirstDiscussion(meetingFullname));
    }

    //rebuttal     前端传稿件的id和rebuttal信息
    @PostMapping(value = "/rebuttal")
    @ResponseBody
    public ResponseEntity<Boolean> rebuttal(@RequestParam("id") Long id, @RequestParam("rebuttal") String rebuttal) {
        logger.info("稿件Id：  "+id);
        logger.info("rebuttal:  "+rebuttal);
        return ResponseEntity.ok(discussService.rebuttal(id,rebuttal));
    }

    @PostMapping(value = "/showDiscussion")
    @ResponseBody
    public ResponseEntity<?> showDiscussion(@RequestParam("contributionId")Long contributionId){
        return ResponseEntity.ok(discussService.showDiscussion(contributionId));
    }
    @PostMapping(value="/showContributionByMeetingFullnameAndState")
    @ResponseBody
    public ResponseEntity<?> showContributionByMeetingFullnameAndState(@RequestParam("meetingFullname")String meetingFullname,
                                                                       @RequestParam("state")String state){
        return ResponseEntity.ok(discussService.showContributionByMeetingFullnameAndState(meetingFullname,state));

    }
    @PostMapping(value = "/ifAllContributionHasBeenConfirmed")
    @ResponseBody
    public ResponseEntity<?> ifAllContributionHasBeenConfirmed(@RequestParam("meetingFullname")String meetingFullname){
        return ResponseEntity.ok(discussService.ifAllContributionHasBeenConfirmed(meetingFullname));
    }
    @PostMapping(value = "/releaseFirstResult")
    @ResponseBody
    public ResponseEntity<?> releaseFirstResult(@RequestParam("meetingFullname")String meetingFullname){
        return ResponseEntity.ok(discussService.releaseFirstResult(meetingFullname));
    }


}



