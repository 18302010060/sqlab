package fudan.se.lab2.controller;



import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.DiscussionRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.service.DiscussService;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<Boolean> discuss(@RequestParam("contributionId")Long contributionId,
                                     @RequestParam("username")String username,
                                     @RequestParam("comment")String comment,
                                     @RequestParam("subusername")String subusername,
                                     @RequestParam("subcomment")String subcomment,
                                     @RequestParam("responseUsername")String responseUsername,
                                     @RequestParam("time")String time,
                                     @RequestParam("subtime")String subtime,
                                     @RequestParam("mainOrSub")String mainOrSub){
        //public boolean discuss(Long contributionId,String username,String comment,String subusername,String subcomment,String responseUsername,Date time,Date subtime)
        logger.info("contributionId:"+contributionId);
        logger.info("username:"+username);
        logger.info("comment:"+comment);
        logger.info("subusername:"+subusername);
        logger.info("subcomment:"+subcomment);
        logger.info("responseUsername:"+responseUsername);
        logger.info("time:"+time);
        logger.info("subtime:"+subtime);


        return ResponseEntity.ok(discussService.discuss(contributionId,username,comment,subusername,subcomment,responseUsername,time,subtime,mainOrSub));
    }

    @PostMapping(value="/showDiscussionByMeetingFullnameAndUsername")
    @ResponseBody
    public ResponseEntity<List<Contribution>> showDiscussion(@RequestParam("meetingFullname")String meetingFullname,
                                            @RequestParam("username")String username){
        logger.info("meetingFullname:"+meetingFullname);
        logger.info("username:"+username);
        return ResponseEntity.ok(discussService.showContributionByMeetingFullnameAndUsername(meetingFullname,username));
    }

    @PostMapping(value = "/showDiscussionByMeetingFullnameAndEmployState")
    @ResponseBody
    public ResponseEntity<List<Contribution>> showDiscussionByMeetingFullnameAndEmployState(@RequestParam("meetingFullname")String meetingFullname,
                                                             @RequestParam("state")Boolean employState){
        logger.info("meetingFullname:"+meetingFullname);
        logger.info("state:"+employState);
        return ResponseEntity.ok(discussService.showContributionByMeetingFullnameAndEmployState(meetingFullname,employState));
    }
    @PostMapping(value = "/showDiscussionByMeetingFullnameAndState")
    @ResponseBody
    public ResponseEntity<List<Contribution>> showDiscussionByMeetingFullnameAndState(@RequestParam("meetingFullname")String meetingFullname,
                                                             @RequestParam("username")String username,
                                                             @RequestParam("state")String state){
        logger.info("meetingFullname:"+meetingFullname);
        logger.info("username:"+username);
        logger.info("state:"+state);
        return ResponseEntity.ok(discussService.showContributionByMeetingFullnameAndState(meetingFullname,username,state));
    }

    @PostMapping(value = "/firstConfirm")
    @ResponseBody
    public ResponseEntity<String> firstConfirm(@RequestParam("contributionId")Long contributionId,
                                          @RequestParam("username")String username,
                                          @RequestParam("grade")String grade,
                                          @RequestParam("comment")String comment,
                                          @RequestParam("confidence")String confidence,
                                          @RequestParam("discussionState")String discussionState){
        //Long contributionId,String username,String grade,String comment,String confidence
        logger.info("id:"+contributionId);
        logger.info("comment:"+ comment);
        logger.info("username:"+username);
        logger.info("grade:"+grade);
        logger.info("confidence:"+confidence);
        return ResponseEntity.ok(discussService.firstConfirm(contributionId,username,grade,comment,confidence,discussionState));
    }

    @PostMapping(value = "/openFirstDiscussion")
    @ResponseBody
    public ResponseEntity<Boolean> openFirstDiscussion(@RequestParam("meetingFullname")String meetingFullname) {
        logger.info("???????????????  "+meetingFullname);

        return ResponseEntity.ok(discussService.openFirstDiscussion(meetingFullname));
    }



    @PostMapping(value = "/showDiscussion")
    @ResponseBody
    public ResponseEntity<List<List<Discussion>>> showDiscussion(@RequestParam("contributionId")Long contributionId,@RequestParam("discussionState")String discussionState){
        return ResponseEntity.ok(discussService.showDiscussion(contributionId,discussionState));
    }
    @PostMapping(value="/showContributionByMeetingFullnameAndState")
    @ResponseBody
    public ResponseEntity<List<Contribution>> showContributionByMeetingFullnameAndState(@RequestParam("meetingFullname")String meetingFullname,
                                                                       @RequestParam("state")String state){
        return ResponseEntity.ok(discussService.showContributionByMeetingFullnameAndState(meetingFullname,state));

    }
    @PostMapping(value = "/ifAllContributionHasBeenConfirmed")
    @ResponseBody
    public ResponseEntity<Boolean> ifAllContributionHasBeenConfirmed(@RequestParam("meetingFullname")String meetingFullname){
        return ResponseEntity.ok(discussService.ifAllContributionHasBeenConfirmed(meetingFullname));
    }
    @PostMapping(value = "/releaseFirstResult")
    @ResponseBody
    public ResponseEntity<Boolean> releaseFirstResult(@RequestParam("meetingFullname")String meetingFullname){
        return ResponseEntity.ok(discussService.releaseFirstResult(meetingFullname));
    }

    //??????rebutta
    //rebuttal     ??????????????????id???rebuttal??????
    @PostMapping(value = "/rebuttal")
    @ResponseBody
    public ResponseEntity<Boolean> rebuttal(@RequestParam("id") Long id, @RequestParam("rebuttal") String rebuttal) {
        logger.info("??????Id???  "+id);
        logger.info("rebuttal:  "+rebuttal);
        return ResponseEntity.ok(discussService.rebuttal(id,rebuttal));
    }

    //???????????????????????????????????????????????????
    //?????????@RequestParam("username") String username,@RequestParam("rebuttalState") Boolean rebuttalState???false???rebuttal true??????rebuttal???

    @PostMapping(value = "/showContributionsByUsernameAndEmployStateAndState")
    @ResponseBody
    public ResponseEntity<List<Contribution>> showContributionsByUsernameAndEmployStateAndState(@RequestParam("username") String username,
                                                                                                @RequestParam("rebuttalState") Boolean rebuttalState) {
        logger.info("username???  "+username);
        return ResponseEntity.ok(discussService.showContributionsByUsernameAndRebuttalState(username,rebuttalState));
    }

    //  ??????????????????  ?????????????????????????????????????????????????????????????????? rebuttal
    //?????????@RequestParam("username") String username,@RequestParam("meetingFullname") String meetingFullname,@RequestParam("rebuttalState") Boolean rebuttalState
    @PostMapping(value = "/showContributionsByMeetingFullnameAndStateAndEmployStateAndRebuttalState")
    @ResponseBody
    public ResponseEntity<List<Contribution>> showContributionsByMeetingfullnameAndRebuttalState(@RequestParam("username") String username,
                                                                                                 @RequestParam("meetingFullname") String meetingFullname,
                                                                                                @RequestParam("rebuttalState") Boolean rebuttalState) {
        logger.info("meetingFullname???  "+meetingFullname);
        return ResponseEntity.ok(discussService.showContributionsByMeetingfullnameAndRebuttalState(username,meetingFullname,rebuttalState));
    }

    //  ??????????????????  ????????????????????????????????????
    @PostMapping(value = "/showContributionsSecondConfirm")
    @ResponseBody
    public ResponseEntity<List<Contribution>> showContributionsSecondConfirm(@RequestParam("username") String username,
                                                                             @RequestParam("meetingFullname") String meetingFullname) {
        logger.info("meetingFullname???  "+meetingFullname);
        return ResponseEntity.ok(discussService.showContributionsSecondConfirm(username,meetingFullname));
    }

    //????????????????????????????????????????????????
    @PostMapping(value="/releaseFinalResults")
    @ResponseBody
    public ResponseEntity<Boolean> releaseFinalResults(@RequestParam("meetingFullname")String meetingFullname){
        logger.info("meetingFullname???  "+meetingFullname);
        return ResponseEntity.ok(discussService.releaseResults(meetingFullname));
    }

    //?????? ??????/?????????????????????
    //??????@RequestParam("username")String username,@RequestParam("employState")Boolean employState  false?????????(????????????)???true??????
    @PostMapping(value="/employContributions")
    @ResponseBody
    public ResponseEntity<List<Contribution>> getEmployContributions(@RequestParam("username")String username,@RequestParam("employState")Boolean employState){
        logger.info("username???  "+username);
        logger.info("employState???  "+employState);

        return ResponseEntity.ok(discussService.getEmployContributions(username,employState));
    }

    //?????? ??????rebuttal????????? ?????????"firstDiscussionResultReleased"
    //????????? @RequestParam("username")String username
    @PostMapping(value="/inRebuttalContributions")
    @ResponseBody
    public ResponseEntity<List<Contribution>> getInRebuttalContributions(@RequestParam("username")String username){
        logger.info("username???  "+username);
        return ResponseEntity.ok(discussService.getInRebuttalContributions(username));
    }

    //???????????????????????????????????????????????????
    @PostMapping(value="/getNonEditableContributions")
    @ResponseBody
    public ResponseEntity<List<Contribution>> getNonEditableContributions(@RequestParam("username")String username){
        logger.info("username???  "+username);
        return ResponseEntity.ok(discussService.getNonEditableContributions(username));
    }





}



