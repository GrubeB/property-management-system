package pl.app.common.security.authorozation_method_security;


import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
public class MethodSecurityConfig {

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler handler = new CustomMethodSecurityExpressionHandler();
        handler.setTrustResolver(new AuthenticationTrustResolverImpl());
        handler.setRoleHierarchy(roleHierarchy);
        handler.setPermissionEvaluator(customPermissionEvaluator());
        return handler;
    }

    @Bean
    static CustomPermissionEvaluator customPermissionEvaluator() {
        return new CustomPermissionEvaluator();
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        return new RoleHierarchyImpl();
    }
}
