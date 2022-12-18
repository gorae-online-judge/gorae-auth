package ads02.auth.service;

import ads02.auth.exception.GoraeException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Long JWT_ACCESS_TOKEN_TIME;
    private final String JWT_ACCESS_SECRET_KEY;
    private Key accessKey;

    public JwtService(@Value("${jwt.time.access}") Long JWT_ACCESS_TOKEN_TIME,
                      @Value("${jwt.secret.access}") String JWT_ACCESS_SECRET_KEY) {
        this.JWT_ACCESS_TOKEN_TIME = JWT_ACCESS_TOKEN_TIME;
        this.JWT_ACCESS_SECRET_KEY = JWT_ACCESS_SECRET_KEY;
        this.accessKey = Keys.hmacShaKeyFor(JWT_ACCESS_SECRET_KEY.getBytes());
    }

    public String createJwt(String nickname, String bojToken){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("id", nickname)
                .claim("bojToken", bojToken)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JWT_ACCESS_TOKEN_TIME))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Header Authorization에서 JWT 받아온다.
    public String getJwtFromHeader(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        if(authorization!= null)
            authorization.replace("Bearer","");
        return authorization;
    }

    public long getMemberIdFromJwt() throws ExpiredJwtException, GoraeException {
        // 1. 추출
        String accessToken = getJwtFromHeader();
        if (accessToken == null || accessToken.length() == 0){
            throw new GoraeException("토큰이 비어있습니다.");
        }
        // 2. 파싱
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(accessKey).build()
                    .parseClaimsJws(accessToken);
        }catch(ExpiredJwtException e){
            throw new GoraeException("만료된 토큰입니다.");
        }catch(Exception e){
            throw new GoraeException("토큰 값을 확인해주세요.");
        }
        return claims.getBody().get("memberId", Long.class);
    }
}
