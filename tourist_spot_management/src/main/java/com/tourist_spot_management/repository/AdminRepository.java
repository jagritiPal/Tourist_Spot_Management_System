package com.tourist_spot_management.repository;

import com.tourist_spot_management.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUsernameOrEmail(String username, String email);
}
