package com.jolya.oauth2.example.config;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DumbUserServiceImpl implements UserService {

	@Override
	public List<String> getRolesForUser(String id) {
		return Arrays.asList("role_get", "role_post");

	}
}
