package ru.transport.rent.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Обработчик исключений для контроллеров.
 */
@Slf4j
@RestControllerAdvice
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class CustomExceptionHandler {

    /**
     * Обработчик OwnerMismatchException, для случаев когда пользователь пытается удалить транспорт, хозяином которого не является.
     */
    @ExceptionHandler(OwnerMismatchException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleOwnerMismatchException(final OwnerMismatchException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage());
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * Обработчик EntityNotFoundException, для случаев когда объект в репозитории найден не был.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEntityNotFoundException(final EntityNotFoundException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage());
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Обработчик InvalidTransportTypeException, если пришёл json с неправильным типом транспорта.
     */
    @ExceptionHandler(InvalidTransportTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidTransportTypeException(final InvalidTransportTypeException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage());
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработчик UsernameNotFoundException.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUsernameNotFoundException(final UsernameNotFoundException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage());
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
