package ru.job4j.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Класс DTO для обновления пароля.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password cannot be less than 6 characters")
    private String password;
}
