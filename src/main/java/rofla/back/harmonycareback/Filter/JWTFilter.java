package rofla.back.harmonycareback.Filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import rofla.back.harmonycareback.Dto.CustomUserDetails;
import rofla.back.harmonycareback.Jwt.JWTUtil;
import rofla.back.harmonycareback.Model.Member;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {


    private final JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        //토큰이 없으면 다음 필터로 넘김
        if (accessToken == null) {
            filterChain.doFilter(request, response);

            return;
        }

        //토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            //response body

            PrintWriter writer = response.getWriter();
            writer.println("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
            // 토큰 유무 검사와 달리 만료시에는 응답을 넘기지 않고 바로 상태코드 반환
        }

        //토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.println("invaild access token");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        // 여기 까지 오면 토큰 검증 완료

        //username, role 값을 획득 - 임시 세션 작성 부분
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        Member userEntity = new Member();
        userEntity.setUsername(username);
        userEntity.setRole(role);
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request,response);

    }
}
