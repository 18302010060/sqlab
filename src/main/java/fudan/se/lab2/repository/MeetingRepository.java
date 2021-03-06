package fudan.se.lab2.repository;


import fudan.se.lab2.domain.Meeting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//18302010060 黄怡清'part
@Repository
public interface MeetingRepository extends CrudRepository<Meeting, Long>{
    /*Meeting findByFullname(String fullname);
    Meeting findById(int id);
    Meeting findByState(String state);
    Meeting findAllByStateEqualsAndStateEquals(String state, String state2);
    Meeting findAllByChairEquals(String chair);
    Meeting findAllByChairEqualsAndStateEquals(String chair,String state);*/
    List<Meeting> findAllByStateEqualsOrStateEquals(String state,String state2);
    List<Meeting> findAllByChairEqualsAndStateEquals(String chair,String state);
    Meeting findByFullname(String fullname);
    List<Meeting> findAllByFullname(String fullname);
    List<Meeting> findAllByStateEquals(String state);
    Meeting findByShortname(String shortname);
    Meeting findMeetingByFullnameAndChair(String fullname,String chair);


}
