package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    @Override
    public List<RestaurantResponse> getAll() {
        List<Restaurant> list = repository.findAll();
        List<RestaurantResponse> responseList = new ArrayList<>();
        for (Restaurant restaurant1: list) {
            RestaurantResponse restaurant = new RestaurantResponse(
                    restaurant1.getId(),
                    restaurant1.getName(),
                    restaurant1.getLocation(),
                    restaurant1.getRestType(),
                    restaurant1.getNumberOfEmployees(),
                    restaurant1.getService(),
                    menuItemRepository.getAll(restaurant1.getId())
            );
            responseList.add(restaurant);
        }
        return responseList;
    }

    @Override
    public SimpleResponse save(RestaurantRequest request) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if(authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                Restaurant restaurant = new Restaurant();
                setRestaurant(request, restaurant);
                User user = userRepository.findByEmail(userDetails.getUsername())
                        .orElseThrow(() -> new NotFoundException("User with userName: " + userDetails.getUsername() + " not found!"));
                restaurant.setNumberOfEmployees(restaurant.getNumberOfEmployees() + 1);
                repository.save(restaurant);
                user.setRestaurant(restaurant);
                return new SimpleResponse("SAVE", "Restaurant successfully saved!");
            }
        }
        return new SimpleResponse("FAIL", "Access Denied!");
    }

    private void setRestaurant(RestaurantRequest request, Restaurant restaurant) {
        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setRestType(request.restType());
        restaurant.setNumberOfEmployees(0);
        restaurant.setService(request.service());
        restaurant.setMenuItems(new ArrayList<>());
        restaurant.setEmployees(new ArrayList<>());
    }

    @Override
    public RestaurantResponse finById(Long restaurantId) {
        Restaurant restaurant = repository.findById(restaurantId)
                .orElseThrow(() ->
                        new NotFoundException("Restaurant with id: " + restaurantId + " not found!"));
        return new RestaurantResponse(restaurant.getId(),
                restaurant.getName(), restaurant.getLocation(),
                restaurant.getRestType(), restaurant.getNumberOfEmployees(),
                restaurant.getService(), menuItemRepository.getAll(restaurantId)
        );
    }

    @Override
    public SimpleResponse update(Long restaurantId, RestaurantRequest request) {
        Restaurant restaurant = repository.findById(restaurantId)
                .orElseThrow(() ->
                        new NotFoundException("Restaurant with id: " + restaurantId + " not found!"));
        if(repository.existsById(restaurantId)){
            setRestaurant(request, restaurant);
            return new SimpleResponse("UPDATE", "Restaurant with id: "+restaurantId+" successfully updated!");
        }
        return new SimpleResponse("FAIL", "Restaurant with id: "+restaurantId+" failed to update!");
    }

    @Override
    public SimpleResponse delete(Long restaurantId) {
        if(repository.existsById(restaurantId)){
            Restaurant restaurant = repository.findById(restaurantId)
                    .orElseThrow(()-> new NotFoundException("Restaurant with id: "+ restaurantId +" not found!"));
            List<User> employees = new ArrayList<>();
            for (User employee : restaurant.getEmployees()) {
                if(!employee.getRole().equals(Role.ADMIN)){
                    employees.add(employee);
                }
                employee.setRestaurant(null);
            }
            restaurant.setEmployees(employees);
            repository.delete(restaurant);
            return new SimpleResponse("DELETE", "Restaurant with id: "+restaurantId+" successfully deleted!");
        }
        return new SimpleResponse("FAIL", "Restaurant with id: "+restaurantId+" failed to delete!");
    }
}
