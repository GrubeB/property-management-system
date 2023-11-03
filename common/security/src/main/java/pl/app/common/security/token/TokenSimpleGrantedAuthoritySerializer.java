package pl.app.common.security.token;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TokenSimpleGrantedAuthoritySerializer implements TokenGrantedAuthoritySerializer {
    @Override
    public String serialize(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<? extends GrantedAuthority> deserialize(Object object) {
        if (object instanceof String authoritiesString) {
            return Arrays.stream(authoritiesString.split(","))
                    .filter(Predicate.not(String::isBlank))
                    .map(SimpleGrantedAuthority::new).toList();
        } else {
            return new ArrayList<>();
        }
    }
}
