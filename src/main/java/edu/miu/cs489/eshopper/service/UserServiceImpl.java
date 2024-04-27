package edu.miu.cs489.eshopper.service;

import edu.miu.cs489.eshopper.config.Mapper;
import edu.miu.cs489.eshopper.exception.ResourceNotFoundException;
import edu.miu.cs489.eshopper.model.User;
import edu.miu.cs489.eshopper.model.request.UserRegistrationDTO;
import edu.miu.cs489.eshopper.model.response.UserByIdDTO;
import edu.miu.cs489.eshopper.repository.UserRepository;
import edu.miu.cs489.eshopper.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserServiceImpl(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User addUser(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.findByEmail(userRegistrationDTO.getEmail()).isPresent()) {
            throw new ResourceNotFoundException("User with email " + userRegistrationDTO.getEmail() + " already exists");
        }
        User user = User.builder()
                .firstName(userRegistrationDTO.getFirstName())
                .lastName(userRegistrationDTO.getLastName())
                .email(userRegistrationDTO.getEmail())
                .password(userRegistrationDTO.getPassword())
                .build();
        user.setReviewList(new ArrayList<>());
        user.setCart(null);
        user.setRoles(new HashSet<>());
        System.err.println("User " + user);
//        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public UserByIdDTO getUserById(Long userId) {
        return mapUserToUserByIdDTO(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found")));
    }

    
    private UserByIdDTO mapUserToUserByIdDTO(User user) {
        return mapper.map(user, UserByIdDTO.class);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}