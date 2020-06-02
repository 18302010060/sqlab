package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Contribution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends CrudRepository<Contribution, Long> {
    Contribution findContributionByUsernameAndMeetingFullname(String username, String meetingFullname);
    List<Contribution> findAllByMeetingFullname(String fullname);

    List<Contribution> findAllByUsername(String username);

    List<Contribution> findAllContributionByUsernameAndMeetingFullname(String username,String fullname);
    Contribution findContributionById(Long id);


    List<Contribution> findAllByUsernameAndState(String username, String state);

    List<Contribution> findAllByMeetingFullnameAndUsernameAndState(String meetingFullname,String username,String state);
    List<Contribution> findAllByMeetingFullnameAndState(String meetingFullname,String state);
    List<Contribution> findAllByMeetingFullnameAndEmployState(String meetingFullname,Boolean employState);

    List<Contribution> findContributionsByUsernameAndStateAndEmployStateAndRebuttalState(String username,String state,Boolean employState,Boolean rebuttalState);
    List<Contribution> findContributionsByMeetingFullnameAndStateAndEmployStateAndRebuttalState(String meetingFullname,String state,Boolean employState,Boolean rebuttalState);
    List<Contribution> findContributionsByMeetingFullnameAndRebuttalState(String meetingFullname,Boolean rebuttalState);
    List<Contribution> findContributionsByUsernameAndEmployState(String username,Boolean employState);
    List<Contribution> findContributionsByUsernameAndStateAndEmployState(String username,String state,Boolean employState);
    List<Contribution> findContributionsByMeetingFullnameAndState(String meetingFullname,String state);

}
