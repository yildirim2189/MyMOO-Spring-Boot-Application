package com.yildirimbayrakci.movieapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yildirimbayrakci.movieapp.entity.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	// Find actor by name.
	public Actor findByName(String name);
	
	// Search actors containing query.
	public List<Actor> findByNameContaining(String query);
	
	// Check with name if actor exists.
	public boolean existsActorByName(String name);
}
