package peaksoft.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.config.jwt.JwtUtil;
import peaksoft.dto.request.AcceptRequest;
import peaksoft.dto.request.LoginRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserNotAcceptResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.AlreadyExistsException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RestaurantRepository restaurantRepository;


    @PostConstruct
    void initMethod(){
        try {
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("gadgetarium.json").getInputStream());
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build();
            log.info("successfully works the init method");
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        } catch (IOException e) {
            log.error("IOException");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(
                "Iskhak",
                "Abdukhamitov",
                LocalDate.of(2002,8,28),
                "admin@gmail.com",
                passwordEncoder.encode("admin123"),
                "+996507434242",
                Role.ADMIN,
                3);
        if(!repository.existsByEmail(user.getEmail())){
            repository.save(user);
        }
    }

    @Override
    public List<UserResponse> getAll(Long restaurantId) {
        return repository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public UserResponse finById(Long restaurantId, Long userId) {
        return repository.findUserById(restaurantId,userId).orElseThrow(()->
                new NotFoundException("User with id: "+userId+" not found!"));
    }

    @Override
    public SimpleResponse update(Long restaurantId, Long userId, UserRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                new NotFoundException("Restaurant with id:" + restaurantId + " not found!"));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if(authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            for (User user : restaurant.getEmployees()) {
                if (user.getId().equals(userId) && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")) || user.getId().equals(userId) && userDetails.getUsername().equals(user.getEmail())) {
                    user.setFirstName(request.firstName());
                    user.setLastName(request.lastName());
                    user.setDateOfBirth(request.dateOfBirth());
                    user.setEmail(request.email());
                    user.setPhoneNumber(request.phoneNumber());
                    user.setExperience(request.experience());
                    return new SimpleResponse("UPDATE", "Employee with id: " + user.getId() + " successfully updated!");
                }
            }
        }
        return new SimpleResponse("FAIL", "You cannot change another user's details!");
    }

    @Override
    public SimpleResponse delete(Long restaurantId, Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("Restaurant with id:" + userId + " not found!"));
        if (user.getId().equals(userId)){
                repository.deleteById(userId);
                return new SimpleResponse("DELETE","Employee with id: "+user.getId()+" successfully deleted!");
        }
        return new SimpleResponse("FAIL","Employee with id: "+userId+" failed to delete!");
    }

    @Override
    public List<UserNotAcceptResponse> getAllByAcceptIsFalse() {
        return repository.getAllByAcceptIsFalse();
    }

    @Override
    public SimpleResponse assign(AcceptRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.restaurantId()).orElseThrow(() ->
                new NotFoundException("Restaurant with id: " + request.restaurantId() + " not found!"));
        User user = repository.findById(request.userId())
                    .orElseThrow(()->
                            new NotFoundException("User with id: " + request.userId() + " not found!"));
        if(restaurant.getNumberOfEmployees() < 16) {
            if (request.isAccept()) {
                user.setRestaurant(restaurant);
                restaurant.setNumberOfEmployees(restaurant.getNumberOfEmployees() + 1);
                user.setAccept(true);
                return new SimpleResponse("ACCEPT", "Employee with id: " + request.userId() + " successfully assigned!");
            } else {
                repository.deleteById(request.userId());
                return new SimpleResponse("REJECT", "Employee with id: " + request.userId() + " successfully deleted!");
            }
        }else {
            return new SimpleResponse("FULL", "Restaurant employees are full (15) . Sorry!");
        }
    }

    @Override
    public AuthenticationResponse register(UserRequest request, Long restaurantId) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(repository.existsByEmail(request.email())){
            throw new AlreadyExistsException(String.format("User with email: %s already exists!", request.email()));
        }
        List<UserResponse> users = repository.getAllUsers(restaurantId);
        if (users.size() > 15){
            throw new BadRequestException("Restaurant employees are full (15) . Sorry!");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setDateOfBirth(request.dateOfBirth());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber());
        user.setRole(request.role());
        user.setExperience(request.experience());
        user.setAccept(false);

        if (!user.getPhoneNumber().startsWith("+996")) {
            throw new BadRequestException("Phone number must start with +996");
        }
        int age = LocalDate.now().minusYears(user.getDateOfBirth().getYear()).getYear();

        if (user.getRole() == Role.CHEF) {
            if (age < 25 || age > 45 || user.getExperience() < 2) {
                throw new BadRequestException("Chef's age should be between 25-45 and experience >= 2");
            }
        } else if (age < 18 || age > 30 || user.getExperience() < 1) {
            throw new BadRequestException("Waiter's age should be between 18-30 and experience >= 1");
        }
        repository.save(user);

        String token = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .username(user.getEmail())
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = repository.findByEmail(request.email())
                .orElseThrow(()->
                        new NotFoundException(String.format("User with email: %s doesn't exists!", request.email())));

        String token = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .username(user.getEmail())
                .token(token)
                .build();
    }
}
