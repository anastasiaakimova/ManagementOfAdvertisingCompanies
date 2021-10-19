package by.akimova.ManagementOfAdvertisingCompanies.service.impl;

import by.akimova.ManagementOfAdvertisingCompanies.exception.NotFreeUsernameException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.NotValidUsernameException;
import by.akimova.ManagementOfAdvertisingCompanies.model.User;
import by.akimova.ManagementOfAdvertisingCompanies.repository.UserRepository;
import by.akimova.ManagementOfAdvertisingCompanies.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The class is implementation of user's business logic.
 * The class is implementation of  {@link UserService} interface.
 * Wrapper for {@link UserRepository} + business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * The method add new user.
     *
     * @param user This is user with information about it, and it's fields
     * @return Saved user.
     */
    @Override
    public User saveUser(User user) throws NotFreeUsernameException {
        Optional mailUser = userRepository.findByMail(user.getMail());

        if (mailUser.isPresent()) {
            throw new NotFreeUsernameException("This username is already taken");
        }

        user.setId(UUID.randomUUID());
        user.setPassword(user.getPassword());
        user.setIsActive(Boolean.TRUE);
        log.info("IN saveUser - new user with id: {} successfully added", user.getId());
        return userRepository.save(user);
    }

    /**
     * The method get user by id with all information about it.
     *
     * @param usrId This is user's id.
     * @return found user.
     */
    @Override
    public User getById(UUID usrId) throws EntityNotFoundException, NotValidUsernameException {

        if (usrId == null) {
            log.error("IN getById - userId is null ");
            throw new NotValidUsernameException("userId is null");
        }

        User user = userRepository.findUserById(usrId);

        if (user == null) {
            log.error("IN getById -  no user found by id {}", usrId);
            throw new EntityNotFoundException("user not found");
        }

        log.info("IN getById - user: {} found by id: {}", user, usrId);
        return user;
    }

    /**
     * The method get all users with all information about it.
     *
     * @return list of active users.
     */
    @Override
    public List<User> getAllUsers(Optional<Integer> page,
                                  Optional<Integer> size,
                                  Optional<String> sortBy) {

        List<User> users = userRepository.findAll(PageRequest.of(page.orElse(0),
                        size.orElse(userRepository.findAll().size()),
                        Sort.Direction.ASC, sortBy.orElse("id")))
                .stream()
                .filter((User::getIsActive))
                .collect(Collectors.toList());

        log.info("IN getAllUsers - {} users found", users.size());
        return users;
    }

    /**
     * This method update user.
     *
     * @param usrId This is user's id which needed to update.
     * @param user  This is updated user.
     * @return Updated user.
     */
    @Override
    public User updateUser(UUID usrId, User user) throws EntityNotFoundException, NotValidUsernameException {

        if (user == null) {
            log.error("IN updateUser - user is null");
            throw new NotValidUsernameException("user is null");
        }

        User dbUser = userRepository.findUserById(usrId);

        if (dbUser == null) {
            log.error("IN updateUser - user not found by id {}", usrId);
            throw new EntityNotFoundException("user not found");
        }

        dbUser.setName(user.getName());
        dbUser.setMail(user.getMail());
        dbUser.setPassword(user.getPassword());
        dbUser.setRole(user.getRole());

        log.info("IN updateUser - user with id: {} successfully edited ", usrId);
        return userRepository.save(dbUser);
    }

    /**
     * This method delete user.
     * The parameter "isActive" turn false and this means that user deleted.
     *
     * @param usrId This is user's id which needed to delete.
     */
    @Override
    public void deleteUserById(UUID usrId) {
       User user = userRepository.findUserById(usrId);
       user.setIsActive(Boolean.FALSE);
       userRepository.save(user);

        log.info("IN deleteUserById - user with id: {} successfully deleted", usrId);
    }

    /**
     * The method find user by mail and show all information about it.
     *
     * @param mail This is user's mail.
     * @return found user.
     */
    @Override
    public Optional<User> getByMail(String mail) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByMail(mail);

        if (!user.isPresent()) {
            log.error("IN findByMail - user not found by mail: {}", mail);
            throw new EntityNotFoundException("User doesn't exists");
        }

        log.info("IN findByMail - user found by mail: {}", mail);
        return user;
    }
}
