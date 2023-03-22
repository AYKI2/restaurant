package peaksoft.dto.response;

import peaksoft.enums.Role;

import java.time.LocalDate;

public record UserNotAcceptResponse(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        Role role,
        int experience,
        boolean isAccept
){
}
