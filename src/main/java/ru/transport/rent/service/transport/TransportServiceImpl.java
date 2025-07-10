package ru.transport.rent.service.transport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;
import ru.transport.rent.mapper.transport.TransportMapper;
import ru.transport.rent.repository.TransportRepository;
import ru.transport.rent.repository.UserRepository;
import ru.transport.rent.security.UserDetailsImpl;

/**
 * Реализация интерфейса TransportService для обслуживания TransportController.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;
    private final UserRepository userRepository;
    private final TransportMapper transportMapper;

    /**
     * Метод для регистрации нового транспорта.
     */
    @Override
    @Transactional
    public void registerTransport(RequestRegisterTransportDTO registerTransportDTO) throws UsernameNotFoundException{
        Transport newTransport = transportMapper.mapRegisterTransportDtoToTransport(registerTransportDTO);

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        final User owner = userRepository.findByUserName(userDetails.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("User that meant to be an owner of a new car is not found"));

        newTransport.setOwner(owner);

        transportRepository.save(newTransport);
    }
}
