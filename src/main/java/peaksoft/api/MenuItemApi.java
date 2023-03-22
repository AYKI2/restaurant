package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/{restaurantId}/menuItems")
public class MenuItemApi {
    private final MenuItemService service;

    @Autowired
    public MenuItemApi(MenuItemService service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<MenuItemResponse> getAll(@PathVariable Long restaurantId){
        return service.getAll(restaurantId);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse save(@RequestBody MenuItemRequest request, @PathVariable Long restaurantId){
        return service.save(restaurantId,request);
    }

    @GetMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public MenuItemResponse finById(@PathVariable Long chequeId, @PathVariable Long restaurantId){
        return service.finById(restaurantId,chequeId);
    }

    @GetMapping("/search")
    public List<MenuItemResponse> search(@RequestParam String word, @PathVariable Long restaurantId) {
        return service.globalSearch(restaurantId,word);
    }
    @GetMapping("/filter")
    public List<MenuItemResponse> filter(@RequestParam Boolean isVegetarian) {
        return service.filterIsVegetarian(isVegetarian);
    }

    @PutMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse update(@PathVariable Long chequeId,
                                 @RequestBody MenuItemRequest request, @PathVariable Long restaurantId){
        return service.update(restaurantId,chequeId,request);
    }
    @DeleteMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse delete(@PathVariable Long chequeId, @PathVariable Long restaurantId){
        return service.delete(restaurantId,chequeId);
    }
}
