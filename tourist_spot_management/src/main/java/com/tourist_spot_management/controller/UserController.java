package com.tourist_spot_management.controller;

import com.tourist_spot_management.payload.UserDTO;
import com.tourist_spot_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserDTO> createUser(
            @RequestParam("profilePhoto") MultipartFile profilePhoto,
            @Valid @RequestParam("firstName") String firstName,
            @Valid @RequestParam("lastName") String lastName,
            @Valid @RequestParam("username") String username,
            @Valid @RequestParam("password") String password,
            @Valid @RequestParam("mobile") String mobile,
            @Valid @RequestParam("email") String email) throws IOException {

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setMobile(mobile);
        userDTO.setEmail(email);

        UserDTO createdUser = userService.createUser(userDTO, profilePhoto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long userId,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @Valid @RequestParam("firstName") String firstName,
            @Valid @RequestParam("lastName") String lastName,
            @Valid @RequestParam("username") String username,
            @Valid @RequestParam("password") String password,
            @Valid @RequestParam("mobile") String mobile,
            @Valid @RequestParam("email") String email) throws IOException {

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setMobile(mobile);
        userDTO.setEmail(email);

        UserDTO updatedUser = userService.updateUser(userId, userDTO, profilePhoto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User is deleted successfully.", HttpStatus.OK);
    }
}
