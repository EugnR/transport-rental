package ru.transport.rent.service.transport;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.transport.rent.config.TransportTypesConfig;
import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;
import ru.transport.rent.dto.transport.RequestTransportDetailsDTO;
import ru.transport.rent.dto.transport.RequestUpdateTransportDTO;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;
import ru.transport.rent.exceptions.InvalidTransportTypeException;
import ru.transport.rent.exceptions.OwnerMismatchException;
import ru.transport.rent.mapper.transport.TransportMapper;
import ru.transport.rent.repository.TransportRepository;
import ru.transport.rent.security.AuthenticationService;
import ru.transport.rent.utils.TransportUtils;

/**
 * Реализация интерфейса TransportService для обслуживания TransportController.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;
    private final TransportMapper transportMapper;
    private final TransportTypesConfig transportTypesConfig;


    /**
     * Метод для регистрации нового транспорта.
     */
    @Override
    @Transactional
    public void registerTransport(final RequestRegisterTransportDTO registerTransportDTO) {

        final String transportType = registerTransportDTO.getTransportType();

        if (!transportTypesConfig.getValidTypesAsSet().contains(
                TransportUtils.normalizeTransportType(transportType))) {
            throw new InvalidTransportTypeException("Invalid transport type: " + transportType);
        }

        final User owner = AuthenticationService.getUserFromSecurityContext();
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
    public void updateTransportDetails(final Long id, final RequestUpdateTransportDTO updateTransportDTO) {
        final Transport currentTransport = transportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transport to update is not found"));
        transportMapper.mapUpdateTransportDtoToTransport(updateTransportDTO, currentTransport);
        transportRepository.save(currentTransport);
    }

    /**
     * Метод для удаления транспорта по id.
     */
    @Override
    public void deleteTransport(final Long id) {
        final User user = AuthenticationService.getUserFromSecurityContext();

        final Transport transport = transportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transport to delete is not found"));

        if (transport.getOwner().equals(user)) {
            transportRepository.delete(transport);
        } else {
            throw new OwnerMismatchException("Not the transport's owner");
        }
    }


}
