package com.sparta_a5.ootd.user.jwt;

import com.sparta_a5.ootd.user.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization"; // 헤더의 키값을 Authorization 으로 지정

    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer "; // Bearer는 식별자로서 토큰앞에 붙여서 구분하는 역할

    @Value("${jwt.secret.key}")// 우리만의 키값으로 만들수 있도록 roperties에 있는 secret.key 값을 주입
    private String secretKey;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private Key key; // 암호화된 Key값을 여기에 답아서 Key만들때 마다 사용

    @PostConstruct // 키값을 클래스 Bean이 생성될때 같이 만들어질수 있도록 사용하는 어노테이션
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String resolveToken(HttpServletRequest request) { // 문자열로 Token을 뽑아오기 위해서 메서드(resolveToken) 생성, HttpServletRequest 에서 받아온후
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER); // 토큰을 받아옴
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) { //StringUtils를 사용하여 실제로 bearerToken에 텍스트를 가지고 있는지 확인하고 BEARER_PREFIX로 시작하는 지 확인
            return bearerToken.substring(7); // 확인후 bearerToken 내용을 Bearer글자와 띄어쓰기포함 7글자 부터 지움
        }
        return null;
    }

    public boolean validateToken(String token) { //토큰을 검증하는 메소드, 토큰의 상태에따라 대답
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); // 토큰 동일시 확인 과정
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;

    }

    public Claims getUserInformToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); // 토큰 동일시 확인 과정을 거친후 Claims라는곳에 담겨있는 토큰의 Body부분을 가져온다.

    }

    public String createToken(String username) {
        Date date = new Date(); // 토큰 발급일

        // 토큰 만료시간 60분
        long TOKEN_TIME = 60 * 60 * 1000;
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) //만료시간 설정
                        .setIssuedAt(date) // 생성된 시간
                        .signWith(key, signatureAlgorithm) // 어떤 알고리즘으로 암호화했는가
                        .compact();

    }

    public String getUsernameFromHeader(HttpServletRequest httpServletRequest){
        String resolvedToken = resolveToken(httpServletRequest);
        Claims info = getUserInformToken(resolvedToken);

        return info.getSubject();
    }
}