package ru.transport.rent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;
import ru.transport.rent.exceptions.InvalidTransportTypeException;
import ru.transport.rent.service.transport.TransportServiceImpl;

/**
 * Контроллер для запросов на действия с транспортом.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Transport")
public class TransportController {

    private final TransportServiceImpl transportServiceImpl;

    /**
     * Метод для регистрации нового транспорта.
     */
    @PostMapping
    public ResponseEntity<?> registerTransport(@RequestBody final RequestRegisterTransportDTO registerTransportDTO) {
        try {
            transportServiceImpl.registerTransport(registerTransportDTO);
            return ResponseEntity.ok().build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (InvalidTransportTypeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
