package org.xine.stackbooks.business.security.boundary;

import java.lang.reflect.Method;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.xine.stackbooks.business.security.entity.Permission;
import org.xine.stackbooks.business.security.entity.User;

public class Guard {

    @Inject
    Instance<User> users;

    @AroundInvoke
    public Object validatePermissions(InvocationContext ic) throws Exception {

        final Method method = ic.getMethod();
        final User user = this.users.get();

        if (!isAllowed(user, method)) {
            throw new SecurityException("User " + user + " is not allowed to execute the method " + method);
        }

        return ic.proceed();
    }

    private boolean isAllowed(User user, Method method) {
        final AllowedTo annotation = method.getAnnotation(AllowedTo.class);

        if (annotation == null) {
            return true;
        }

        final Permission[] permissions = annotation.value();
        for (final Permission permission : permissions) {
            if (user.isAllowed(permission)) {
                return true;
            }
        }

        return false;
    }

}
