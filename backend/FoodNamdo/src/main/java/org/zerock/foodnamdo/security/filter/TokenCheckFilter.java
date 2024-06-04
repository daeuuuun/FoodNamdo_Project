package org.zerock.foodnamdo.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.repository.UserManagementRepository;
import org.zerock.foodnamdo.security.service.APIUserDetailsService;
import org.zerock.foodnamdo.security.exception.AccessTokenException;
import org.zerock.foodnamdo.util.JWTUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
//    private final UserDetailsService userDetailsService;
    private final APIUserDetailsService apiUserDetailsService;
    private final UserManagementRepository userManagementRepository;
//    private final UserManagementService userManagementService;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        String path = request.getRequestURI();

//        if(!path.startsWith("/api/")) {
//        if(!path.startsWith("/usermanagement/")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // 필터를 적용할 경로들
        List<String> filterPaths = Arrays.asList("/usermanagement/deleteUser",
//                "/mainsystem/**",
                "/mainsystem/verifyReview",
                "/mainsystem/uploadReviewImage",
                "/mainsystem/reactionReview",
                "/mainsystem/modifyReview",
                "/mainsystem/deleteReview",
                "/mainsystem/verifyReview",
                "/mainsystem/RstrFavoriteRegister",
                "/mainsystem/ReviewRegister",
                "/mainsystem/mainRstr",
                "/mainsystem/RstrDetail",
                "/mypage/**");
//        List<String> filterPaths = Arrays.asList("/usermanagement/deleteUser", "/mainsystem/**", "/mypage/myInfo", "/mypage/getFavoriteRstr");

        // 필터를 적용할 경로 검사
//        boolean isFiltered = filterPaths.stream().anyMatch(path::startsWith);
        boolean isFiltered = filterPaths.stream().anyMatch(p -> antPathMatcher.match(p, path));


        if (!isFiltered) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Token Check Filter......................");
        log.info("JWTUtil: " + jwtUtil);

        try {
//            validateAccessToken(request);

            // Use validateAccessToken method to get claims
            Map<String, Object> claims = validateAccessToken(request);
            setAuthentication(claims);
            filterChain.doFilter(request, response);
        } catch (AccessTokenException accessTokenException) {
            accessTokenException.sendResponseError(response);
        }
    }

    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
        String headerStr = request.getHeader("Authorization");

        if(headerStr == null || headerStr.length() < 8) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        String tokenType = headerStr.substring(0, 6);
        String tokenStr = headerStr.substring(7);

        if(tokenType.equalsIgnoreCase("Bearer") == false) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        try {
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            return values;
        } catch (MalformedJwtException malformedJwtException) {
            log.error("MalformedJwtException------------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        } catch (SignatureException signatureException) {
            log.error("SignatureException---------------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("ExpiredJwtException---------------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }

//    private void setAuthentication(Map<String, Object> claims) {
//        String userId = String.valueOf(claims.get("userId"));
//        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
//
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//    private void setAuthentication(Claims claims) {
//        String accountId = claims.getSubject();  // `sub` claim contains accountId
//        log.info("claims Line 119");
//        log.info(claims);
//        UserDetails userDetails = apiUserDetailsService.loadUserByUsername(accountId);
//
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//    private void setAuthentication(Map<String, Object> claims) {
//        Long userId = ((Number) claims.get("userId")).longValue();
//        log.debug("Extracted userId from JWT Claims: " + userId);  // Add this line to log userId
//        UserDetails userDetails = apiUserDetailsService.loadUserByUsername(userId.toString());
//
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//    private void setAuthentication(Map<String, Object> claims) {
//        Long userId = ((Number) claims.get("userId")).longValue();  // claims 맵에서 userId를 추출합니다.
//        log.debug("Extracted userId from JWT Claims: " + userId);  // userId를 로그에 출력합니다.
//
//        UserDetails userDetails = apiUserDetailsService.loadUserByUsername(userId.toString());
//        // UserDetailsService를 사용하여 userId로 사용자 세부 정보를 로드합니다.
//
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        // 생성된 Authentication 객체를 SecurityContextHolder의 현재 보안 컨텍스트에 설정합니다.
//    }

    private void setAuthentication(Map<String, Object> claims) {
        Long userId = ((Number) claims.get("userId")).longValue();
        log.debug("Extracted userId from JWT Claims: " + userId);  // userId를 로그에 출력합니다.

        // userId를 사용하여 UserEntity를 로드하고, accountId를 가져옵니다.
        UserEntity userEntity = userManagementRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find userId: " + userId));

        String accountId = userEntity.getAccountId();
        UserDetails userDetails = apiUserDetailsService.loadUserByUsername(accountId);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
