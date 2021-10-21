package by.akimova.ManagementOfAdvertisingCompanies.exception;

/**
 * This is exception class for catching users who use existing mail in the database.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public class NotFreeUsernameException extends Exception {
    public NotFreeUsernameException(String message) {
        super(message);
    }
}
