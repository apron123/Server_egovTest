package egovframework.cbm.config.security;

import egovframework.cbm.web.user.service.RoleMappingService;
import egovframework.com.jwt.JwtAccessDeniedHandler;
import egovframework.com.jwt.JwtAuthenticationEntryPoint;
import egovframework.com.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public JwtRequestFilter jwtRequestFilter() {
                return new JwtRequestFilter();
        }

        @Autowired
        private JwtAccessDeniedHandler jwtAccessDeniedHandler;

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        @Autowired
        private RoleMappingService roleMappingService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                // updateAuthorization(http); // 권한 동적

                http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authz -> {

                        authz.anyRequest().permitAll();
                }).exceptionHandling(exceptionHandling -> exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler))
                                .sessionManagement(sessionManagement -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                http.addFilterBefore(jwtRequestFilter(),
                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        public void updateAuthorization(HttpSecurity http) throws Exception {
                // DB에서 최신 권한 매핑 정보 가져오기
                Map<String, List<String>> roleList = roleMappingService.getUrlToRoleMappings();

                http.authorizeHttpRequests(authz -> {

                        for (Map.Entry<String, List<String>> role : roleList.entrySet()) {

                                String roleNm = role.getKey();
                                List<String> roles = role.getValue();
                                String[] roleArray = roles.toArray(new String[0]);

                                authz.requestMatchers(roleArray)
                                                .hasAnyRole(roleNm, "MNG_CD");
                        }

                });
        }

}
