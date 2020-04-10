package fudan.se.lab2.repository;

import fudan.se.lab2.domain.MeetingAuthority;
import fudan.se.lab2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LBW
 */
@Repository
public interface MeetingAuthorityRepository extends CrudRepository<MeetingAuthority, Long> {
   /* MeetingAuthority findByUsernameAndAndFullname(String username,String fullname);
    List<MeetingAuthority> findAllByUsernameEqualsAndAuthorityEquals(String username, String authority);
    MeetingAuthority findAllByFullnameEqualsAndAuthorityEquals(String fullname,String authority);*/
    List<MeetingAuthority> findAllByUsernameEqualsAndAuthorityEquals(String username, String authority);
}
