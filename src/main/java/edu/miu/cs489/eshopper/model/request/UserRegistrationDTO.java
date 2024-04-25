package edu.miu.cs489.eshopper.model.request;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}