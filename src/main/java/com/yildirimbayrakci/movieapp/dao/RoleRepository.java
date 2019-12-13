package com.yildirimbayrakci.movieapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yildirimbayrakci.movieapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(String roleName);
}
