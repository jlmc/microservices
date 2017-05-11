package org.xine.stackbooks.business.security.control;

import java.security.Principal;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.xine.stackbooks.business.security.entity.User;

public class UserProvider {

	@Inject
	Principal principal;

	@Inject
	InMemoryPermissionsRealm realm;

	@Produces
	public User fetch() {
		final User user = new User(this.principal.getName());
		// we can map the permissions over here
		user.setPermissions(this.realm.getPermissionForPrincipal(this.principal.getName()));
		return user;
	}

}
