package edu.miu.cs489.eshopper.controller;

import edu.miu.cs489.eshopper.config.JWTManagementUtilityService;
import edu.miu.cs489.eshopper.model.request.UserLoginRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/v1"})
public class UserAuthController {

    private final JWTManagementUtilityService jwtManagementUtilityService;
    private final AuthenticationManager authenticationManager;

    public UserAuthController(JWTManagementUtilityService jwtManagementUtilityService,
                              AuthenticationManager authenticationManager) {
        this.jwtManagementUtilityService = jwtManagementUtilityService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = {"/authenticate"})
    public String authenticateUser(@Valid @RequestBody UserLoginRequest userAuthRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuthRequest.getEmail(),
                            userAuthRequest.getPassword())

            );

        } catch (Exception ex) {
            System.out.println("UserAuthException is: " + ex);
            System.out.println("Invalid Username and/or Password!");
            throw ex;
        }
        // todo fetch all the user's info - firstName, lastName etc.
        return jwtManagementUtilityService.generateToken(userAuthRequest.getEmail());
    }
}
