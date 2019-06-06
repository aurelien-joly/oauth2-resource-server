package com.jolya.oauth2.example.config;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

	/**
	 * Get the roles for the user Id
	 *
	 * @param id
	 * @return
	 */
	public List<String> getRolesForUser(String id);
}
