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
//    @GetMapping("/Transport")
//    public ResponseEntity<?> getTransportAround(@RequestBody final RequestTransportAroundDTO request) {
//        rentService.findAvailableTransport(request.getLatitude(), request.getLongitude(), request.getRadius(), request.getType());
//        return ResponseEntity.ok().build();
//    }
    @GetMapping("/Transport")
    public ResponseEntity<?> getTransportAround(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam Double radius,
            @RequestParam String type) {
        ;
        return ResponseEntity.ok().body(rentService.findAvailableTransport(lat, lon, radius, type));
    }



}
