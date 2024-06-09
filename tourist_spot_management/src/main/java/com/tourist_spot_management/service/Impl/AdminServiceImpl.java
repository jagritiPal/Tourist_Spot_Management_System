package com.tourist_spot_management.service.Impl;

import com.tourist_spot_management.entity.Admin;
import com.tourist_spot_management.entity.Role;
import com.tourist_spot_management.exception.ResourceNotFoundException;
import com.tourist_spot_management.payload.AdminDTO;
import com.tourist_spot_management.repository.AdminRepository;
import com.tourist_spot_management.repository.RoleRepository;
import com.tourist_spot_management.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Import BCryptPasswordEncoder
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Inject BCryptPasswordEncoder

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AdminDTO createUser(AdminDTO adminDTO, MultipartFile profilePhoto) throws IOException {
        Admin admin = mapToEntity(adminDTO);
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            admin.setProfilePhoto(profilePhoto.getBytes());
        }
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword())); // Encode password

        // Retrieve the role from the repository
        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found in the database"));

        // Set the role using Collections.singleton
        admin.setRoles(Collections.singleton(adminRole));

        Admin savedAdmin = adminRepository.save(admin);
        return mapToDto(savedAdmin);
    }

    @Override
    public List<AdminDTO> getAllUsers() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdminDTO getUserById(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));
        return mapToDto(admin);
    }

    @Override
    public AdminDTO updateUser(Long adminId, AdminDTO adminDTO, MultipartFile profilePhoto) throws IOException {
        Admin existingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));

        existingAdmin.setFirstName(adminDTO.getFirstName());
        existingAdmin.setLastName(adminDTO.getLastName());
        existingAdmin.setUsername(adminDTO.getUsername());
        existingAdmin.setMobile(adminDTO.getMobile());
        existingAdmin.setEmail(adminDTO.getEmail());
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            existingAdmin.setProfilePhoto(profilePhoto.getBytes());
        }
        if (adminDTO.getPassword() != null && !adminDTO.getPassword().isEmpty()) {
            existingAdmin.setPassword(passwordEncoder.encode(adminDTO.getPassword())); // Encode new password
        }

        Admin updatedAdmin = adminRepository.save(existingAdmin);
        return mapToDto(updatedAdmin);
    }

    @Override
    public void deleteUser(Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new ResourceNotFoundException("Admin not found with id: " + adminId);
        }
        adminRepository.deleteById(adminId);
    }

    private AdminDTO mapToDto(Admin admin) {
        AdminDTO dto = modelMapper.map(admin, AdminDTO.class);
        if (admin.getProfilePhoto() != null) {
            dto.setProfilePhoto("data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(admin.getProfilePhoto()));
        }
        return dto;
    }

    private Admin mapToEntity(AdminDTO adminDTO) {
        return modelMapper.map(adminDTO, Admin.class);
    }
}
