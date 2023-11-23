package pl.app.common.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import pl.app.common.security.user_details.CustomUserDetails;
import pl.app.common.security.user_details.SessionDetails;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

class TokenServiceImpl implements TokenService {
    private static final String SECRET_KEY = "SNP76YLboembarrNS2nV1hgTRGaOHabXP5RuLfiaPPpgXvaHtDAgSAaQ7N3";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID_KEY = "user_id";
    private static final String ACCESS_TOKEN = "access_token";

    private final TokenGrantedAuthoritySerializer tokenGrantedAuthoritySerializer;

    public TokenServiceImpl(TokenGrantedAuthoritySerializer tokenGrantedAuthoritySerializer) {
        this.tokenGrantedAuthoritySerializer = tokenGrantedAuthoritySerializer;
    }

    @Override
    public String generateToken(Authentication authentication) {
        Map<String, Object> extraClaims = new HashMap<>();
        if (authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            extraClaims.put(USER_ID_KEY, customUserDetails.getUserId());
        }
        extraClaims.put(AUTHORITIES_KEY, tokenGrantedAuthoritySerializer.serialize(authentication.getAuthorities()));
        return generateToken(authentication, extraClaims);
    }

    @Override
    public String generateToken(Authentication authentication, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(authentication.getName()) // TO CHECK
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    @Override
    public Authentication extractAuthentication(String token) {
        try {
            final String userEmail = extractClaim(token, Claims::getSubject);
            final String userId = extractClaim(token, claims -> claims.get(USER_ID_KEY, String.class));
            Object authoritiesObject = extractClaim(token, claims -> claims.get(AUTHORITIES_KEY, Object.class));
            final List<? extends GrantedAuthority> authorities = tokenGrantedAuthoritySerializer.deserialize(authoritiesObject);
            final CustomUserDetails userDetails = new CustomUserDetails(UUID.fromString(userId), userEmail, authorities);
            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authenticationToken.setDetails(new SessionDetails(Collections.singletonMap(ACCESS_TOKEN, token)));
            return authenticationToken;
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String resolveBearerTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
