package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ApplyRequest;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.controller.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

//18302010060 黄怡清'part
@Service
public class MeetingService {

    private MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository) {

        this.meetingRepository = meetingRepository;
    }

    public Meeting apply(ApplyRequest request) {

        String fullname = request.getFullname();
        String shortname = request.getShortname();
        Date deadline = request.getDeadline();
        String place = request.getPlace();
        Date releasetime = request.getReleasetime();
        Date time = request.getTime();
        Meeting meeting = new Meeting(shortname,fullname,time,place,deadline,releasetime);

        meetingRepository.save(meeting);

        return meeting;
    }
   /* public Meeting get(ApplyRequest request){
        String fullname = request.getFullname();
        return meetingRepository.findByFullname(fullname);
    }
*/


}

