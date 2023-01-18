package com.example.HoneyClass.config.auth

import com.example.HoneyClass.entity.User
import com.example.HoneyClass.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

//  http://localhost:8080/login => 여기서 동작을 안 한다
@Service
@RequiredArgsConstructor
class PrincipalDetailsService(private val userRepository: UserRepository): UserDetailsService {

    override fun loadUserByUsername(studentId: String): PrincipalDetails? {
        println("PrincipalDetails - loadUserByUsername()")
        println(studentId)
        val userEntity = userRepository.findByStudentId(studentId)?: throw InternalAuthenticationServiceException("The ID does not exist.")
        return PrincipalDetails(userEntity)
    }
}