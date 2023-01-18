package com.example.HoneyClass.config.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.example.HoneyClass.config.auth.PrincipalDetails
import com.example.HoneyClass.entity.User
import com.example.HoneyClass.response.UserApiResponse
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
//login 요청해서 username, password 전송하면 (post)
//UsernamePasswordAuthenticationFilter 동작을 함
@RequiredArgsConstructor
class JwtAuthenticationFilter(private val myAuthenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication? {
        println("로그인 시도중")
        // username, password 받아서 정상인지 로그인 시도를 해봄
        // authenticationManager로 로그인 시도를 하면 PrincipalDetailsService 호출 - loadUserByUsername() 함수 실행
        // PrincipalDetails 를 세션에 담고 (권한관리 위함)
        // JWT 토큰을 만들어 응답

        val om = ObjectMapper()
        val user: User = om.readValue(request?.inputStream, User::class.java)
        println("$user")

        val authenticationToken = UsernamePasswordAuthenticationToken(user.studentId, user.password)

        // principalDetailsService 의 loadUserByUsername() 함수가 실행
        // 함수 실행 후 정상이면 (로그인이 된 것) authentication 이 리턴됨
        var authentication: Authentication?
        try {
            authentication = myAuthenticationManager.authenticate(authenticationToken)
            //authentication 객체가 session 영역에 저장됨 => 로그인이 되었다는 뜻
            val principalDetails = authentication.principal as PrincipalDetails
            println("로그인 완료됨 : $principalDetails.username")  //값이 있다는 건 로그인 정상적으로 되었다는 뜻
            println("=====================================================")
            //authentication 객체가 session 영역에 저장해야하고 그 방법이 return
            //리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는 것
            //굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없지만 단지 권한 처리때문에 session에 넣어줌.

        } catch(e: InternalAuthenticationServiceException) {
            response?.writer?.write(ObjectMapper().writeValueAsString(e.message?.let { UserApiResponse(status = HttpServletResponse.SC_BAD_REQUEST, message = it) }))
            response?.contentType = MediaType.APPLICATION_JSON_VALUE
            response?.status = HttpServletResponse.SC_BAD_REQUEST
            authentication = null
        } catch(e: BadCredentialsException) {
            response?.writer?.write(ObjectMapper().writeValueAsString(UserApiResponse(status = HttpServletResponse.SC_BAD_REQUEST, message = "password is not correct")))
            response?.contentType = MediaType.APPLICATION_JSON_VALUE
            response?.status = HttpServletResponse.SC_BAD_REQUEST
            authentication = null
        }
        return authentication

    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨.
    //JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨.
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        println("successfulAuthentication 실행됨 : 인증이 완료되었다는 뜻")
        try {
            val principalDetails = authResult?.principal as PrincipalDetails

            val jwtToken = JWT.create()
                .withSubject("토큰")
                .withExpiresAt(Date(System.currentTimeMillis() + (60000*20)))   //토큰 만료시간 20분
                .withClaim("studentId", principalDetails.getStudentId())
                .withClaim("username", principalDetails.username)
                .sign(Algorithm.HMAC512("cos"))
            response?.addHeader("Authorization", "Bearer $jwtToken")
            response?.writer?.write(ObjectMapper().writeValueAsString(UserApiResponse(status = HttpServletResponse.SC_OK, message = "login Success")))
            response?.contentType = MediaType.APPLICATION_JSON_VALUE
            response?.characterEncoding = "UTF-8"
            response?.status = HttpServletResponse.SC_OK
        } catch (e: TokenExpiredException){
            e.message?.let { response?.writer?.write(it) }
            response?.contentType = MediaType.APPLICATION_JSON_VALUE
            response?.characterEncoding = "UTF-8"
            response?.status = HttpServletResponse.SC_BAD_REQUEST
        }
    }
}