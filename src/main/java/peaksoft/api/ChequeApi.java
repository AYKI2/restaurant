package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.ChequeService;

import java.util.List;

@RestController
@RequestMapping("/api/{restaurantId}/cheques")
public class ChequeApi {
    private final ChequeService service;

    @Autowired
    public ChequeApi(ChequeService service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<ChequeResponse> getAll(@PathVariable Long restaurantId){
        return service.getAll(restaurantId);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse save(@RequestBody ChequeRequest request, @PathVariable Long restaurantId){
        return service.save(restaurantId,request);
    }

    @GetMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public ChequeResponse finById(@PathVariable Long chequeId, @PathVariable Long restaurantId){
        return service.finById(chequeId);
    }
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public String getAllChequeByUser(@PathVariable Long userId, @PathVariable Long restaurantId){
        return service.getAllChequesByUser(userId);
    }
    @GetMapping("/average")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getAveragePrice(@PathVariable Long restaurantId){
        return service.getAveragePrice(restaurantId);
    }

    @PutMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse update(@PathVariable Long chequeId,
                                 @RequestBody ChequeRequest request, @PathVariable Long restaurantId){
        return service.update(restaurantId,chequeId,request);
    }
    @DeleteMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse delete(@PathVariable Long chequeId, @PathVariable Long restaurantId){
        return service.delete(restaurantId,chequeId);
    }
}
