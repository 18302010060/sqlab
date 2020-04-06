package fudan.se.lab2.repository;


import fudan.se.lab2.domain.Invitations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends CrudRepository<Invitations, Long> {
    Invitations findByUsernameAndAndFullname(String username,String fullname);
}
