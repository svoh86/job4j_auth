package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер описывает CRUD операции и построен по схеме Rest архитектуры
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {
    private final PersonRepository personRepository;

    /**
     * GET/person/
     *
     * @return список всех пользователей
     */
    @GetMapping("/")
    public List<Person> findAll() {
        return (List<Person>) personRepository.findAll();
    }

    /**
     * GET/person/{id}
     *
     * @param id id
     * @return пользователь с указанным id и статус
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable("id") int id) {
        Optional<Person> person = personRepository.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * POST/person/ - создает пользователя.
     *
     * @param person В теле запроса отправляет данные
     * @return в ответ приходит созданный пользователь со сгенерированным ID и статус
     */
    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(
                personRepository.save(person),
                HttpStatus.CREATED
        );
    }

    /**
     * PUT/person/ - для обновления пользователя
     *
     * @param person В теле запроса отправляет данные
     * @return в ответе статус выполнения
     */
    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        personRepository.save(person);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE/person/{id} - удаляет пользователя
     *
     * @param id id пользователя
     * @return в ответе статус выполнения
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        Person person = new Person();
        person.setId(id);
        personRepository.delete(person);
        return ResponseEntity.ok().build();
    }
}
