package org.xine.stackbooks.business.security.entity;

import java.util.EnumSet;
import java.util.Set;

public class User {

	private final String username;
	private final Set<Permission> permissions;

	public User(String username) {
		this.permissions = EnumSet.noneOf(Permission.class);
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void addRole(Permission permission) {
		this.permissions.add(permission);
	}

	public boolean isAllowed(Permission permission) {
		return this.permissions.contains(permission);
	}

	public void setPermissions(EnumSet<Permission> permissionForPrincipal) {
		this.permissions.clear();
		this.permissions.addAll(permissionForPrincipal);
	}

	@Override
	public String toString() {
		return "User [username=" + this.username + ", permissions=" + this.permissions + "]";
	}

}
