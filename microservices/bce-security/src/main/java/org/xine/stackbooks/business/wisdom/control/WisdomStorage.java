package org.xine.stackbooks.business.wisdom.control;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
@DeclareRoles("ADMIN")
public class WisdomStorage {

	private String wisdom;

	@Inject
	Principal principal;

	@PostConstruct
	public void initialize() {
		this.wisdom = "java EE Rosks !!!";
	}

	@RolesAllowed("ADMIN")
	public void wisdom(String wisdom) {
		this.wisdom = wisdom;
	}

	@PermitAll
	public String wisdom() {
		return this.wisdom;
	}

}
