package com.yo.security;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.yo.model.UserData;

public class CustomUser implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public CustomUser(UserData user) {
		super();
		this.user = user;
	}

	@Autowired
	private UserData user;
	

	  @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));  
	    }


	  @Override
	  public String getPassword() {
	    return user.getPassword();
	  }

	  @Override
	  public String getUsername() {
	    return user.getUsername();
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
