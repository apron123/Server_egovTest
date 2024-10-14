package egovframework.com.jwt;

import egovframework.cbm.web.common.service.VisitorLogService;
import egovframework.cbm.web.user.service.RoleMappingService;
import egovframework.cbm.web.user.service.UserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private EgovJwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserInfoService userDetailsService;

    @Autowired
    private RoleMappingService roleMappingService;

    @Autowired
    private VisitorLogService visitorLogService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(true);

        String userId = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);

            try {
                userId = jwtTokenUtil.extractUsername(jwt);
                if (userId == null) {
                    logger.debug("jwtToken not validate");
                }
                logger.debug("===>>> id = " + userId);
                setVisitorLog(request, session, userId);
            } catch (Exception e) {
                logger.debug("Unable to verify JWT Token: " + e.getMessage());
                response.sendError(401);
            }

        } else {
            /* 로그인 안되어 있을 시 DB에서 가져온 모든 URL 막기 */
            boolean checkYn = roleMappingService.urlList(requestURI);
            if (!checkYn) {
                response.sendError(403);
            }
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.getUserInfoById(userId);

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userDetails.getUserRole())));
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                /*
                 * role mapping으로 뒤져서 맞지 않으면 에러 보내기
                 */

                boolean checkYn = roleMappingService.urlAllList(requestURI, userDetails.getUserRole());

                if (!checkYn) {
                    response.sendError(403);
                }

            } else {
                logger.debug("Unable to verify JWT Token: " + "존재하지 않는 유저");
            }
        }

        chain.doFilter(request, response);
    }

    /*
    * 방문자 카운트 로직
    */
    private void setVisitorLog(HttpServletRequest request, HttpSession session, String userId) {
        if (session.getAttribute("visited") == null) {
            visitorLogService.createVstrLog(userId, request, session);
            session.setMaxInactiveInterval(5 * 60);
            session.setAttribute("visited", true);
        }
    }

}
