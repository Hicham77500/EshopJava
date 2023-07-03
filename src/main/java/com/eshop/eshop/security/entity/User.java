package com.eshop.eshop.security.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class User implements Serializable {
	//Serializable :Je fais appel a une liste de données, des enumerations, des objets.
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(nullable = false, updatable = false)
		// Variables
		private Long uid; // id de la base de données DB

		@Column(name="userEmail")
		private String email;
		@Column(name="username")
		private String username;
		
		@Column(name="userPassword")
		private String password;
		
		@Column(name="genre")
		private String genre;
		
		@Column(name="completeName")
		private String completeName;

			@Column(name="adress")
		private String adress;

		@Column(name="age")
		private Date age;
		
		@Column(name="mobileNumber")
		private String mobileNumber;
		@Column(name="biography")
		private String biography;
		
		@Column(name="profile_img")
		private String profileImageURL;


		private Date lastLoginDate;
		private Date lastLoginDateDisplay;
		private Date joinDate;
		private String role; // ROLE_USER(read,edit), ROLE_ADMIN(delete)
		private String[] authorities; // [] = tableau de String // Authorities = permissions (read, edit, delete)
		private boolean isActive;// Pour activer les rôles
		private boolean isNotLocked;
		
		
		public User() {
		}


		public User(Long uid, String email, String username, String password, String genre, String completeName,
				String adress, Date age, String mobileNumber, String biography, String profileImageURL,
				Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String role, String[] authorities,
				boolean isActive, boolean isNotLocked) {
			this.uid = uid;
			this.email = email;
			this.username = username;
			this.password = password;
			this.genre = genre;
			this.completeName = completeName;
			this.adress = adress;
			this.age = age;
			this.mobileNumber = mobileNumber;
			this.biography = biography;
			this.profileImageURL = profileImageURL;
			this.lastLoginDate = lastLoginDate;
			this.lastLoginDateDisplay = lastLoginDateDisplay;
			this.joinDate = joinDate;
			this.role = role;
			this.authorities = authorities;
			this.isActive = isActive;
			this.isNotLocked = isNotLocked;
		}


		public Long getUid() {
			return uid;
		}


		public void setUid(Long uid) {
			this.uid = uid;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public String getGenre() {
			return genre;
		}


		public void setGenre(String genre) {
			this.genre = genre;
		}


		public String getCompleteName() {
			return completeName;
		}


		public void setCompleteName(String completeName) {
			this.completeName = completeName;
		}


		public String getAdress() {
			return adress;
		}


		public void setAdress(String adress) {
			this.adress = adress;
		}


		public Date getAge() {
			return age;
		}


		public void setAge(Date age) {
			this.age = age;
		}


		public String getMobileNumber() {
			return mobileNumber;
		}


		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}


		public String getBiography() {
			return biography;
		}


		public void setBiography(String biography) {
			this.biography = biography;
		}


		public String getProfileImageURL() {
			return profileImageURL;
		}


		public void setProfileImageURL(String profileImageURL) {
			this.profileImageURL = profileImageURL;
		}


		public Date getLastLoginDate() {
			return lastLoginDate;
		}


		public void setLastLoginDate(Date lastLoginDate) {
			this.lastLoginDate = lastLoginDate;
		}


		public Date getLastLoginDateDisplay() {
			return lastLoginDateDisplay;
		}


		public void setLastLoginDateDisplay(Date lastLoginDateDisplay) {
			this.lastLoginDateDisplay = lastLoginDateDisplay;
		}


		public Date getJoinDate() {
			return joinDate;
		}


		public void setJoinDate(Date joinDate) {
			this.joinDate = joinDate;
		}


		public String getRole() {
			return role;
		}


		public void setRole(String role) {
			this.role = role;
		}


		public String[] getAuthorities() {
			return authorities;
		}


		public void setAuthorities(String[] authorities) {
			this.authorities = authorities;
		}


		public boolean isActive() {
			return isActive;
		}


		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}


		public boolean isNotLocked() {
			return isNotLocked;
		}


		public void setNotLocked(boolean isNotLocked) {
			this.isNotLocked = isNotLocked;
		}


		
	}