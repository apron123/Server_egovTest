package egovframework.com.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final SerDeMapper mapper;
    private final EgovMessageSource messageSource;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        ResponseVO<String> responseVO = new ResponseVO<>(403, messageSource.getMessage("fail.jwt.forbidden"),
                "");

        log.error("accessDeniedException: ", accessDeniedException);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(mapper.toJson(responseVO));
    }

}