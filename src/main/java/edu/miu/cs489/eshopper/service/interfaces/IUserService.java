package edu.miu.cs489.eshopper.service.interfaces;

import edu.miu.cs489.eshopper.model.User;
import edu.miu.cs489.eshopper.model.request.UserRegistrationDTO;

public interface IUserService {

    User addUser(UserRegistrationDTO userRegistrationDTO);

    User getUserById(Long userId);

    void deleteUser(Long userId);
}