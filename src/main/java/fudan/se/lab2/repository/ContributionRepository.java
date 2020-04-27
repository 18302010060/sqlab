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


}
