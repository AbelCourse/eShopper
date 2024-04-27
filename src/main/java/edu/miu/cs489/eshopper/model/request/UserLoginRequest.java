package edu.miu.cs489.eshopper.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    @NotBlank(message = "email cannot be null, empty or blankspace(s)")
    private String email;
    @NotBlank(message = "Password cannot be null, empty or blankspace(s)")
    private String password;
}

