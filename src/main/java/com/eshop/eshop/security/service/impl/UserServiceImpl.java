package com.eshop.eshop.security.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eshop.eshop.security.entity.User;
import com.eshop.eshop.security.entity.UserPrincipal;
import com.eshop.eshop.security.enumeration.Role;
import com.eshop.eshop.security.exception.domain.EmailExistException;
import com.eshop.eshop.security.exception.domain.EmailNotFoundException;
import com.eshop.eshop.security.exception.domain.NotAnImageFileException;
import com.eshop.eshop.security.exception.domain.UserNotFoundException;
import com.eshop.eshop.security.exception.domain.UsernameExistException;
import com.eshop.eshop.security.repository.UserRepository;
import com.eshop.eshop.security.service.EmailService;
import com.eshop.eshop.security.service.LoginAttemptService;
import com.eshop.eshop.security.service.UserService;

import static com.eshop.eshop.security.constant.FileConstant.*;
import static com.eshop.eshop.security.constant.UserImplConstant.*;
import static com.eshop.eshop.security.enumeration.Role.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.*;

@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired // permet d'activer l'injection automatique de dépendance
	private UserRepository iUserRepository;
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private BCryptPasswordEncoder passwordEncoder;
	private LoginAttemptService loginAttemptService;
	@Autowired
	private EmailService emailService;

	@Autowired
	public UserServiceImpl(UserRepository iUserRepository, BCryptPasswordEncoder passwordEncoder,
			LoginAttemptService loginAttemptService) {
		super();
		this.iUserRepository = iUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;

	}

	// Ajoute un objet utilisateur dans la base de données, réserver au back office
	// elle est destinée à l'ajout d'un
	// utilisateur exécuté par un administrateur de l'application
	@Override
	public User addNewUser(User userCreated)
			throws IOException, UserNotFoundException, UsernameExistException, EmailExistException {
		validateNewUsernameAndEmail(EMPTY, userCreated.getUsername(), userCreated.getEmail());
		User user = new User();
		String encodedPassword = encodePassword(userCreated.getPassword());
		user.setCompleteName(userCreated.getCompleteName());
		user.setUsername(userCreated.getUsername());
		user.setEmail(userCreated.getEmail());
		user.setAge(userCreated.getAge());
		user.setGenre(userCreated.getGenre());
		user.setJoinDate(new Date());
		user.setPassword(encodedPassword);
		user.setActive(true);
		user.setNotLocked(true);
		user.setRole(getRoleEnumName(userCreated.getRole()).name());
		user.setAuthorities(getRoleEnumName(userCreated.getRole()).getAuthorities());
		if (user.getGenre().equals("homme")) {

			user.setProfileImageURL("homme.png");
		} else {
			user.setProfileImageURL("femme.png");
		}

		iUserRepository.save(user);
		LOGGER.info(NEW_USER_PASSWORD + userCreated.getPassword());

		return user;

	}

	// Ajoute également un objet utilisateur dans la base de données, réserver au
	// front office elle est destinée
	// à l'ajout d'un utilisateur lorsqu'un utilisateur créé un compte dans
	// l'application

	@Override
	public User register(User userCreated) throws UserNotFoundException,
	UsernameExistException, EmailExistException {
	// try {
	validateNewUsernameAndEmail(EMPTY, userCreated.getUsername(),
	userCreated.getEmail());
	User user = new User();
	String encodedPassword = encodePassword(userCreated.getPassword());
	user.setCompleteName(userCreated.getCompleteName());
	user.setUsername(userCreated.getUsername());
	user.setEmail(userCreated.getEmail());
	user.setAge(new Date());
	user.setGenre(userCreated.getGenre());
	user.setJoinDate(new Date());
	user.setPassword(encodedPassword);
	user.setActive(true);
	user.setNotLocked(true);
	user.setRole(ROLE_USER.name());
	user.setAuthorities(ROLE_USER.getAuthorities());
	if (user.getGenre().equals("homme")) {

	user.setProfileImageURL("homme.png");
	} else {
	user.setProfileImageURL("femme.png");
	}
	iUserRepository.save(user);
	LOGGER.info(NEW_USER_PASSWORD + userCreated.getPassword());
	
	return user;

	}

	// getUsers() renvoie dans une liste tous les objets User utilisateurs
	// enregistrés dans la base de données
	@Override
	public List<User> getUsers() {
		return iUserRepository.findAll();
	}

	// findUserByEmail() cette méthode trouve un utilisateur en le recherchant par
	// son email
	@Override
	public User findUserByEmail(String email) {
		return iUserRepository.findUserByEmail(email);
	}

	// findUserByUsername() cette méthode trouve un utilisateur en le recherchant
	// par : username
	@Override
	public User findUserByUsername(String username) {
		return iUserRepository.findUserByUsername(username);
	}

	@Override
	public User findUserById(Long uid) {
		return iUserRepository.findUserByUid(uid);

	}

	@Override
	public User updateUser(Long id, User user)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		String currentUsername = findUserById(id).getUsername();
		System.out.println(currentUsername);
		User currentUser = validateNewUsernameAndEmail(currentUsername, user.getUsername(), user.getEmail());
		if (user.getBiography() != null || user.getBiography() != "") {
			currentUser.setBiography(user.getBiography());
			currentUser.setActive(true);
			currentUser.setNotLocked(true);
		} else {
			currentUser.setCompleteName(user.getCompleteName());
			currentUser.setUsername(user.getUsername());
			currentUser.setEmail(user.getEmail());
			currentUser.setAge(user.getAge());
			currentUser.setActive(user.isActive());
			currentUser.setNotLocked(user.isNotLocked());
			currentUser.setRole(getRoleEnumName(user.getRole()).name());
			currentUser.setAuthorities(getRoleEnumName(user.getRole()).getAuthorities());
			currentUser.setGenre(user.getGenre());
			currentUser.setActive(true);
			currentUser.setNotLocked(true);
			currentUser.setMobileNumber(user.getMobileNumber());
			currentUser.setProfileImageURL(currentUser.getProfileImageURL());
		}

		iUserRepository.save(currentUser);
		System.out.println("Lett username :" + user.getUsername());
		return currentUser;
	}

	// deleteUser() supprime un objet User en le ciblant via son ID
	@Override
	public void deleteUser(Long uid) {

		iUserRepository.deleteById(uid);
	}

	// saveProfileImage() est appelée par addNewUser() elle permet la création d'une
	// image de profile
	private void saveProfileImage(User user, MultipartFile profileImage) throws IOException, NotAnImageFileException {

		if (profileImage != null) {
			if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE)
					.contains(profileImage.getContentType())) {
				throw new NotAnImageFileException(profileImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
			}

			String path = "src/main/resources/static/images";
			user.setProfileImageURL(profileImage.getOriginalFilename());
			Files.copy(profileImage.getInputStream(),
					Paths.get(path + File.separator + profileImage.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);

			iUserRepository.save(user);
			LOGGER.info(FILE_SAVED_IN_SYSTEM + profileImage.getOriginalFilename());

		}

	}

	// Enumération d'une liste de rôles pour déterminer le(s) droit(s) d'un
	// l'utilisateur dans l'application
	// Chaque rôle peut contenir un ou plusieurs droit(s)(create, read, update,
	// delete)
	private Role getRoleEnumName(String role) {
		return Role.valueOf(role);
	}

	// loadUserByUsername() cette méthode cherche un utilisateur en faisant et
	// exécute des actions en foction du résultat
	// retourné
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = iUserRepository.findUserByUsername(username);

		if (user == null) {
			LOGGER.error(USER_NOT_FOUND_BY_USERNAME + username);
			throw new UsernameNotFoundException(USER_NOT_FOUND_BY_USERNAME + username);
		} else {
			try {
				validateLoginAttempt(user);
				user.setLastLoginDateDisplay(user.getLastLoginDate());
				user.setLastLoginDate(new Date());
				iUserRepository.save(user);
				UserPrincipal userPrincipal = new UserPrincipal(user);
				return userPrincipal;
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	// Cette méthode permet de réinitialiser le mot de passe de l'utilisateur
	@Override
	public void resetPassword(String email) throws EmailNotFoundException, MessagingException {
		User user = iUserRepository.findUserByEmail(email);
		if (user == null) {
			throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL);
		}
		String password = generatePassword();
		user.setPassword(encodePassword(password));
		passwordEncoder.matches(password, password);
		iUserRepository.save(user);
		LOGGER.info("New user password " + password); // J'affiche le password dans les LOGGERS
		// emailService.sendNewPasswordEmail(user.getFirstname(), password,
		// user.getEmail());
	}

	// updateProfileImage() met à jour l'image de profile
	@Override
	public User updateProfileImage(String username, MultipartFile profileImage) throws NotAnImageFileException,
			UserNotFoundException, UsernameExistException, EmailExistException, IOException {
		// try {
		User user = validateNewUsernameAndEmail(username, null, null);
		saveProfileImage(user, profileImage);
		return user;
		// } catch (UserNotFoundException | UsernameExistException | EmailExistException
		// | IOException e) {
		// }
		// return null;
	}

	// encodePassword() encode le mot de passe de l'utilisateur
	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	// generatePassword() génère un mot de passe à l'utilisateur
	private String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

	// generateUserId() génère un uid
	private String generateUserId() {
		return RandomStringUtils.randomNumeric(10);
	}

	// validateNewUsernameAndEmail() est appelé par validateNewUsernameAndEmail() et
	// register()
	// elle vérifie si les valeurs Username et Email appartiennent déjà à un
	// utlisateur
	private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
			throws UserNotFoundException, UsernameExistException, EmailExistException {

		User userByNewUsername = findUserByUsername(newUsername);
		User userByNewEmail = findUserByEmail(newEmail);

		if (StringUtils.isNotBlank(currentUsername)) {
			User currentUser = findUserByUsername(currentUsername);

			if (currentUser == null) {
				throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
			}

			if (userByNewUsername != null && !currentUser.getUid().equals(userByNewUsername.getUid())) {

				throw new UsernameExistException(USERNAME_ALREADY_EXIST);
			}

			if (userByNewEmail != null && !currentUser.getUid().equals(userByNewEmail.getUid())) {
				throw new EmailExistException(EMAIL_ALREADY_EXIST);
			}
			return currentUser;
		} else {

			if (userByNewUsername != null) {
				throw new UsernameExistException(USERNAME_ALREADY_EXIST/* + userByNewUsername */);
			}

			if (userByNewEmail != null) {
				throw new EmailExistException(EMAIL_ALREADY_EXIST/* + currentUsername + userByNewEmail */);
			}
			return null;
		}
	}

	// validateLoginAttempt() block un utilisateur si celui-ci à exécuté trop de
	// tentatives de connexion
	// avec un mauvais mot de passe
	private void validateLoginAttempt(User user) throws ExecutionException {
		if (user.isNotLocked()) {
			if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}
		} else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}
	}

	@Override
	public User ChangePasswordUser(User user) {
		User loginUser = findUserByUsername(user.getUsername());
		String encodedPassword = encodePassword(user.getPassword());
		loginUser.setPassword(encodedPassword);
		return iUserRepository.save(loginUser);
	}

}
