package peaksoft.service;

import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface MenuItemService{
    List<MenuItemResponse> getAll(Long restaurantId);
    SimpleResponse save(Long restaurantId, MenuItemRequest request);
    MenuItemResponse finById(Long restaurantId,Long menItemId);
    SimpleResponse update(Long restaurantId,Long menItemId, MenuItemRequest request);
    List<MenuItemResponse> globalSearch(Long restaurantId, String word);
    List<MenuItemResponse> filterIsVegetarian(Boolean isVegetarian);
    SimpleResponse delete(Long restaurantId,Long menItemId);
}
