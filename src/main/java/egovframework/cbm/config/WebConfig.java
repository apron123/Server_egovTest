// package egovframework.cbm.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import egovframework.cbm.interceptor.JwtTokenInterceptor;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

// private final JwtTokenInterceptor jwtTokenInterceptor;

// /*
// * URL 필터
// * 필터에 추가된 url은 Token을 확인
// */

// private String[] FILTER_LIST = {
// // "/view/**",
// "/white-list/**",
// };

// /*
// * 예외처리 URL
// * 필터에 들어간 url중 예외처리
// */
// private String[] EXCLUDE_LIST = {
// "/",

// /* TEST */
// "/user/token",

// };

// @Autowired
// public WebConfig(JwtTokenInterceptor jwtTokenInterceptor) {
// this.jwtTokenInterceptor = jwtTokenInterceptor;
// }

// @Override
// public void addInterceptors(InterceptorRegistry registry) {
// registry.addInterceptor(jwtTokenInterceptor)
// .addPathPatterns(FILTER_LIST)
// .excludePathPatterns(EXCLUDE_LIST);

// }

// }