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
    public ResponseEntity<?> invite(@RequestParam("meetingFullname")String meetingFullname,
                                    @RequestParam("username")String username,
                                    @RequestParam("discussion")String discussion,
                                    @RequestParam("contributionId")Long contributionId){
        //String meetingFullname,String username,String discussion,long contributionId
        return ResponseEntity.ok(discussService.discuss(meetingFullname,username,discussion,contributionId));
    }

    @PostMapping(value="/showDiscussion")
    @ResponseBody
    public ResponseEntity<?> acceptInvite(@RequestParam("contributionId")Long contributionId){

        return ResponseEntity.ok(discussService.showDiscussion(contributionId));
    }

    @PostMapping(value = "/firstConfirm")
    @ResponseBody
    public ResponseEntity<?> firstConfirm(@RequestParam("contributionId")Long contributionId,
                                          @RequestParam("username")String username,
                                          @RequestParam("grade")String grade,
                                          @RequestParam("comment")String comment,
                                          @RequestParam("confidence")String confidence){
        //Long contributionId,String username,String grade,String comment,String confidence
        return ResponseEntity.ok(discussService.firstConfirm(contributionId,username,grade,comment,confidence));
    }

    //rebuttal     前端传稿件的id和rebuttal信息
    @PostMapping(value = "/rebuttal")
    @ResponseBody
    public ResponseEntity<Boolean> rebuttal(@RequestParam("id") Long id, @RequestParam("rebuttal") String rebuttal) {
        logger.info("稿件Id：  "+id);
        logger.info("rebuttal:  "+rebuttal);
        return ResponseEntity.ok(discussService.rebuttal(id,rebuttal));
    }


}



