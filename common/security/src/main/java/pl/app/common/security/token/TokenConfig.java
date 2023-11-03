package pl.app.common.security.token;

import org.springframework.context.annotation.Bean;

public class TokenConfig {
    @Bean
    public TokenService tokenService() {
        TokenGrantedAuthoritySerializer converter = new TokenDomainObjectGrantedAuthoritySerializer();
        return new TokenServiceImpl(converter);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(TokenService tokenService) {
        return new JwtAuthenticationFilter(tokenService);
    }
}
