package pl.app.property.user.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.app.common.security.authorozation_method_security.DomainObjectGrandAuthority;
import pl.app.common.shared.authorization.PermissionDomainObjectType;
import pl.app.common.shared.authorization.PermissionName;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.organization.service.OrganizationService;
import pl.app.property.user.adapter.out.persistence.model.UserEntity;
import pl.app.property.user.adapter.out.query.UserQueryService;
import pl.app.property.user.application.port.in.AddOrganizationToUserCommand;
import pl.app.property.user.application.port.in.CreateUserCommand;
import pl.app.property.user.application.port.in.CreateUserUseCase;

import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerByOrganizationIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    CreateUserUseCase createUserUseCase;
    @Autowired
    UserQueryService userQueryService;
    @Autowired
    ObjectMapper objectMapper;

    private OrganizationEntity organization;
    private UserEntity user;

    @BeforeEach
    void beforeAll() {
        organization = organizationService.create(OrganizationEntity.builder()
                .name("name")
                .organizationId(UUID.randomUUID())
                .build());
        UUID userId = createUserUseCase.createUser(new CreateUserCommand("ala@ma.kota", "password"));
        user = userQueryService.fetchById(userId);
    }

    @Test
    void whenValidCommandWithUserAuthentication_thenShouldAddUserToOrganization() throws Exception {
        AddOrganizationToUserCommand command = new AddOrganizationToUserCommand(user.getUserId(), organization.getOrganizationId());

        mockMvc.perform(post(UserByOrganizationController.resourcePath + UserByOrganizationController.addOrganizationToUserPath, organization.getOrganizationId())
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(user.getEmail()).password(user.getPassword()).authorities(List.of(
                                new DomainObjectGrandAuthority(
                                        organization.getOrganizationId().toString(),
                                        PermissionDomainObjectType.ORGANIZATION.name(),
                                        PermissionName.USER_WRITE.name())))
                        ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void whenValidCommandWithoutUserAuthentication_thenOperationShouldBeForbidden() throws Exception {
        AddOrganizationToUserCommand command = new AddOrganizationToUserCommand(user.getUserId(), organization.getOrganizationId());

        mockMvc.perform(post(UserByOrganizationController.resourcePath + UserByOrganizationController.addOrganizationToUserPath, organization.getOrganizationId())
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(user.getEmail()).password(user.getPassword()).authorities(List.of(
                                new DomainObjectGrandAuthority(
                                        UUID.randomUUID().toString(),
                                        PermissionDomainObjectType.ORGANIZATION.name(),
                                        PermissionName.USER_WRITE.name())))
                        ))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}