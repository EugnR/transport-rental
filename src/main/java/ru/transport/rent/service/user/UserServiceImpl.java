package ru.transport.rent.service.user;

import java.security.Principal;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.transport.rent.dto.user.RequestRegistrationUserDTO;
import ru.transport.rent.dto.user.RequestSignInUserDTO;
import ru.transport.rent.dto.user.RequestUpdateUserDTO;
import ru.transport.rent.dto.user.RequestUserDetailsDTO;
import ru.transport.rent.entity.User;
import ru.transport.rent.mapper.user.UserMapper;
import ru.transport.rent.repository.RoleRepository;
import ru.transport.rent.repository.UserRepository;
import ru.transport.rent.security.JwtService;
import ru.transport.rent.security.UserDetailsImpl;


/**
 * Реализация для сервиса.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private static final long ROLE_ID_USER = 2L;


    @Value("${jwt.tokenExpiresIn}")
    private int tokenExpiresIn;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    /**
     * Метод для регистрации пользователя.
     */
    @Override
    @Transactional
    public void registerUser(final RequestRegistrationUserDTO requestRegistrationUserDTO) {
        final User user = userMapper.mapRegistrationDtoToUser(requestRegistrationUserDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findById(ROLE_ID_USER)
                .orElseThrow(EntityNotFoundException::new));
        user.setBalance(0.0);
        userRepository.save(user);
    }


    /**
     * Метод для получения JWT токена.
     */
    @Override
    @Transactional(readOnly = true)
    public String signInUser(final RequestSignInUserDTO requestSignInUserDTO) {
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                requestSignInUserDTO.getUserName(),
                requestSignInUserDTO.getPassword()
        );

        final Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new EntityNotFoundException(e.getMessage(), e);
        }

        final User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();

        return jwtService.generateToken(user.getUserName(), user.getRole()
                .getName(), tokenExpiresIn);
    }


    /**
     * Метод для возвращения информации об аккаунте.
     */
    @Override
    public RequestUserDetailsDTO getUserDetails(final Principal principal) {
        final String username = principal.getName();
        final User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.mapUserToUserDetailsDto(user);
    }

    /**
     * Метод для обновления информации об аккаунте.
     *
     * @param requestUpdateUserDTO строки новых логина и пароля
     * @param principal сам пользователь
     */
    @Override
    public void updateUserDetails(final RequestUpdateUserDTO requestUpdateUserDTO, final Principal principal) {
        final String username = principal.getName();

        final User currentUser = userRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException("User to change not found"));
        final User newUserData = userMapper.mapUpdateUserDtoToUser(requestUpdateUserDTO);

        currentUser.setUserName(newUserData.getUserName());
        currentUser.setPassword(passwordEncoder.encode(newUserData.getPassword()));
        userRepository.save(currentUser);

    }

}
