package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.UserNotAcceptResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    @Query("SELECT new peaksoft.dto.response.UserResponse(u.id,u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.role,u.experience,u.restaurant.name) FROM User u where u.restaurant.id = :restaurantId")
    List<UserResponse> getAllByRestaurantId(Long restaurantId);
    @Query("select new peaksoft.dto.response.UserNotAcceptResponse(u.id,u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.role,u.experience,u.isAccept) from User u where u.isAccept = false")
    List<UserNotAcceptResponse> getAllByAcceptIsFalse();
    @Query("SELECT new peaksoft.dto.response.UserResponse(u.id,u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.role,u.experience,u.restaurant.name) FROM User u where u.id = :userId and u.restaurant.id = :restaurantId")
    Optional<UserResponse> findUserById(Long restaurantId, Long userId);
    @Query("SELECT new peaksoft.dto.response.UserResponse(u.id,u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.role,u.experience,u.restaurant.name) FROM User u where u.restaurant.id = :restaurantId")
    List<UserResponse> getAllUsers(Long restaurantId);
}
