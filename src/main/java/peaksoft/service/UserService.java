package peaksoft.service;

import peaksoft.dto.request.AcceptRequest;
import peaksoft.dto.request.LoginRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserNotAcceptResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll(Long restaurantId);
    UserResponse finById(Long restaurantId,Long userId);
    SimpleResponse update(Long restaurantId,Long userId, UserRequest request);
    SimpleResponse delete(Long restaurantId,Long userId);
    List<UserNotAcceptResponse> getAllByAcceptIsFalse();
    SimpleResponse assign(AcceptRequest request);
    AuthenticationResponse register(UserRequest request, Long restaurantId);
    AuthenticationResponse authenticate(LoginRequest request);
}
