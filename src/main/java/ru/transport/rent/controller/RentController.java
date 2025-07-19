package ru.transport.rent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.transport.rent.dto.rent.RequestTransportAroundDTO;
import ru.transport.rent.service.rent.RentService;

/**
 * Контроллер для взаимодействий с арендами.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Rent")
public class RentController {

    private final RentService rentService;

    /**
     * Эндпоинт для обработки запроса на поиск транспорта в радиусе.
     */
    @GetMapping("/Transport")
    ResponseEntity<?> getTransportAround(@RequestBody RequestTransportAroundDTO request) {
        return ResponseEntity.ok().body(
                rentService.findAvaliableTransport(request.getLatitude(), request.getLongitude(), request.getRadius(), request.getType()));
    }


}
