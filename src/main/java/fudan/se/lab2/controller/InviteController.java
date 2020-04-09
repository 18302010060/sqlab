package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.Invitations;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.UserRepository;
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
    private InvitationRepository invitationRepository;
    private UserRepository userRepository;

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



        return ResponseEntity.ok(inviteService.invite(request));
    }

    @PostMapping(value="/acceptinvite")
    @ResponseBody
    public ResponseEntity<?> acceptInvite(@RequestBody AcceptInviteRequest request){



        return ResponseEntity.ok(inviteService.acceptInvitation(request));
    }
    /*@GetMapping("/findAll/{page}/{size}")
    public Invitations findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        //PageRequest request = PageRequest.of(page,size);
        return (Invitations) invitationRepository.findAll();
    }



    @PostMapping("/findByFullUsername/{fullname}")
    public User findByFullUsername(@PathVariable("fullname") String fullname){//查找用户
        return userRepository.findByFullname(fullname);
    }
    @PostMapping("/findByFullname/{fullname}")
    public Invitations findByFullname(@PathVariable("fullname") String fullname){//查找用户接收到的邀请
        return invitationRepository.findByUsername(fullname);
    }
    @PostMapping("/findByFullname/{fullname}")
    public Invitations findByFullname1(@PathVariable("fullname") String chair){//查找用户发出的邀请
        return invitationRepository.findByChair(chair);
    }*/
    /*@GetMapping("/menu/invitations/{username}")
    public Invitations findByUsername(@PathVariable("username") String username){
        return invitationRepository.findByUsername(username);
    }
    @GetMapping("/menu/invitations/{state}")
    public Meeting */


    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */


}



