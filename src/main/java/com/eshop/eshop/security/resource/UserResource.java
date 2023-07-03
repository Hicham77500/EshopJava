package com.eshop.eshop.security.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eshop.eshop.security.entity.HttpResponse;
import com.eshop.eshop.security.entity.User;
import com.eshop.eshop.security.entity.UserPrincipal;
import com.eshop.eshop.security.exception.domain.*;
import com.eshop.eshop.security.service.UserService;
import com.eshop.eshop.security.utility.JWTTokenProvider;

import static com.eshop.eshop.security.constant.FileConstant.*;
import static com.eshop.eshop.security.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

@RestController
@RequestMapping(path = { "/" })
// @CrossOrigin("http://localhost:4200" permet de ne laisser passer que les requetes venant d'http://localhost:4200)
@CrossOrigin("*")

public class UserResource extends ExceptionHandling {

	private static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
	// Des que vous voyez la notation Rest c est un Web Service pour obtenir du JSON
	private static final String EMAIL_SENT = "An email with a new password,was sent to : you";

	private UserService userService;
	private AuthenticationManager authenticationManager;
	private JWTTokenProvider jwtTokenProvider;

	@Autowired
	public UserResource(UserService userService, AuthenticationManager authenticationManager,
			JWTTokenProvider jwtTokenProvider) {
		super();
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@GetMapping("/admin/listUser")
	@PreAuthorize("hasAnyAuthority('admin:read')")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> user = userService.getUsers();

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/admin/getUser/{uid}")
	@PreAuthorize("hasAnyAuthority('admin:read')")
	public ResponseEntity<User> getUser(@PathVariable("uid") long uid) {
		User user = userService.findUserById(uid);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) throws UserNotFoundException {
		authenticate(user.getUsername(), user.getPassword());
		User loginUser = userService.findUserByUsername(user.getUsername());
		UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
		return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		User newUser = userService.register(user);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@PostMapping("/admin/addUser")
	@PreAuthorize("hasAnyAuthority('admin:create')")
	public ResponseEntity<User> addNewUser(@RequestBody User user)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException,
			NotAnImageFileException {
		User newUser = userService.addNewUser(user);

		return new ResponseEntity<>(newUser, HttpStatus.OK);
	};

	@PutMapping("/admin/updateUser/{uid}")
	@PreAuthorize("hasAnyAuthority('admin:update')")
	public ResponseEntity<User> update(@PathVariable(name = "uid") Long uid, @RequestBody User user)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		User updateUser = userService.updateUser(uid, user);
		System.out.println("Here Update : " + updateUser);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	};

	@DeleteMapping("/admin/deleteUser/{uid}")
	@PreAuthorize("hasAnyAuthority('admin:delete')")
	public ResponseEntity<HttpResponse> deleteUser(@PathVariable("uid") Long id)
			throws EmailNotFoundException, MessagingException {
		userService.deleteUser(id);
		return response(HttpStatus.OK, USER_DELETED_SUCCESSFULLY + id);
	}

	@GetMapping("/findUser/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		User user = userService.findUserByUsername(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}



	@PostMapping("/admin/updateProfileImage")
	@PreAuthorize("hasAnyAuthority('admin:update')")
	public ResponseEntity<User> updateProfileImage(
			@RequestParam("username") String username,
			@RequestParam(value = "profileImage") MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, IOException, NotAnImageFileException,
			EmailExistException {
		User user = userService.updateProfileImage(username, profileImage);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {

		HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase());

		return new ResponseEntity<>(body, httpStatus);
	}

	private HttpHeaders getJwtHeader(UserPrincipal user) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(user));

		return headers;
	}

	private void authenticate(String username, String password) {
		// donner une instance de UsernamAUthentication
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

	}

}
