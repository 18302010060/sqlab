package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ContributionRequest;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.repository.ContributionRepository;
import fudan.se.lab2.repository.MeetingAuthorityRepository;
import fudan.se.lab2.service.ContributionService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ContributionControllerTest {

    @Test
    void contribute() {
        ContributionRepository contributionRepository = new ContributionRepository() {
            @Override
            public Contribution findContributionByUsernameAndMeetingFullname(String username, String meetingFullname) {
                return null;
            }

            @Override
            public List<Contribution> findAllByMeetingFullname(String fullname) {
                return null;
            }

            @Override
            public <S extends Contribution> S save(S s) {
                return null;
            }

            @Override
            public <S extends Contribution> Iterable<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public Optional<Contribution> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public Iterable<Contribution> findAll() {
                return null;
            }

            @Override
            public Iterable<Contribution> findAllById(Iterable<Long> iterable) {
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
            public void delete(Contribution contribution) {

            }

            @Override
            public void deleteAll(Iterable<? extends Contribution> iterable) {

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
        ContributionService contributionService = new ContributionService(contributionRepository,meetingAuthorityRepository);
        ContributionController contributionController = new ContributionController(contributionService);
        ContributionRequest contributionRequest = new ContributionRequest("fullname","title","summary","path");
        contributionController.contribute(contributionRequest);
        Contribution contribution = contributionRepository.findContributionByUsernameAndMeetingFullname("username","meetingFullname");
        assertEquals("title",contribution.getTitle());

    }
}