package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Distribution;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DistributionRespository extends CrudRepository<Distribution,Long> {
    List<Distribution> findAllByUsername(String username);
    List<Distribution> findAllByFullname(String fullname);
    List<Distribution> findAllByFullnameAndUsername(String fullname,String username);
    Distribution findDistinctById(Long id);
    List<Distribution> findAllByContributionId(Long id);
    List<Distribution> findAllByFullnameAndUsernameAndState(String fullname, String username, Boolean state);
    Distribution findDistributionById(Long id);
    Distribution findDistributionByContributionIdAndUsername(Long contributionId,String username);
    List<Distribution> findAllByContributionIdAndState(Long contributionID,Boolean state);


}
