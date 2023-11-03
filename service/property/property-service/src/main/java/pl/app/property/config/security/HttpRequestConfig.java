package pl.app.property.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pl.app.common.security.authorization.AuthorizeHttpRequestCustomizer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class HttpRequestConfig {
    private RequestMatcher or(HttpMethod... methods) {
        List<RequestMatcher> methodMatchers = Stream.of(methods).
                map(AntPathRequestMatcher::antMatcher)
                .collect(Collectors.toList());
        if (methodMatchers.size() == 1) {
            return methodMatchers.get(0);
        } else {
            return new OrRequestMatcher(methodMatchers);
        }
    }

    private RequestMatcher or(String... patterns) {
        List<RequestMatcher> patternMatchers = Stream.of(patterns).
                map(AntPathRequestMatcher::antMatcher)
                .collect(Collectors.toList());
        if (patternMatchers.size() == 1) {
            return patternMatchers.get(0);
        } else {
            return new OrRequestMatcher(patternMatchers);
        }
    }

    private RequestMatcher and(RequestMatcher... requestMatchers) {
        return new AndRequestMatcher(requestMatchers);
    }

    private static final String byId = "/{id}";
    private static final String all = "/**";

    @Bean
    AuthorizeHttpRequestCustomizer authorizeHttpRequestCustomizer() {
        return c -> c
                .anyRequest().permitAll();
        // AUTH
//                .requestMatchers(AuthenticationController.controllerPath).permitAll()
//
//                // USER
//                // TODO temporary, must be change!
//                .requestMatchers(and(or(GET), or(UserQueryController.resourcePath, UserQueryController.resourcePath + byId))).permitAll()
//                .requestMatchers(and(or(GET), or(UserByOrganizationQueryController.resourcePath, UserByOrganizationQueryController.resourcePath + byId))).permitAll()
//                .requestMatchers(and(or(POST, PUT, DELETE), or(UserController.resourcePath, UserController.resourcePath + all))).permitAll()
//
//                // GUEST
//                .requestMatchers(and(or(GET), or(GuestQueryController.resourcePath, GuestQueryController.resourcePath + byId))).access(
//                        hasRootPermission(PermissionName.GUEST_READ)
//                )
//                .requestMatchers(and(or(GET), or(GuestByOrganizationQueryController.resourcePath, GuestByOrganizationQueryController.resourcePath + byId))).access(
//                        anyOf(hasRootPermission(PermissionName.GUEST_READ),
//                                hasOrganizationPermission(PermissionName.GUEST_READ))
//                ).requestMatchers(and(or(GET), or(GuestByOrganizationAndPropertyQueryController.resourcePath, GuestByOrganizationAndPropertyQueryController.resourcePath + byId))).access(
//                        anyOf(hasRootPermission(PermissionName.GUEST_READ),
//                                hasOrganizationPermission(PermissionName.GUEST_READ),
//                                hasPropertyPermission(PermissionName.GUEST_READ))
//                ).requestMatchers(and(or(POST, PUT, DELETE), or(GuestController.resourcePath))).access(
//                        anyOf(hasRootPermission(PermissionName.GUEST_WRITE),
//                                hasOrganizationPermission(PermissionName.GUEST_WRITE),
//                                hasPropertyPermission(PermissionName.GUEST_WRITE))
//                )
//
//                // OTHER
//                .anyRequest().authenticated();
    }
}
