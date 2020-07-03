package com.project.shop.config.security;

import com.project.shop.config.security.handler.*;
import com.project.shop.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Конфигурация Spring Security
 * 1)Form Login
 * 2)Google OAuth2
 * 3)Yandex OAuth2
 *
 * @author Алексей Климов
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${google.id}")
    private String googleClientId;

    @Value("${google.secret}")
    private String googleClientSecret;

    @Value("${yandex.id}")
    private String yandexClientId;

    @Value("${yandex.secret}")
    private String yandexClientSecret;

    @Value("${image.file.path}")
    private String imagePath;

    @Autowired
    private CustomUserService userService;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Конфигурация http запросов
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/registration", "/product", "/product/**", "/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/configuration/ui",
                        "/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/registration/oauth2", true)
                .clientRegistrationRepository(clientRegistrationRepository())
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }

    /**
     * Конфигурация аутентификации
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }


    /**
     * Аутентификация Cross-Origin
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(frontendUrl)
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedHeaders("*");
    }

    /**
     * Репозиторий для хранения конфигурации об OAuth 2 Client
     *
     * @return
     */
    @PostConstruct
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration yandex =
                CustomOauth2Provider.YANDEX.getBuilder("yandex")
                        .clientId(yandexClientId)
                        .clientSecret(yandexClientSecret)
                        .build();
        ClientRegistration google =
                CustomOauth2Provider.GOOGLE.getBuilder("google")
                        .clientId(googleClientId)
                        .clientSecret(googleClientSecret)
                        .build();
        List<ClientRegistration> clientRegistrationList = new ArrayList<>();
        clientRegistrationList.add(yandex);
        clientRegistrationList.add(google);
        return new InMemoryClientRegistrationRepository(clientRegistrationList);
    }

    /**
     * Сервис для OAuth 2 Client
     *
     * @return
     */
    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService() {
        return new
                InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    /**
     * Конфигурация ресурса изображений
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + imagePath + "///");
    }
}
