package by.akimova.ManagementOfAdvertisingCompanies.exception;

/**
 * This is exception class for catching not existing entity.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public class EntityNotExistException extends Exception{
    public EntityNotExistException(String message) {
        super(message);
    }
}
