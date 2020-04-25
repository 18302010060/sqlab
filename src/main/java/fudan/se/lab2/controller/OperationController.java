package fudan.se.lab2.controller;

import fudan.se.lab2.service.InitService;
import fudan.se.lab2.service.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
