package com.example;




import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.User;

@RestController
public class UserController {

	//private final UserRepository userRepository;

	/**
	 * Converts a numerical role to an equivalent list of roles.
	 * 
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();
		
		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");
			
		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}
		
		return roles;
	}
	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects.
	 * 
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}	

	@RequestMapping("/users")
	public Iterable<User> getUsers() {
		User user= new User();
		ArrayList<User> list = new ArrayList<User>();
	
		user.setId(1);
		user.setLogin("roy");
		user.setPassword("spring");
		user.setName("Roy");
		ArrayList<GrantedAuthority> authList = (ArrayList<GrantedAuthority>) getGrantedAuthorities(getRoles(1));
		user.setRoles(authList);
	
		list.add(user);	
		return list;
	}

}
