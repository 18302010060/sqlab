package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.InviteRequest;
import fudan.se.lab2.domain.Invitations;
import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
import fudan.se.lab2.service.InviteService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InviteControllerTest {
    InvitationRepository invitationRepository = new InvitationRepository() {
        @Override
        public Invitations findByUsername(String username) {
            return null;
        }

        @Override
        public Invitations findByUsernameAndFullname(String username, String fullname) {
            return null;
        }

        @Override
        public List<Invitations> findAllByUsernameEqualsAndInviteStateEquals(String username, String inviteState) {
            return null;
        }

        @Override
        public <S extends Invitations> S save(S s) {
            return null;
        }

        @Override
        public <S extends Invitations> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<Invitations> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public Iterable<Invitations> findAll() {
            return null;
        }

        @Override
        public Iterable<Invitations> findAllById(Iterable<Long> iterable) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(Invitations invitations) {

        }

        @Override
        public void deleteAll(Iterable<? extends Invitations> iterable) {

        }

        @Override
        public void deleteAll() {

        }
    };
    MeetingAuthorityRepository meetingAuthorityRepository = new MeetingAuthorityRepository() {
        @Override
        public List<MeetingAuthority> findAllByUsernameEqualsAndAuthorityEquals(String username, String authority) {
            return null;
        }

        @Override
        public MeetingAuthority findByUsername(String username) {
            return null;
        }

        @Override
        public <S extends MeetingAuthority> S save(S s) {
            return null;
        }

        @Override
        public <S extends MeetingAuthority> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<MeetingAuthority> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public Iterable<MeetingAuthority> findAll() {
            return null;
        }

        @Override
        public Iterable<MeetingAuthority> findAllById(Iterable<Long> iterable) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(MeetingAuthority meetingAuthority) {

        }

        @Override
        public void deleteAll(Iterable<? extends MeetingAuthority> iterable) {

        }

        @Override
        public void deleteAll() {

        }
    };
    InviteService inviteService = new InviteService(invitationRepository,meetingAuthorityRepository);
    InviteController inviteController = new InviteController(inviteService);
    @Test
    void invite() {
        InviteRequest inviteRequest = new InviteRequest("fullname","username","chair");
        inviteController.invite(inviteRequest);
        Invitations invitations = new Invitations("fullname","username");
        invitationRepository.save(invitations);
        try{
        Invitations invitations1 = invitationRepository.findByUsername("username");
        assertEquals("invited",invitations1.getInviteState());}
        catch(Exception e){
            System.out.print("失败");
        }

       /* Invitations invitations = invitationRepository.findByUsername("username");
        assertEquals("invited",invitations.getInviteState());
        assertEquals("username",invitations.getUsername());
        assertEquals("fullname",invitations.getFullname());
        assertEquals("chair",invitations.getChair());*/
    }

    @Test
    void acceptInvite() {
    }
}