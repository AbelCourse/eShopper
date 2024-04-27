package edu.miu.cs489.eshopper.service.interfaces;

import edu.miu.cs489.eshopper.model.User;
import edu.miu.cs489.eshopper.model.request.UserRegistrationDTO;
import edu.miu.cs489.eshopper.model.response.UserByIdDTO;

public interface IUserService {

    User addUser(UserRegistrationDTO userRegistrationDTO);

    UserByIdDTO getUserById(Long userId);

    void deleteUser(Long userId);
}