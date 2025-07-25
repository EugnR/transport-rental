package ru.transport.rent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;
import ru.transport.rent.dto.transport.RequestUpdateTransportDTO;
import ru.transport.rent.service.transport.TransportService;

/**
 * Контроллер для запросов на действия с транспортом.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Transport")
public class TransportController {

    private final TransportService transportService;

    /**
     * Метод для регистрации нового транспорта.
     */
    @PostMapping
    public ResponseEntity<?> registerTransport(@RequestBody final RequestRegisterTransportDTO registerTransportDTO) {
        transportService.registerTransport(registerTransportDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод для получения информации о транспорте по id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransportDetails(@PathVariable final Long id) {
        return ResponseEntity.ok().body(transportService.getTransportDetails(id));
    }

    /**
     * Метод для изменения информации о транспорте по id.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransport(@PathVariable final Long id, @RequestBody final RequestUpdateTransportDTO updateTransportDTO) {
        transportService.updateTransportDetails(id, updateTransportDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод для удаления транспорта по id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransport(@PathVariable final Long id) {
        transportService.deleteTransport(id);
        return ResponseEntity.ok().build();
    }
}
