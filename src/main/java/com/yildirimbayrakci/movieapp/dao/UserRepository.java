package com.yildirimbayrakci.movieapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yildirimbayrakci.movieapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndType(String email, String type);

}
