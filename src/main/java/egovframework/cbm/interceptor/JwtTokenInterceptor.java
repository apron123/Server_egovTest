// package egovframework.cbm.interceptor;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.env.Environment;
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.HandlerInterceptor;

// import egovframework.com.jwt.EgovJwtTokenUtil;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Component
// public class JwtTokenInterceptor implements HandlerInterceptor {

// @Autowired
// EgovJwtTokenUtil jwtTokenUtil;

// @Autowired
// Environment env;

// /*
// * JWT Token을 확인하는 Interceptor
// * 필터 url 요청시
// * request Header 에서 token 유무 확인하여
// * token이 유효하면 controller로 통과
// * token이 유효하지 않으면 error 반환
// */

// @Override
// public boolean preHandle(HttpServletRequest request, HttpServletResponse
// response, Object handler)
// throws Exception {

// String authorizationHeader = request.getHeader("Authorization");

// String accessToken = "";
// if (authorizationHeader != null) {
// // Bearer 접두사를 제거하고 토큰만 추출
// if (authorizationHeader.startsWith("Bearer ")) {
// accessToken = authorizationHeader.substring(7);
// } else {

// accessToken = authorizationHeader;
// }
// }
// String userId = request.getHeader("userId");

// if (jwtTokenUtil.isTokenValidation(accessToken, userId)) {
// // TODO 토큰 유효하지 않으면 예외발생 코드 작성 필요
// }

// log.debug("===============================================");
// log.debug("==================== BEGIN ====================");
// log.debug("Request URI ===> " + request.getRequestURI());
// return HandlerInterceptor.super.preHandle(request, response, handler);
// }

// /*
// * @Override
// * public void postHandle(HttpServletRequest request, HttpServletResponse
// * response, Object handler,
// * ModelAndView modelAndView) throws Exception {
// * log.debug("==================== END ======================");
// * log.debug("===============================================");
// * HandlerInterceptor.super.postHandle(request, response, handler,
// * modelAndView);
// * }
// */
// }
