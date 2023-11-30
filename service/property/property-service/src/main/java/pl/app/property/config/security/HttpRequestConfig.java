package pl.app.property.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pl.app.common.security.authorization.AuthorizeHttpRequestCustomizer;
import pl.app.property.auth.controller.AuthenticationController;
import pl.app.property.guest.controller.GuestByOrganizationQueryController;
import pl.app.property.guest.controller.GuestByPropertyQueryController;
import pl.app.property.guest.controller.GuestController;
import pl.app.property.guest.controller.GuestQueryController;
import pl.app.property.organization.controller.OrganizationController;
import pl.app.property.organization.controller.OrganizationQueryController;
import pl.app.property.property.controller.PropertyByOrganizationQueryController;
import pl.app.property.property.controller.PropertyController;
import pl.app.property.property.controller.PropertyQueryController;
import pl.app.property.user.adapter.in.UserByOrganizationController;
import pl.app.property.user.adapter.in.UserByOrganizationQueryController;
import pl.app.property.user.adapter.in.UserController;
import pl.app.property.user.adapter.in.UserQueryController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpMethod.*;
import static pl.app.common.security.authorization.CustomAuthorizationManager.*;
import static pl.app.common.shared.authorization.PermissionName.*;

@Configuration
public class HttpRequestConfig {

    private static final String byId = "/{id}";
    private static final String all = "/**";

    @Bean
    AuthorizeHttpRequestCustomizer authorizeHttpRequestCustomizer() {
        return c -> c
                // AUTH
                .requestMatchers(AuthenticationController.controllerPath).permitAll()

                // USER
                .requestMatchers(and(or(GET), or(UserQueryController.resourcePath, UserQueryController.resourcePath + byId))).access(
                        hasRootPermission(USER_READ)
                )
                .requestMatchers(and(or(GET), or(UserByOrganizationQueryController.resourcePath, UserByOrganizationQueryController.resourcePath + byId))).access(
                        anyOf(
                                hasRootPermission(USER_READ),
                                hasOrganizationPermission(USER_READ)
                        )
                )
                .requestMatchers(and(or(POST), or(UserController.resourcePath + UserController.createUserPath))).permitAll()
                .requestMatchers(and(or(POST, PUT, DELETE), or(UserByOrganizationController.resourcePath, UserByOrganizationController.resourcePath + all))).access(
                        anyOf(
                                hasRootPermission(USER_WRITE),
                                hasOrganizationPermission(USER_WRITE)
                        )
                )

                // ORGANIZATION
                .requestMatchers(and(or(GET), or(OrganizationQueryController.resourcePath))).access(
                        hasRootPermission(ORGANIZATION_READ)
                )
                .requestMatchers(and(or(GET), or( OrganizationQueryController.resourcePath + byId))).access(
                        anyOf(
                                hasRootPermission(ORGANIZATION_READ),
                                hasOrganizationPermission(ORGANIZATION_READ,"id")
                        )
                )
                .requestMatchers(and(or(POST), or(OrganizationController.resourcePath))).permitAll()
                .requestMatchers(and(or(PUT, DELETE), or(OrganizationController.resourcePath, OrganizationController.resourcePath + all))).access(
                        anyOf(
                                hasRootPermission(ORGANIZATION_WRITE),
                                hasOrganizationPermission(ORGANIZATION_WRITE)
                        )
                )

                // PROPERTY
                .requestMatchers(and(or(GET), or(PropertyQueryController.resourcePath, PropertyQueryController.resourcePath + byId))).access(
                        hasRootPermission(PROPERTY_READ)
                )
                .requestMatchers(and(or(GET), or( PropertyByOrganizationQueryController.resourcePath))).access(
                        anyOf(
                                hasRootPermission(PROPERTY_READ),
                                hasOrganizationPermission(PROPERTY_READ)
                        )
                )
                .requestMatchers(and(or(GET), or(  PropertyByOrganizationQueryController.resourcePath + byId))).access(
                        anyOf(
                                hasRootPermission(PROPERTY_READ),
                                hasOrganizationPermission(PROPERTY_READ),
                                hasPropertyPermission(PROPERTY_READ,"id")
                        )
                )
                .requestMatchers(and(or(POST, PUT, DELETE), or(PropertyController.resourcePath, PropertyController.resourcePath + all))).access(
                        anyOf(
                                hasRootPermission(PROPERTY_WRITE),
                                hasOrganizationPermission(PROPERTY_WRITE)
                        )
                )

                // GUEST
                .requestMatchers(and(or(GET), or(GuestQueryController.resourcePath, GuestQueryController.resourcePath + byId))).access(
                        hasRootPermission(GUEST_READ)
                )
                .requestMatchers(and(or(GET), or(GuestByOrganizationQueryController.resourcePath, GuestByOrganizationQueryController.resourcePath + byId))).access(
                        anyOf(
                                hasRootPermission(GUEST_READ),
                                hasOrganizationPermission(GUEST_READ)
                        )
                ).requestMatchers(and(or(GET), or(GuestByPropertyQueryController.resourcePath, GuestByPropertyQueryController.resourcePath + byId))).access(
                        anyOf(
                                hasRootPermission(GUEST_READ),
                                hasOrganizationPermission(GUEST_READ),
                                hasPropertyPermission(GUEST_READ)
                        )
                ).requestMatchers(and(or(POST, PUT, DELETE), or(GuestController.resourcePath))).access(
                        anyOf(
                                hasRootPermission(GUEST_WRITE),
                                hasOrganizationPermission(GUEST_WRITE),
                                hasPropertyPermission(GUEST_WRITE)
                        )
                )

                // OTHER
                .anyRequest().authenticated();
    }

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
}
