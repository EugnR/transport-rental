package ru.transport.rent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;
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
    public ResponseEntity<?> registerTransport(@RequestBody RequestRegisterTransportDTO registerTransportDTO) {
        transportServiceImpl.registerTransport(registerTransportDTO);
        return ResponseEntity.ok().build();
    }
}
