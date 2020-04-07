package fudan.se.lab2.repository;

import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LBW
 */
@Repository
public interface MeetingAuthorityRepository extends CrudRepository<MeetingAuthority, Long> {
    MeetingAuthority findByUsernameAndAndFullname(String username,String fullname);
}
