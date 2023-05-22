/**
 * 
 */
package com.redarpa.bookmatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redarpa.bookmatch.dao.IUserDAO;
import com.redarpa.bookmatch.dto.User;

/**
 * @author RedArpa - BookMatch
 *
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired // Autowire UserDAO
	IUserDAO userDAO;

	// Load user details by username
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDAO.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

		return UserDetailsImpl.build(user);
	}
}
