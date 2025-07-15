package ru.transport.rent.service.transport;

import java.util.NoSuchElementException;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;
import ru.transport.rent.dto.transport.RequestTransportDetailsDTO;
import ru.transport.rent.dto.transport.RequestUpdateTransportDTO;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;
import ru.transport.rent.exceptions.InvalidTransportTypeException;
import ru.transport.rent.mapper.transport.TransportMapper;
import ru.transport.rent.repository.TransportRepository;
import ru.transport.rent.security.UserDetailsImpl;

/**
 * Реализация интерфейса TransportService для обслуживания TransportController.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransportServiceImpl implements TransportService {

    private static final Set<String> VALID_TRANSPORT_TYPES = Set.of("Car", "Bike", "Scooter");

    private final TransportRepository transportRepository;
    private final TransportMapper transportMapper;


    /**
     * Метод для регистрации нового транспорта.
     */
    @Override
    @Transactional
    public void registerTransport(final RequestRegisterTransportDTO registerTransportDTO) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final User owner = userDetails.getUser();
        final String transportType = registerTransportDTO.getTransportType();

        if (!VALID_TRANSPORT_TYPES.contains(transportType)) {
            throw new InvalidTransportTypeException("Invalid transport type: " + transportType);
        }

        final Transport newTransport = transportMapper.mapRegisterTransportDtoToTransport(registerTransportDTO);
        newTransport.setOwner(owner);

        transportRepository.save(newTransport);
    }

    @Override
    public RequestTransportDetailsDTO getTransportDetails(final Long id) {
        return transportMapper.mapTransportToTransportDetailsDto(
                transportRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("Transport not found")));
    }

    /**
     * Метод для смены информации о транспорте по id.
     */
    @Override
    public void updateTransportDetails(Long id, RequestUpdateTransportDTO UpdateTransportDTO) {
        final String transportType = UpdateTransportDTO.getTransportType();

        if (!VALID_TRANSPORT_TYPES.contains(transportType)) {
            throw new InvalidTransportTypeException("Invalid transport type: " + transportType);
        }

        Transport currentTransport = transportRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transport to update is not found"));
        transportMapper.mapUpdateTransportDtoToTransport(UpdateTransportDTO, currentTransport);
        transportRepository.save(currentTransport);


    }
}
