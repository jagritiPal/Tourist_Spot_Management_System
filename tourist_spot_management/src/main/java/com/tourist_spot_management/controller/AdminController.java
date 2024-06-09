package com.tourist_spot_management.controller;

import com.tourist_spot_management.payload.AdminDTO;
import com.tourist_spot_management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // http://localhost:8080/api/admins/add
    @PostMapping("/add")
    public ResponseEntity<AdminDTO> createAdmin(
            @RequestParam("profilePhoto") MultipartFile profilePhoto,
            @Valid @RequestParam("username") String username,
            @Valid @RequestParam("email") String email,
            @Valid @RequestParam("mobile") String mobile,
            @Valid @RequestParam("password") String password,
            @Valid @RequestParam("firstName") String firstName,
            @Valid @RequestParam("lastName") String lastName) throws IOException {

        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setUsername(username);
        adminDTO.setEmail(email);
        adminDTO.setMobile(mobile);
        adminDTO.setPassword(password);
        adminDTO.setFirstName(firstName);
        adminDTO.setLastName(lastName);

        AdminDTO createdAdmin = adminService.createUser(adminDTO, profilePhoto);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/admins
    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllUsers();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    // http://localhost:8080/api/admins/1
    @GetMapping("/{adminId}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long adminId) {
        AdminDTO admin = adminService.getUserById(adminId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    // http://localhost:8080/api/admins/1
    @PutMapping("/{adminId}")
    public ResponseEntity<AdminDTO> updateAdmin(
            @PathVariable Long adminId,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @Valid @RequestParam("username") String username,
            @Valid @RequestParam("email") String email,
            @Valid @RequestParam("mobile") String mobile,
            @Valid @RequestParam("password") String password,
            @Valid @RequestParam("firstName") String firstName,
            @Valid @RequestParam("lastName") String lastName) throws IOException {

        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setUsername(username);
        adminDTO.setEmail(email);
        adminDTO.setMobile(mobile);
        adminDTO.setPassword(password);
        adminDTO.setFirstName(firstName);
        adminDTO.setLastName(lastName);

        AdminDTO updatedAdmin = adminService.updateUser(adminId, adminDTO, profilePhoto);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    // http://localhost:8080/api/admins/1
    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long adminId) {
        adminService.deleteUser(adminId);
        return new ResponseEntity<>("Admin is deleted successfully.", HttpStatus.OK);
    }
}
