package pl.app.common.security.authorozation_method_security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


@Getter
@Setter
@AllArgsConstructor
public class DomainObjectGrandAuthority implements GrantedAuthority {
    private String domainObjectId;
    private String permissionLevel;
    private String permissionName;

    @Override
    public String getAuthority() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(permissionLevel);
        stringBuilder.append('_');
        stringBuilder.append(permissionName);
        stringBuilder.append('_');
        stringBuilder.append(domainObjectId);
        return stringBuilder.toString();
    }
}

