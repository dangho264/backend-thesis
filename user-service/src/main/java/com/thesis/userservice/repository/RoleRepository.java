package com.thesis.userservice.repository;

import com.thesis.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>, PagingAndSortingRepository<Role, Integer> {
}
