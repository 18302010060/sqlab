package fudan.se.lab2.repository;


import fudan.se.lab2.domain.Meeting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
//18302010060 黄怡清'part
@Repository
public interface MeetingRepository extends CrudRepository<Meeting, Long>{
    Meeting findByFullname(String fullname);
    Meeting findById(int id);
    Meeting findByState(String state);
}
