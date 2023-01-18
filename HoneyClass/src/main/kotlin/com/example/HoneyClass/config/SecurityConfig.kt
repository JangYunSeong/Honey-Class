package com.example.HoneyClass.config

import com.example.HoneyClass.config.jwt.JwtAuthenticationFilter
import com.example.HoneyClass.config.jwt.JwtAuthorizationFilter
import com.example.HoneyClass.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.filter.CorsFilter


@Configuration
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter(){

    @Autowired
    private lateinit var corsFilter: CorsFilter
    @Autowired
    private lateinit var userRepository: UserRepository

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

//    @Bean
//    fun objectMapper(): ObjectMapper {
//        val objectMapper = ObjectMapper()
//        objectMapper.registerKotlinModule()
//        return objectMapper
//    }

    @Bean
    fun myAuthenticationManager(): AuthenticationManager {
        return authenticationManager()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity){
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다
            .and()
            .formLogin().disable() //jwt 서버 사용하기때문에 form tag를 만들어 Login 하지 않음.
            .httpBasic().disable()
            .addFilter(corsFilter) //모든 요청은 이 필터를 탐
                ///login default login path
            .addFilter(JwtAuthenticationFilter(authenticationManager()))
            .addFilter(JwtAuthorizationFilter(authenticationManager(), userRepository))
            .authorizeRequests()
            .antMatchers("/api/**")
            .access("hasRole('ROLE_USER')")
            .anyRequest().permitAll()
    }
}