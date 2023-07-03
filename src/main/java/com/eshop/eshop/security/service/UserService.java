package com.eshop.eshop.security.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.eshop.eshop.security.entity.User;
import com.eshop.eshop.security.exception.domain.EmailExistException;
import com.eshop.eshop.security.exception.domain.EmailNotFoundException;
import com.eshop.eshop.security.exception.domain.NotAnImageFileException;
import com.eshop.eshop.security.exception.domain.UserNotFoundException;
import com.eshop.eshop.security.exception.domain.UsernameExistException;


public interface UserService {

	List<User> getUsers();
	
	User register(User user) throws UserNotFoundException, UsernameExistException, EmailExistException;
	
	User addNewUser(User user) throws 
	IOException, UserNotFoundException, UsernameExistException, EmailExistException;

	User updateUser(Long uid, User user) throws UserNotFoundException, UsernameExistException, EmailExistException;

	void deleteUser(Long uid);
	

	User findUserByEmail(String email);
	User findUserByUsername(String username);
	User findUserById(Long uid);
	User updateProfileImage(String username, MultipartFile profileImage) throws NotAnImageFileException, IOException, UserNotFoundException, UsernameExistException, EmailExistException;
	void resetPassword(String email) throws EmailNotFoundException, MessagingException;

	User ChangePasswordUser(User user);
			
}
