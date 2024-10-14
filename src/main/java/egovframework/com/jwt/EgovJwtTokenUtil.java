package egovframework.com.jwt;

import egovframework.cbm.web.user.model.entity.UserInfo;
import egovframework.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class EgovJwtTokenUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = -5180902194184255251L;

    public static final long ACCESS_TOKEN_VALIDITY = (60 * 60 * 1000 * 1);
    // public static final long ACCESS_TOKEN_VALIDITY = (long) (60 * 60 * 1000 * 1);
    public static final long REFRESH_TOKEN_VALIDITY = (60 * 60 * 1000 * 12);

    public static final SecretKey sKey = Keys
            .hmacShaKeyFor(Base64.getEncoder().encodeToString(Constants.KEY.getBytes()).getBytes());

    /**
     * Access Token 발급
     * 
     * @param userInfo
     * @return
     */
    public String generateToken(UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userInfoSeq", userInfo.getUserInfoSeq());
        claims.put("userId", userInfo.getUserId());
        claims.put("userNm", userInfo.getUserNm());
        claims.put("useYn", userInfo.getUseYn());
        claims.put("userRole", userInfo.getUserRole());
        String subject = String.valueOf(userInfo.getUserId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(sKey).compact();
    }

    /**
     * Refresh Token 발급
     * 
     * @param userId
     * @return
     */
    public String generateRfToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        String subject = String.valueOf(userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(sKey).compact();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("userId").toString();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(sKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // 토큰에서 아이디 추출
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 토큰에서 만료시간 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 유효시간 체크
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).after(new Date());
    }

    // 토큰이 유효한지 체크
    public boolean isTokenValidation(String token, String uid) {
        String username = extractUsername(token);
        boolean result = isTokenExpired(token);
        return username.equals(uid) && result;
    }
}
