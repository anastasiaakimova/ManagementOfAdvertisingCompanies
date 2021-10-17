package by.akimova.ManagementOfAdvertisingCompanies.repository;

import by.akimova.ManagementOfAdvertisingCompanies.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for class {@link User}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    void deleteUserById(UUID usrId);

    Optional<User> findByMail(String mail);

    User findUserById(UUID usrId);
}
