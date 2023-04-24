package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.*;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.*;
import peaksoft.service.MenuItemService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;
    private final SubCategoryRepository subCategoryRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public List<MenuItemResponse> getAll(Long restaurantId,String ascOrDesc) {
        if(ascOrDesc.equals("asc")) {
            List<MenuItem> allAsc = repository.getAllAsc(restaurantId);
            return createMenuItemResponse(allAsc);
        }else if(ascOrDesc.equals("desc")){
            List<MenuItem> allDesc = repository.getAllDesc(restaurantId);
            return createMenuItemResponse(allDesc);
        }else {
            return repository.getAll(restaurantId);
        }
    }

    private List<MenuItemResponse> createMenuItemResponse(List<MenuItem> allAscOrDesc) {
        List<MenuItemResponse> responses = new ArrayList<>();
        for (MenuItem response :allAscOrDesc) {
            if(response.getStopList() == null || !response.getStopList().getDate().equals(LocalDate.now())){
                responses.add(new MenuItemResponse(
                        response.getId(),
                        response.getName(),
                        response.getImage(),
                        response.getPrice(),
                        response.getDescription(),
                        response.isVegetarian(),
                        response.getSubCategory().getName()
                ));
            }
        }
        return responses;
    }


    @Override
    public SimpleResponse save(Long restaurantId, MenuItemRequest request) {
        MenuItem menuItem = new MenuItem();
        SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId())
                .orElseThrow(() ->
                        new NotFoundException("SubCategory with id: " + request.subCategoryId() + " not found!"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new NotFoundException("Restaurant with id: " + restaurantId + " not found!"));
        setMenuItem(request, menuItem, subCategory, restaurant);
        repository.save(menuItem);
        return new SimpleResponse("SAVE", "MenuItem successfully saved!");
    }

    private void setMenuItem(MenuItemRequest request,
                             MenuItem menuItem,
                             SubCategory subCategory,
                             Restaurant restaurant) {
        menuItem.setName(request.name());
        menuItem.setImage(request.image());
        menuItem.setPrice(request.price());
        menuItem.setDescription(request.description());
        menuItem.setVegetarian(request.isVegetarian());
        menuItem.setSubCategory(subCategory);
        menuItem.setRestaurant(restaurant);
    }

    @Override
    public MenuItemResponse finById(Long restaurantId, Long menItemId) {
        return repository.getMenuItemById(restaurantId,menItemId)
                .orElseThrow(()->
                        new NotFoundException("MenuItem with id: "+ menItemId +" not found!"));
    }

    @Override
    public SimpleResponse update(Long restaurantId, Long menItemId, MenuItemRequest request) {
        MenuItem menuItem = repository.findById(menItemId)
                .orElseThrow(() ->
                        new NotFoundException("MenuItem with id: " + menItemId + " not found!"));
        SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId())
                .orElseThrow(() ->
                        new NotFoundException("SubCategory with id: " + request.subCategoryId() + " not found!"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new NotFoundException("Restaurant with id: " + restaurantId + " not found!"));
        if(repository.existsById(menItemId)) {
            setMenuItem(request, menuItem, subCategory, restaurant);
            return new SimpleResponse("UPDATE", "MenuItem successfully updated!");
        }
        return new SimpleResponse("FAIL", "MenuItem fail to update!");
    }
    @Override
    public List<? extends GlobalSearchResponse> globalSearch(Long restaurantId, String word) {
        if (word == null || word.isEmpty()) {
            return repository.getAll(restaurantId);
        }
        return repository.globalSearch(word);
    }
    @Override
    public List<MenuItemResponse> filterIsVegetarian(Boolean isVegetarian) {
        return repository.getAllByVegetarian(isVegetarian);
    }

    @Override
    public SimpleResponse delete(Long restaurantId, Long menItemId) {
        if(repository.existsById(menItemId)) {
            MenuItem menuItem = repository.findById(menItemId)
                    .orElseThrow(() ->
                            new NotFoundException("MenuItem with id: " + menItemId + " not found!"));
            List<MenuItem> items = new ArrayList<>();
            for (Cheque cheque : menuItem.getCheques()) {
                for (MenuItem item : cheque.getMenuItems()) {
                    if(!item.getId().equals(menItemId)){
                        items.add(item);
                    }
                }
                cheque.setMenuItems(items);
            }
            menuItem.setCheques(new ArrayList<>());
            repository.deleteById(menItemId);
            return new SimpleResponse("DELETE", "MenuItem successfully deleted!");
        }
        return new SimpleResponse("FAIL", "MenuItem fail to delete!");
    }

    @Override
    public PaginationResponse getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("price"));
        Page<MenuItemResponse> itemPage = repository.findAllBy(pageable);
        return new PaginationResponse(
                itemPage.getContent(),
                itemPage.getNumber()+1,
                itemPage.getTotalPages()
        );
    }
}
