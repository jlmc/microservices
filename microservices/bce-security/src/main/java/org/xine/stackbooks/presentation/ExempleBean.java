package org.xine.stackbooks.presentation;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.stackbooks.business.security.entity.User;

@Named
@RequestScoped
public class ExempleBean {

	private final String username = "this will be the username";

	@Inject
	private Principal principal;

	@Inject
	private User myUser;

	public String getUsername() {
		return this.username;
	}

	public String getPrincipalName() {
		return this.principal.getName();
	}

	public String getMyUser() {
		return String.valueOf(this.myUser);
	}

}
