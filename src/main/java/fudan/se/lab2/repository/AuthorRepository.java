package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Author;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author,Long> {
    List<Author> findAllById(Long id);
    void deleteAllById(Long id);
    Author findByIdAndUsername(Long id,String username);
    Author findByIdAndUsernameAndEmail(Long id,String username,String email);
    List<Author> findAllByEmail(String email);
}
