package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantApi {
    private final RestaurantService service;

    @Autowired
    public RestaurantApi(RestaurantService service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<RestaurantResponse> getAll(){
        return service.getAll();
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse save(@RequestBody RestaurantRequest request){
        return service.save(request);
    }

    @GetMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public RestaurantResponse finById(@PathVariable Long restaurantId){
        return service.finById(restaurantId);
    }

    @PutMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse update(@RequestBody RestaurantRequest request, @PathVariable Long restaurantId){
        return service.update(restaurantId,request);
    }
    @DeleteMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long restaurantId){
        return service.delete(restaurantId);
    }
}
