package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.AcceptRequest;
import peaksoft.dto.request.LoginRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserNotAcceptResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/{restaurantId}/users")
public class UserApi {
    private final UserService service;

    @Autowired
    public UserApi(UserService service) {
        this.service = service;
    }
    @PostMapping("/signIn")
    public AuthenticationResponse singIn(@RequestBody UserRequest request, @PathVariable Long restaurantId){
        return service.register(request, restaurantId);
    }
    @PostMapping("/logIn")
    public AuthenticationResponse register(@RequestBody LoginRequest request, @PathVariable String restaurantId){
        return service.authenticate(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<UserResponse> getAll(@PathVariable Long restaurantId){
     return service.getAll(restaurantId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public SimpleResponse update(@PathVariable Long restaurantId,@PathVariable Long userId, @RequestBody UserRequest request){
        return service.update(restaurantId,userId,request);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public SimpleResponse delete(@PathVariable Long restaurantId,@PathVariable Long userId){
        return service.delete(restaurantId,userId);
    }
    @GetMapping("/notAccept")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<UserNotAcceptResponse> getAllByAcceptIsFalse(@PathVariable String restaurantId){
        return service.getAllByAcceptIsFalse();
    }
    @PostMapping("/assign")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse assign(@RequestBody AcceptRequest request){
        return service.assign(request);
    }
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public UserResponse findById(@PathVariable Long restaurantId, @PathVariable Long userId){
        return service.finById(restaurantId,userId);
    }
}
