package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Discussion;
import fudan.se.lab2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author LBW
 */
@Repository
public interface DiscussionRepository extends CrudRepository<Discussion, Long> {
    Discussion findDiscussionByContributionId(Long contributionId);
    List<Discussion> findAllByContributionId(Long contributionId);
    List<Discussion> findAllByMeetingFullnameAndAndDiscussionState(String meetingFullname,String discussionState);
    List<Discussion> findAllByMeetingFullname(String meetingFullname);
    List<Discussion> findAllBySubcomment(String subcomment);
    List<Discussion> findAllByContributionIdAndUsernameAndTime(Long contributionId, String username, String time);



}
