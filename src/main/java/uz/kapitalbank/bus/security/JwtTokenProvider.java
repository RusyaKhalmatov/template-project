package uz.kapitalbank.bus.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import uz.kapitalbank.bus.exceptions.JwtAuthenticationException;
import uz.kapitalbank.bus.user.User;
import uz.kapitalbank.bus.user.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rustam Khalmatov
 */

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.validity}")
    private long validityInMlSeconds;
    @Value("${jwt.header}")
    private String authorization;
    @Value("${jwt.refreshTokenExpires}")
    private long refreshValidityInMiliseconds;
    private final UserService userService;

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserService userService,
                            @Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role, Long id, String salt){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        claims.put("id", id);
        claims.put("salt", salt);
        Date now  = new Date();
        Date validity = new Date(now.getTime() + this.validityInMlSeconds * 1000 * 72); // 3 days

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
    }

//    public boolean validateToken(String token) throws JwtAuthenticationException {
//        try {
//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
//            return !claimsJws.getBody().getExpiration().before(new Date());
//        }catch(JwtException | IllegalArgumentException e)
//        {
////            throw new JwtAuthenticationException("Jwt token is expired or invalid", HttpStatus.UNAUTHORIZED);
//            return false;
//        }
//    }
    public boolean validateToken(String token) throws JwtAuthenticationException {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
            Integer userId = (Integer) claimsJws.getBody().get("id");
            Optional<User> userOptional = userService.findById(userId.longValue());
            if(userOptional.isEmpty())
                return false;
            String salt = (String) claimsJws.getBody().get("salt");
            if(!salt.equals(userOptional.get().getSalt()))
                return false;

            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch(JwtException | IllegalArgumentException e)
        {
//            throw new JwtAuthenticationException("Jwt token is expired or invalid", HttpStatus.UNAUTHORIZED);
            return false;
        }
    }


    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }


    public String getUsername(String token)
    {
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody().getSubject(); // subject - username that we entered in cof file
    }

    public String getUserIdFromToken(String token ){
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody().getSubject(); //
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(this.authorization);
    }

    public String generateRefreshToken(Long id, String username,String salt){
        Claims claims = Jwts.claims().setSubject(String.valueOf(id));
        claims.put("username", username);
        claims.put("id", id);
        claims.put("salt", salt);
        Date now  = new Date();
        Date validity = new Date(now.getTime() + this.refreshValidityInMiliseconds * 1000 * 8760); // 1 year

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
    }

    public String generateTmpToken(){
        return UUID.randomUUID().toString();
    }
}
