package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.*;
import peaksoft.repository.*;
import peaksoft.service.MenuItemService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;
    private final SubCategoryRepository subCategoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final StopListRepository stopListRepository;
    private final ChequeRepository chequeRepository;

    @Override
    public List<MenuItemResponse> getAll(Long restaurantId) {
        return repository.getAll(restaurantId);
    }

    @Override
    public SimpleResponse save(Long restaurantId, MenuItemRequest request) {
        MenuItem menuItem = new MenuItem();
        SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId())
                .orElseThrow(() ->
                        new NoSuchElementException("SubCategory with id: " + request.subCategoryId() + " not found!"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new NoSuchElementException("Restaurant with id: " + restaurantId + " not found!"));
        menuItem.setName(request.name());
        menuItem.setImage(request.image());
        menuItem.setPrice(request.price());
        menuItem.setDescription(request.description());
        menuItem.setVegetarian(request.isVegetarian());
        menuItem.setSubCategory(subCategory);
        menuItem.setRestaurant(restaurant);
        repository.save(menuItem);
        return new SimpleResponse("SAVE", "MenuItem successfully saved!");
    }

    @Override
    public MenuItemResponse finById(Long restaurantId, Long menItemId) {
        return repository.getMenuItemById(restaurantId,menItemId)
                .orElseThrow(()->
                        new NoSuchElementException("MenuItem with id: "+ menItemId +" not found!"));
    }

    @Override
    public SimpleResponse update(Long restaurantId, Long menItemId, MenuItemRequest request) {
        MenuItem menuItem = repository.findById(menItemId)
                .orElseThrow(() ->
                        new NoSuchElementException("MenuItem with id: " + menItemId + " not found!"));
        SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId())
                .orElseThrow(() ->
                        new NoSuchElementException("SubCategory with id: " + request.subCategoryId() + " not found!"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new NoSuchElementException("Restaurant with id: " + restaurantId + " not found!"));
        if(repository.existsById(menItemId)) {
            menuItem.setName(request.name());
            menuItem.setImage(request.image());
            menuItem.setPrice(request.price());
            menuItem.setDescription(request.description());
            menuItem.setVegetarian(request.isVegetarian());
            menuItem.setSubCategory(subCategory);
            menuItem.setRestaurant(restaurant);
            return new SimpleResponse("UPDATE", "MenuItem successfully updated!");
        }
        return new SimpleResponse("FAIL", "MenuItem fail to update!");
    }
    @Override
    public List<MenuItemResponse> globalSearch(Long restaurantId,String word) {
        if (word == null) {
            return repository.getAll(restaurantId);
        }
        return repository.globalSearch(word);
    }
    @Override
    public List<MenuItemResponse> filterIsVegetarian(Boolean isVegetarian) {
        return repository.getAll(isVegetarian);
    }

    @Override
    public SimpleResponse delete(Long restaurantId, Long menItemId) {
        if(repository.existsById(menItemId)) {
            repository.deleteById(menItemId);
            return new SimpleResponse("DELETE", "MenuItem successfully deleted!");
        }
        return new SimpleResponse("FAIL", "MenuItem fail to delete!");
    }
}
