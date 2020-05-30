package fudan.se.lab2.controller;


import fudan.se.lab2.service.DiscussService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping
public class DiscussController {
    private DiscussService discussService;
    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    public DiscussController(DiscussService discussService){
        this.discussService=discussService;
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