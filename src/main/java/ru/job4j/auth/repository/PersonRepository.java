package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Person;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
