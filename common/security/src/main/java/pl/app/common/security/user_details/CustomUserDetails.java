package pl.app.common.security.user_details;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;

@Getter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final UUID userId;

    public CustomUserDetails(UUID userId, String email, List<? extends GrantedAuthority> authorities) {
        super(email, "", authorities);
        this.userId = userId;
    }

    public CustomUserDetails(UUID userId, String email, String password, List<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.userId = userId;
    }
}
