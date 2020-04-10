package fudan.se.lab2.repository;


import fudan.se.lab2.domain.Invitations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface InvitationRepository extends CrudRepository<Invitations, Long> {
   Invitations findByUsername(String username);//通过用户姓名查找
   Invitations findByUsernameAndFullname(String username,String fullname);//通过用户姓名和会议全称查找
   List<Invitations> findAllByUsernameEqualsAndInviteStateEquals(String username, String inviteState);
}
