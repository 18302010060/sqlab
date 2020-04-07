package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.service.InviteService;
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
public class InviteController {

    private InviteService inviteService;

    Logger logger = LoggerFactory.getLogger(InviteController.class);

    @Autowired
    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }


    /*@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug("RegistrationForm: " + request.toString());

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.debug("LoginForm: " + request.toString());

        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }*/

    @PostMapping(value="/invite")
    @ResponseBody
    public ResponseEntity<?> invite(@RequestBody InviteRequest request){
        logger.debug(request.toString());
        logger.info(request.getFullname());
        logger.info(request.getInviteState());
        logger.info(request.getUserName());


        return ResponseEntity.ok(inviteService.invite(request));
    }

    @PostMapping(value="/acceptinvite")
    @ResponseBody
    public ResponseEntity<?> acceptInvite(@RequestBody AcceptInviteRequest request){
        logger.debug(request.toString());
        logger.info(request.getFullname());
        logger.info(request.getInviteState());


        return ResponseEntity.ok(inviteService.acceptInvitation(request));
    }

    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */


}



