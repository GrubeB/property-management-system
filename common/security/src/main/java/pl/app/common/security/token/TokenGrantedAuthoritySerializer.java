package pl.app.common.security.token;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface TokenGrantedAuthoritySerializer {
    Object serialize(Collection<? extends GrantedAuthority> authorities);

    List<? extends GrantedAuthority> deserialize(Object object);
}
