package com.eshop.eshop.security.service;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {

	private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
	private static final int ATTEMPT_INCREMENT = 1;
	private LoadingCache<String, Integer> loginAttemptCache;

	public LoginAttemptService() {
		super();
		this.loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, MINUTES).maximumSize(100)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

	public void evictUserFromLoginAttemptCache(String username) {
		loginAttemptCache.invalidate(username);
	}

	public void addUserToLoginAttemptCache(String username) {

		int attempts = 0;
		try {
			attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username);
			loginAttemptCache.put(username, attempts);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean hasExceededMaxAttempts(String username) throws ExecutionException {

		return loginAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
	}

}
