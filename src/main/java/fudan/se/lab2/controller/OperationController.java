package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.InitRequest4;
import fudan.se.lab2.service.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OperationController {
    private OperationService operationService;

    Logger logger = LoggerFactory.getLogger(InitController.class);

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping(value="/getTopicsByFullname")
    @ResponseBody
    public ResponseEntity<?> getTopicsByFullname(@RequestParam("meetingFullname")String fullname){
        logger.info("meetingFullname"+fullname);
        return ResponseEntity.ok(operationService.getTopicsByFullname(fullname));
    }
    @PostMapping(value="/getTopicsByFullnameAndusername")
    @ResponseBody
    public ResponseEntity<?> getTopicsByFullnameAndUsername(@RequestParam("meetingFullname")String fullname){
        logger.info("meetingFullname"+fullname);

        return ResponseEntity.ok(operationService.getTopicsByFullnameAndUsername(fullname));
    }

    //开启审稿
    @PostMapping(value="/menuOfMeeting/openReview")
    @ResponseBody
    public ResponseEntity<?> openReview(@RequestParam("fullname")String fullname,@RequestParam("strategy")String strategy){
        logger.info("fullname: "+fullname);
        logger.info("strategy: "+strategy);
        return ResponseEntity.ok(operationService.openReview(fullname,strategy));
    }

    //提交审稿信息 传递的id是审稿数据的id 不是contributionId
    @PostMapping(value="/setReview")
    @ResponseBody
    public ResponseEntity<?> setReview(@RequestParam("id")Long id,@RequestParam("grade")String grade,@RequestParam("comment")String comment,@RequestParam("confidence")String confidence){
        return ResponseEntity.ok(operationService.setReview(id,grade,comment,confidence));
    }

    //根据username和fullname得到当前用户的两种审稿的审稿信息
    @PostMapping(value="/getReviewContributions")
    @ResponseBody
    public ResponseEntity<?> getReviewContributions(@RequestParam("fullname")String fullname,@RequestParam("username")String username,@RequestParam("state")Boolean state){
        return ResponseEntity.ok(operationService.getReviewContributions(fullname,username,state));
    }

    //根据contributionId得到当前用户的待审稿的稿件信息
    @PostMapping(value="/getContribution")
    @ResponseBody
    public ResponseEntity<?> getContribution(@RequestParam("id")Long id){
        return ResponseEntity.ok(operationService.getContribution(id));
    }

    //根据contributionId得到当前用户的待审稿的稿件信息
    @PostMapping(value="/getContributionAndMeetingTopics")
    @ResponseBody
    public ResponseEntity<?> getContributionAndMeetingTopics(@RequestParam("id")Long id){

        return ResponseEntity.ok(operationService.getContributionAndMeetingTopics(id));
    }

    //根据contributionId和username得到某个审稿信息
    @PostMapping(value="/getReviewResults")
    @ResponseBody
    public ResponseEntity<?> getReviewResult(@RequestParam("id")Long id,@RequestParam("username")String username){
        return ResponseEntity.ok(operationService.getReviewResults(id,username));
    }

    //根据contributionId得到该稿件的已经审稿的Distribution即状态为true的稿件
    @PostMapping(value="/getContributionReviewResult")
    @ResponseBody
    public ResponseEntity<?> getContributionReviewResult(@RequestParam("id")Long id){
        return ResponseEntity.ok(operationService.getContributionReviewResult(id));
    }
    //判断一个会议的全部投稿是否已经全部审核完毕
    @PostMapping(value="/meetingReviewIsOver")
    @ResponseBody
    public ResponseEntity<?> meetingReviewIsOver(@RequestParam("fullname")String fullname){
        return ResponseEntity.ok(operationService.meetingReviewIsOver(fullname));
    }


}
