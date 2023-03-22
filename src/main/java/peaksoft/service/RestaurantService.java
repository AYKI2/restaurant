package peaksoft.service;

import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface RestaurantService {
    List<RestaurantResponse> getAll();
    SimpleResponse save(RestaurantRequest request);
    RestaurantResponse finById(Long restaurantId);
    SimpleResponse update(Long restaurantId,RestaurantRequest request);
    SimpleResponse delete(Long restaurantId);
}
