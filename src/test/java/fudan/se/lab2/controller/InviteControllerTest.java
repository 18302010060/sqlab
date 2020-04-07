package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.InviteRequest;
import fudan.se.lab2.domain.Invitations;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.MeetingRepository;
import fudan.se.lab2.service.InviteService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InviteControllerTest {
    private MeetingRepository meetingRepository = new MeetingRepository() {
        @Override
        public Meeting findByFullname(String fullname) {
            return null;
        }

        @Override
        public Meeting findById(int id) {
            return null;
        }

        @Override
        public Meeting findByState(String state) {
            return null;
        }

        @Override
        public <S extends Meeting> S save(S s) {
            return null;
        }

        @Override
        public <S extends Meeting> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<Meeting> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public Iterable<Meeting> findAll() {
            return null;
        }

        @Override
        public Iterable<Meeting> findAllById(Iterable<Long> iterable) {
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
        public void delete(Meeting meeting) {

        }

        @Override
        public void deleteAll(Iterable<? extends Meeting> iterable) {

        }

        @Override
        public void deleteAll() {

        }
    };
    private InvitationRepository invitationRepository = new InvitationRepository() {
        @Override
        public Invitations findByUsernameAndAndFullname(String username, String fullname) {
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
    InviteService inviteService = new InviteService(meetingRepository,invitationRepository);
    InviteController inviteController = new InviteController(inviteService);

    @Test
    void invite() {
        InviteRequest inviteRequest = new InviteRequest("fullname","username");
        inviteController.invite(inviteRequest);

    }

    @Test
    void acceptInvite() {
    }
}