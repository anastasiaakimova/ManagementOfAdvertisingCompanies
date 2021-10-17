package by.akimova.ManagementOfAdvertisingCompanies.service;

import by.akimova.ManagementOfAdvertisingCompanies.exception.NotFreeUsernameException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.NotValidUsernameException;
import by.akimova.ManagementOfAdvertisingCompanies.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for class {@link User}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

public interface UserService {

    User saveUser(User user) throws NotFreeUsernameException;

    User getById(UUID usrId) throws EntityNotFoundException, NotValidUsernameException;

    List<User> getAllUsers();

    User updateUser(UUID usrId, User user) throws EntityNotFoundException, NotValidUsernameException;

    void deleteUserById(UUID usrId);

    Optional<User> getByMail(String mail) throws EntityNotFoundException;
}