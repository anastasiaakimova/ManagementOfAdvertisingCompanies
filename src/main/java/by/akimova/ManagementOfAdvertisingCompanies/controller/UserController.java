package by.akimova.ManagementOfAdvertisingCompanies.controller;

import by.akimova.ManagementOfAdvertisingCompanies.exception.NotFreeUsernameException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.NotValidUsernameException;
import by.akimova.ManagementOfAdvertisingCompanies.model.User;
import by.akimova.ManagementOfAdvertisingCompanies.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

/**
 * REST controller user connected requests.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * The method get all users.
     *
     * @return ResponseEntity with list of users and status ok.
     */
    @GetMapping
    ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * The method shows user by id.
     *
     * @param id This is user's id which should be viewed.
     * @return ResponseEntity with body of user and status ok.
     */

    @GetMapping("/{id}")
    ResponseEntity<?> getUserById(@PathVariable(value = "id") UUID id) {
        User user;
        try {
            user = userService.getById(id);
        } catch (NotValidUsernameException e) {
            log.error("IN UserController getUserById - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.error("IN UserController getUserById - user by id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * The method add new user.
     *
     * @param user This is user with its information and body.
     * @return response with body of created user and status ok.
     */
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User savedUser;
        try {
            savedUser = userService.saveUser(user);
        } catch (NotFreeUsernameException e) {
            return new ResponseEntity<>("This username is already taken ", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * The method shows user by mail.
     *
     * @param mail This is mail of the person to be found.
     * @return ResponseEntity with found user and status ok.
     */
    @GetMapping("mail/{mail}")
    ResponseEntity<?> getUserByMail(@PathVariable(value = "mail") String mail) throws EntityNotFoundException {
        User user;
        try {
            user = userService.getByMail(mail).orElseThrow(
                    () -> new EntityNotFoundException("User doesn't exists!"));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(user);
    }

    /**
     * The method update user.
     *
     * @param id   This is user's id which should be updated.
     * @param user This is new body for user which should be updated.
     * @return response with body of updated user and status ok.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") UUID id, @RequestBody User user) {
        User updatedUser;
        try {
            updatedUser = userService.updateUser(id, user);
        } catch (NotValidUsernameException e) {
            log.error("IN UserController updateUser - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.error("IN UserController updateUser - user by id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * The method delete user.
     *
     * @param id This is user's id which should be deleted.
     * @return response status no_content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}