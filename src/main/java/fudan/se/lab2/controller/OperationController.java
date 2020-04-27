package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.InitRequest4;
import fudan.se.lab2.service.InitService;
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
    public ResponseEntity<?> openReview(@RequestBody InitRequest4 request){
        logger.info("fullname: "+request.getFullname());
        return ResponseEntity.ok(operationService.openReview(request));
    }


}
