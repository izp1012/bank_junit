package shop.mtcoding.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.http.client.HttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.util.CustomResponseUtil;

@Configuration  //IoC 컨테이너에 BCryptPasswordEncoder() 객체가 등록됨
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final HttpClientProperties httpClientProperties;

    public SecurityConfig(HttpClientProperties httpClientProperties) {
        this.httpClientProperties = httpClientProperties;
    }
    
    // JWT 필터 등록이 필요함


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.headers().frameOptions().disable();    //iframe 허용안함
//        http.csrf().disable();  //enable 이면 post 맨 작동안함 (메타코딩 유튜브에 시큐리티 강의)
//        http.cors().configurationSource(null);
//
//        //jSessionId 를 서버쪽에서 관리안하겠다는 뜻
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        // react, 앱으로 요청할 예정
//        http.formLogin().disable();
//        // httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다.
//        http.httpBasic().disable();
//
//        //Exception 가로채기
//        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
//            ObjectMapper om  = new ObjectMapper();
//            ResponseDto responseDto = new ResponseDto(-1, "권한없음", null);
//            String responseBody = om,.writeValueAsString(responseDto);
//
//            response.setContentType("application/json; charset=utf-8");
//            response.setStatus(403);
//            response.getWriter().println("error");
//    });
//
//        http.authorizeHttpRequests()
//                .requestMatchers("/api/s/**").authenticated()
//                .requestMatchers("api/admin/**").hasRole("" + UserEnum.ADMIN)
//                .requestMatchers().permitAll();
//
//        return http.build();
//    }
    
    
    // JWT 서버를 만들 예정!! Session 사용안함.
    //위의 method 는 Spring Security 6.1 버전부터는 지원하지않아 아래방식으로 변경
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : filterChain 빈 등록됨");
        // HTTP 헤더 설정 (iframe 허용 안 함)
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        // CSRF 비활성화
        http.csrf(csrf -> csrf.disable());

        // CORS 설정 (필요 시 세부 설정 추가 가능)
        http.cors(cors -> cors.configurationSource(corsConfiguration()));

        // 세션 관리 설정 (JWT 사용, STATELESS)
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 폼 로그인 비활성화
        http.formLogin(formLogin -> formLogin.disable());

        // HTTP Basic 인증 비활성화
        http.httpBasic(httpBasic -> httpBasic.disable());



        http.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                    CustomResponseUtil.unAuthentication(response, "로그인을 진행해주세요");
                })
        );

        // 권한 및 요청 경로 매핑
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/s/**").authenticated() // 인증 필요
                .requestMatchers("/api/admin/**").hasRole(UserEnum.ADMIN.name()) // ADMIN 역할 필요
                .anyRequest().permitAll() // 나머지 요청 허용
        );

        return http.build();
    }

    public CorsConfigurationSource corsConfiguration() {
        log.debug("디버그 : filterChain cors 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");    //GET, POST, PUT, DELETE (JavaScript 요청 허용)
        configuration.addAllowedOriginPattern("*"); //모든 IP 주소 허용 (프론트 엔드 IP 만 허용 react)
        configuration.setAllowCredentials(true); //클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //모든 요청에 대해서 위에 정의한 configure 를 셋팅해준다

        return source;
    }
}
