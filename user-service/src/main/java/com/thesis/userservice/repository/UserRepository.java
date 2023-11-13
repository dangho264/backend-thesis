package com.thesis.userservice.repository;


import com.thesis.userservice.entity.Role;
import com.thesis.userservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);
	@Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
	Page<User> findAllByRole(@Param("role") Role role, Pageable pageable);
}
