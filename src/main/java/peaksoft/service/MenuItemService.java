package peaksoft.service;

import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.*;

import java.util.List;

public interface MenuItemService{
    List<MenuItemResponse> getAll(Long restaurantId,String ascOrDesc);
    SimpleResponse save(Long restaurantId, MenuItemRequest request);
    MenuItemResponse finById(Long restaurantId,Long menItemId);
    SimpleResponse update(Long restaurantId,Long menItemId, MenuItemRequest request);
    List<? extends GlobalSearchResponse> globalSearch(Long restaurantId, String word);
    List<MenuItemResponse> filterIsVegetarian(Boolean isVegetarian);
    SimpleResponse delete(Long restaurantId,Long menItemId);
    PaginationResponse getPagination(int page, int max);
}
