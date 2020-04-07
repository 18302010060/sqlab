package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Contribution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends CrudRepository<Contribution, Long> {
    Contribution findContributionsByUsernameAndMeetingFullname(String username, String meetingFullname);
}
