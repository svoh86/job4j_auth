package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final BCryptPasswordEncoder encoder;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    @Transactional
    public boolean update(Person person) {
        boolean flag = false;
        if (personRepository.findById(person.getId()).isPresent()) {
            person.setPassword(encoder.encode(person.getPassword()));
            personRepository.save(person);
            flag = true;
        }
        return flag;
    }

    @Transactional
    public boolean delete(Person person) {
        boolean flag = false;
        if (personRepository.findById(person.getId()).isPresent()) {
            personRepository.delete(person);
            flag = true;
        }
        return flag;
    }
}
