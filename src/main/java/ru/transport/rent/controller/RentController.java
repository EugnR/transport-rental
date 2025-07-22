package ru.transport.rent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<?> getTransportAround(
            @RequestParam final Double lat,
            @RequestParam final Double lon,
            @RequestParam final Double radius,
            @RequestParam final String type) {
        return ResponseEntity.ok().body(rentService.findAvailableTransport(lat, lon, radius, type));
    }



}
