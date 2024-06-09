package com.tourist_spot_management.service.Impl;

import com.tourist_spot_management.entity.Role;
import com.tourist_spot_management.entity.User;
import com.tourist_spot_management.exception.ResourceNotFoundException;
import com.tourist_spot_management.payload.UserDTO;
import com.tourist_spot_management.repository.RoleRepository;
import com.tourist_spot_management.repository.UserRepository;
import com.tourist_spot_management.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Inject BCryptPasswordEncoder

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO, MultipartFile profilePhoto) throws IOException {
        User user = mapToEntity(userDTO);
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            user.setProfilePhoto(profilePhoto.getBytes());
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encode password

        // Retrieve the role from the repository
        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found in the database"));

        // Set the role using Collections.singleton
        user.setRoles(Collections.singleton(userRole));


        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapToDto(user);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO, MultipartFile profilePhoto) throws IOException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setMobile(userDTO.getMobile());
        existingUser.setEmail(userDTO.getEmail());
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            existingUser.setProfilePhoto(profilePhoto.getBytes());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encode new password
        }

        User updatedUser = userRepository.save(existingUser);
        return mapToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    private UserDTO mapToDto(User user) {
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        if (user.getProfilePhoto() != null) {
            dto.setProfilePhoto("data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(user.getProfilePhoto()));
        }
        return dto;
    }

    private User mapToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
