package com.yo.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yo.model.UserData;

public interface UserRepository extends JpaRepository<UserData, Integer> {

	public UserData findByUsername(String username);
}
