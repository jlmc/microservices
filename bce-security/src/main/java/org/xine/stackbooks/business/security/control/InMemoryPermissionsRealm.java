package org.xine.stackbooks.business.security.control;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

import org.xine.stackbooks.business.security.entity.Permission;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class InMemoryPermissionsRealm {

    private Map<String, EnumSet<Permission>> customStore;

    @PostConstruct
    public void populateRealm() {
        this.customStore = new HashMap<String, EnumSet<Permission>>();
        this.customStore.put("costajlmpp@gmail.com", EnumSet.allOf(Permission.class));
        this.customStore.put("john", EnumSet.allOf(Permission.class));
        this.customStore.put("blogger", EnumSet.noneOf(Permission.class));
    }

    public EnumSet<Permission> getPermissionForPrincipal(String userName) {
        final EnumSet<Permission> configuredPermissions = this.customStore.get(userName);
        if (configuredPermissions == null) {
            return EnumSet.noneOf(Permission.class);
        }

        return configuredPermissions;
    }
}
