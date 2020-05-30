package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Discussion;
import fudan.se.lab2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LBW
 */
@Repository
public interface DiscussionRepository extends CrudRepository<Discussion, Long> {
    Discussion findDiscussionByContributionId(long contributionId);
    List<Discussion> findAllByContributionId(long contributionId);


}
