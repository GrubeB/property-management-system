package pl.app.common.security.user_details;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final String userId;

    public CustomUserDetails(String userId, String email, List<? extends GrantedAuthority> authorities) {
        super(email, "", authorities);
        this.userId = userId;
    }

    public CustomUserDetails(String userId, String email, String password, List<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.userId = userId;
    }
}
