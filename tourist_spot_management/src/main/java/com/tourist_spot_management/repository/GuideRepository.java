package com.tourist_spot_management.repository;

import com.tourist_spot_management.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide, Long> {

    Guide findByUsernameOrEmail(String username, String email);
}
