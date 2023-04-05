package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public boolean update(Person person) {
        boolean flag;
        try {
            personRepository.save(person);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public boolean delete(Person person) {
        boolean flag;
        try {
            personRepository.delete(person);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
