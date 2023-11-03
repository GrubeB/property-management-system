package pl.app.common.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import pl.app.common.security.authorozation_method_security.DomainObjectGrandAuthority;

import java.io.Serializable;
import java.util.*;

public class TokenDomainObjectGrantedAuthoritySerializer implements TokenGrantedAuthoritySerializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object serialize(Collection<? extends GrantedAuthority> authorities) {
        GrandAuthoritiesObject object = new GrandAuthoritiesObject();
        authorities.stream()
                .filter(authority -> authority instanceof DomainObjectGrandAuthority domainObjectGrandAuthority)
                .map(authority -> (DomainObjectGrandAuthority) authority)
                .forEach(object::addPrivilege);
        return object.getPrivilegeGroups();
    }

    @Override
    public List<? extends GrantedAuthority> deserialize(Object object) {
        List<DomainObjectGrandAuthority> authorities = new ArrayList<>();
        if (object instanceof ArrayList<?> list) {
            list.forEach(privilegeGroupObj -> {
                if (privilegeGroupObj instanceof LinkedHashMap privilegeGroupMap) {
                    String level = (String) privilegeGroupMap.get("level");
                    ArrayList<?> privilegesList = (ArrayList<?>) privilegeGroupMap.get("privileges");
                    privilegesList.forEach(privilegeObj -> {
                        if (privilegeObj instanceof LinkedHashMap privilegeMap) {
                            String permissionName = (String) privilegeMap.get("name");
                            Object domainObjectIdsObj = privilegeMap.get("domainObjectIds");
                            if (domainObjectIdsObj instanceof ArrayList<?> domainObjectIds) {
                                domainObjectIds.forEach(id -> authorities.add(new DomainObjectGrandAuthority((String) id, level, permissionName)));
                            }
                        }
                    });
                }
            });
        }
        return authorities;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class GrandAuthoritiesObject implements Serializable {
        private List<PrivilegeGroup> privilegeGroups;

        public GrandAuthoritiesObject() {
            privilegeGroups = new ArrayList<>();
        }

        public void addPrivilege(DomainObjectGrandAuthority authority) {
            Optional<PrivilegeGroup> group = getPrivateGroupByType(authority.getPermissionLevel());
            if (group.isPresent()) {
                group.get().addPrivilege(authority);
            } else {
                PrivilegeGroup newGroup = new PrivilegeGroup(authority.getPermissionLevel());
                newGroup.addPrivilege(authority);
                this.privilegeGroups.add(newGroup);
            }
        }

        private Optional<PrivilegeGroup> getPrivateGroupByType(String permissionLevel) {
            return privilegeGroups.stream().filter(g -> g.level.equals(permissionLevel)).findAny();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class PrivilegeGroup implements Serializable {
        private String level;
        private List<Privilege> privileges;

        public PrivilegeGroup(String level) {
            this.level = level;
            this.privileges = new ArrayList<>();
        }

        public void addPrivilege(DomainObjectGrandAuthority authority) {
            Optional<Privilege> privilege = getPrivilege(authority.getPermissionName());
            if (privilege.isPresent()) {
                privilege.get().addId(authority.getDomainObjectId());
            } else {
                Privilege newPrivilege = new Privilege(authority.getPermissionName());
                newPrivilege.addId(authority.getDomainObjectId());
                this.privileges.add(newPrivilege);
            }
        }

        private Optional<Privilege> getPrivilege(String permissionName) {
            return privileges.stream().filter(p -> p.getName().equals(permissionName)).findAny();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class Privilege implements Serializable {
        private String name;
        private List<String> domainObjectIds;

        public Privilege(String name) {
            this.name = name;
            this.domainObjectIds = new ArrayList<>();
        }

        public void addId(String domainObjectId) {
            this.domainObjectIds.add(domainObjectId);
        }
    }
}
