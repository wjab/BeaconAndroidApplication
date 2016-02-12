package com.example;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.data.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {





	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= new User();
		user.setId(1);
		user.setLogin("roy");
		user.setPassword("spring");
		user.setName("Roy");
		ArrayList<GrantedAuthority> authList = getGrantedAuthorities(getRoles(1));
		user.setRoles(authList);
		return new UserRepositoryUserDetails(user);
	}
	/**
	 * Converts a numerical role to an equivalent list of roles.
	 * 
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(Integer role) {
		ArrayList<String> roles = new ArrayList<String>();
		
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
	public static ArrayList<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
	private final static class UserRepositoryUserDetails extends User implements UserDetails {

		private static final long serialVersionUID = 1L;

		private UserRepositoryUserDetails(User user) {
		//	super(user.getUser(),user.getPassword(),user.getEnable(),user.getCategory_id(),user.getTotal_gift_points(),user.getCreationDate(),user.getModifiedDate(), user.getName(),user.getLastName(),user.getEmail(),user.getPhone());
		super();
		}

		/**
		 * Retrieves a collection of {@link GrantedAuthority} based on a numerical role.
		 * 
		 * @param role the numerical role
		 * @return a collection of {@link GrantedAuthority
		 */
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(1));
			return authList;
		}
		
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

		@Override
		public String getUsername() {
			return getName();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}



	}

}
