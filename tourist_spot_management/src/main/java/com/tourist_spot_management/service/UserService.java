package com.tourist_spot_management.service;

import com.tourist_spot_management.payload.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO, MultipartFile profilePhoto) throws IOException;

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO, MultipartFile profilePhoto) throws IOException;

    void deleteUser(Long userId);
}
