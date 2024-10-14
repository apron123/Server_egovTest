package egovframework.com.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import egovframework.cbm.web.common.model.ResponseVO;
import egovframework.cbm.web.common.model.mapper.SerDeMapper;
import egovframework.com.cmm.EgovMessageSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * fileName : JwtAuthenticationEntryPoint
 * author : crlee
 * date : 2023/06/11
 * description :
 * ===========================================================
 * DATE AUTHOR NOTE
 * -----------------------------------------------------------
 * 2023/06/11 crlee 최초 생성
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final SerDeMapper mapper;
    private final EgovMessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        ResponseVO<String> responseVO = new ResponseVO<>(401, messageSource.getMessage("fail.jwt.unauthorized"),
                "");

        log.error("authException: ", authException);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(mapper.toJson(responseVO));

    }

}