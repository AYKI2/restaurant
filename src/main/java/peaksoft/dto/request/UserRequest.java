package peaksoft.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.lang.NonNull;
import peaksoft.enums.Role;

import java.time.LocalDate;

public record UserRequest(
        @NonNull
        String firstName,
        @NonNull
        String lastName,
        @NonNull
        LocalDate dateOfBirth,
        @NonNull
        String email,
        @NonNull
        @Min(value = 4)
        String password,
        @NonNull
        @Min(value = 13)
        @Max(value = 13)
        String phoneNumber,
        @NonNull
        Role role,
        @NonNull
        int experience
) {
}
