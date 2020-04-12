package fudan.se.lab2.repository;

import fudan.se.lab2.domain.MeetingAuthority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LBW
 */
@Repository
public interface MeetingAuthorityRepository extends CrudRepository<MeetingAuthority, Long> {

    List<MeetingAuthority> findAllByUsernameEqualsAndAuthorityEquals(String username, String authority);
    MeetingAuthority findByUsername(String username);
    MeetingAuthority findByUsernameAndFullname(String username, String fullname);



    List<MeetingAuthority> findAllByUsernameAndAuthority(String username,String authority);
}

