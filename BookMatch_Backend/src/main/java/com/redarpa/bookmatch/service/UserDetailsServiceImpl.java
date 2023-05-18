/**
 * 
 */
package com.redarpa.bookmatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.redarpa.bookmatch.dao.IUserDAO;
import com.redarpa.bookmatch.dto.User;

/**
 * @author Marc
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired // Autowire UserDAO
	IUserDAO userDAO;

	// Load user details by username
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
}
