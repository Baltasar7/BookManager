package com.example.demo.login.domain.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

	  private final User user;
	  private final ArrayList<SimpleGrantedAuthority> grantedAuthorityList;

	  static ArrayList<SimpleGrantedAuthority> generateGrantedAuthorityList(String role) {
	  	ArrayList<SimpleGrantedAuthority> grantedAuthorityList;
	  	grantedAuthorityList = new ArrayList<>();
	  	grantedAuthorityList.add(new SimpleGrantedAuthority(role));
	  	return grantedAuthorityList;
	  }

	  public UserDetailsImpl(User user) {
	  	this.user = user;
	  	grantedAuthorityList = generateGrantedAuthorityList(user.getRole());
	  }

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return grantedAuthorityList;
		}

		@Override
		public String getPassword() {
			return user.getPassword();
		}

		@Override
		public String getUsername() {
			return user.getUserId();
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

		public String getName() {
			return user.getUserName();
		}

}
