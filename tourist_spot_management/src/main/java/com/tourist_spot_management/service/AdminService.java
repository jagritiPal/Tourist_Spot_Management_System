package com.tourist_spot_management.service;

import com.tourist_spot_management.payload.AdminDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    AdminDTO createUser(AdminDTO adminDTO, MultipartFile profilePhoto) throws IOException;

    List<AdminDTO> getAllUsers();

    AdminDTO getUserById(Long adminId);

    AdminDTO updateUser(Long adminId, AdminDTO adminDTO, MultipartFile profilePhoto) throws IOException;

    void deleteUser(Long adminId);
}
