package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;
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
                    restaurant1.getMenuItems()
            );
            responseList.add(restaurant);
        }
        return responseList;
    }

    @Override
    public SimpleResponse save(RestaurantRequest request) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setRestType(request.restType());
        restaurant.setNumberOfEmployees(0);
        restaurant.setService(request.service());
        restaurant.setMenuItems(new ArrayList<>());
        restaurant.setEmployees(new ArrayList<>());
        repository.save(restaurant);
        return new SimpleResponse("SAVE","Restaurant successfully saved!");
    }

    @Override
    public RestaurantResponse finById(Long restaurantId) {
        Restaurant restaurant = repository.findById(restaurantId)
                .orElseThrow(() ->
                        new NoSuchElementException("Restaurant with id: " + restaurantId + " not found!"));
        return new RestaurantResponse(restaurant.getId(),
                restaurant.getName(), restaurant.getLocation(), restaurant.getRestType(),
                restaurant.getNumberOfEmployees(), restaurant.getService(),restaurant.getMenuItems()
        );
    }

    @Override
    public SimpleResponse update(Long restaurantId, RestaurantRequest request) {
        Restaurant restaurant = repository.findById(restaurantId)
                .orElseThrow(() ->
                        new NoSuchElementException("Restaurant with id: " + restaurantId + " not found!"));
        if(repository.existsById(restaurantId)){
            restaurant.setName(request.name());
            restaurant.setLocation(request.location());
            restaurant.setRestType(request.restType());
            restaurant.setNumberOfEmployees(0);
            restaurant.setService(request.service());
            restaurant.setMenuItems(new ArrayList<>());
            restaurant.setEmployees(new ArrayList<>());
            return new SimpleResponse("UPDATE", "Restaurant with id: "+restaurantId+" successfully updated!");
        }
        return new SimpleResponse("FAIL", "Restaurant with id: "+restaurantId+" failed to update!");
    }

    @Override
    public SimpleResponse delete(Long restaurantId) {
        if(repository.existsById(restaurantId)){
            repository.deleteById(restaurantId);
            return new SimpleResponse("DELETE", "Restaurant with id: "+restaurantId+" successfully deleted!");
        }
        return new SimpleResponse("FAIL", "Restaurant with id: "+restaurantId+" failed to delete!");
    }
}
