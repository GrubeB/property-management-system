package pl.app.common.security.authorozation_method_security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import pl.app.common.shared.authorization.PermissionDomainObjectType;

import java.io.Serializable;


public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;

    private Object returnObject;

    private Object target;

    CustomMethodSecurityExpressionRoot(Authentication a) {
        super(a);
    }

    public boolean hasRootPermission(Object permission) {
        return hasPermission(null, PermissionDomainObjectType.ROOT.name(), permission);
    }

    public boolean hasOrganizationPermission(Serializable targetId, Object permission) {
        return hasPermission(targetId, PermissionDomainObjectType.ORGANIZATION.name(), permission);
    }

    public boolean hasPropertyPermission(Serializable targetId, Object permission) {
        return hasPermission(targetId, PermissionDomainObjectType.PROPERTY.name(), permission);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    public void setThis(Object target) {
        this.target = target;
    }

    @Override
    public Object getThis() {
        return this.target;
    }

}