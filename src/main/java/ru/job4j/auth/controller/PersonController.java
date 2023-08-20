package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.dto.PersonDTO;
import ru.job4j.auth.handlers.GlobalExceptionHandler;
import ru.job4j.auth.service.PersonService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
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
    private final PersonService personService;
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class.getSimpleName());
    private final ObjectMapper objectMapper;

    /**
     * GET/person/
     *
     * @return список всех пользователей
     */
    @GetMapping("/")
    public List<Person> findAll() {
        return personService.findAll();
    }

    /**
     * GET/person/{id}
     *
     * @param id id
     * @return пользователь с указанным id и статус
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable("id") int id) {
        Optional<Person> person = personService.findById(id);
        if (person.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не найден!");
        }
        return new ResponseEntity<>(person.get(), HttpStatus.OK);
    }

    /**
     * POST/person/ - создает пользователя.
     *
     * @param person В теле запроса отправляет данные
     * @return в ответ приходит созданный пользователь со сгенерированным ID и статус
     */
    @PostMapping("/")
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        return new ResponseEntity<>(
                personService.save(person),
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
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        if (!personService.update(person)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не обновлен!");
        }
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
        if (!personService.delete(person)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не удален!");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody Person person) {
        personService.save(person);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void exceptionHandler(Exception e, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getMessage());
    }

    @PatchMapping("/{id}")
    public Person updatePassword(@Valid @RequestBody PersonDTO personDTO, @PathVariable("id") int id) {
        Person personDB = personService.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found!"));
        personDB.setPassword(personDTO.getPassword());
        personService.update(personDB);
        return personDB;
    }

    /**
     * Метод раздает pdf-файл
     *
     * @return pdf-файл
     * @throws IOException исключение
     */
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getPDF() throws IOException {
        byte[] content = Files.readAllBytes(Path.of("./book.pdf"));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(content.length)
                .body(content);
    }
}
