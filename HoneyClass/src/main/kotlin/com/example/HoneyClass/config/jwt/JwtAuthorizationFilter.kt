package com.example.HoneyClass.config.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.example.HoneyClass.config.auth.PrincipalDetails
import com.example.HoneyClass.repository.UserRepository
import com.example.HoneyClass.response.UserApiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.lang.RuntimeException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

// 시큐리티가 filter를 가지고 있는데 그 필터 중에 BasicAuthenticationFilter 라는 것이 있음.
// 권한이나 인증이 필요 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음.
// 만약에 권한 인증이 필요한 주소가 아니라면 이 필터를 안 탐.
class JwtAuthorizationFilter(private val myAuthenticationManager: AuthenticationManager,
                             private val userRepository: UserRepository): BasicAuthenticationFilter(myAuthenticationManager) {



    // 인증이나 권한이 필요한 주소요청이 있을 때 이 필터를 타게 됨.
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        println("인증이나 권한이 필요한 주소 요청이 됨")

        val jwtHeader = request.getHeader("Authorization")
        println("jwtHeader : $jwtHeader")

        // header 가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response)
            return
        }

        // JWT 토큰을 검증해서 정상적인 사용자인지 확인
        val jwtToken = request.getHeader("Authorization").replace("Bearer ", "")

        try {
            val studentId = JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken).getClaim("studentId").asString()
            println("studentId $studentId")
            if (studentId != null) {
                val userEntity = userRepository.findByStudentId(studentId) ?: throw RuntimeException("존재하지 않는 ID입니다.")
                val principalDetails = PrincipalDetails(userEntity)
                //Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만듦.
                val authentication = UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.authorities)
                //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
                SecurityContextHolder.getContext().authentication = authentication
                chain.doFilter(request, response)
            }
        } catch (e: TokenExpiredException) {
            response.writer.write(ObjectMapper().writeValueAsString(UserApiResponse(status = HttpServletResponse.SC_BAD_REQUEST, message = "Token is expired")))
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.status = HttpServletResponse.SC_BAD_REQUEST
        } catch(e: JWTVerificationException) {
            response.writer.write(ObjectMapper().writeValueAsString(UserApiResponse(status = HttpServletResponse.SC_BAD_REQUEST, message = "Token is not valid")))
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = MediaType.APPLICATION_JSON_VALUE
        }
    }
}