package com.example.data;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class User {

	@Id
	private Integer id;


	private String name;


	private String login;


	private String password;

	
	ArrayList<GrantedAuthority> roles;

	public User() {
	}

	public User(User user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.login = user.getLogin();
		this.password = user.getPassword();
		this.roles = user.getRoles();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<GrantedAuthority> authList) {
		this.roles = authList;
	}

}
