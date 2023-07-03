package com.eshop.eshop.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.eshop.security.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	User findUserByUsername(String username);
	
	User findUserByEmail(String email);

	User findUserByUid(Long uid);


}
