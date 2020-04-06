package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ContributionRequest;
import fudan.se.lab2.service.ContributionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ContributionController {
    private ContributionService contributionService;
    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    public ContributionController(ContributionService contributionService){
        this.contributionService=contributionService;
    }

    @PostMapping(value="/contribute")
    @ResponseBody
    public ResponseEntity<?> contribute(@RequestBody ContributionRequest request){
        logger.debug("ContributionForm: " + request.toString());
        logger.info("meetingFullname:"+request.getMeetingFullname());
        logger.info("title:"+request.getTitle());
        logger.info("summary:"+request.getSummary());
        logger.info("path:"+request.getPath());
        logger.info("token: "+request.getToken());

        return ResponseEntity.ok(contributionService.submit(request));
    }
}
