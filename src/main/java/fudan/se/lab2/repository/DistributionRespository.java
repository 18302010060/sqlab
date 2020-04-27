package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Distribution;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DistributionRespository extends CrudRepository<Distribution,Long> {
    List<Distribution> findAllByUsername(String username);
    List<Distribution> findAllByFullname(String fullname);
    Distribution findDistinctById(Long id);
}
