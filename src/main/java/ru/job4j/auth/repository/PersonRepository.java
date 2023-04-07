package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Person;

import java.util.List;
import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findAll();

    Optional<Person> findByLogin(String login);
}
