package com.example.HoneyClass.service

import com.example.HoneyClass.dto.userDto.UserJoinRequestDto
import com.example.HoneyClass.dto.userDto.UserProfileRequestDto
import com.example.HoneyClass.dto.userDto.UserProfileResponseDto
import com.example.HoneyClass.entity.User
import com.example.HoneyClass.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.BadRequest
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*


@Service
@RequiredArgsConstructor
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {


    fun profile(studentId: String): UserProfileResponseDto {
        val user = userRepository.findById(studentId).get()
        println(studentId)
        print("userEntity $user")
        return UserProfileResponseDto(studentId = user.studentId, name = user.name, major = user.major)
    }

    fun join(requestDto: UserJoinRequestDto) {

        val userEntity = userRepository.findByStudentId(requestDto.studentId)
        if (userEntity != null) {
            throw RuntimeException("이미 가입된 ID 입니다")
        }
        val encodingPassword = passwordEncoder.encode(requestDto.password)
        val user= User(requestDto.studentId, requestDto.name, encodingPassword, requestDto.major, "ROLE_USER")
        userRepository.save(user)
    }

    fun userExistence(studentId: String): Boolean {
        userRepository.findByStudentId(studentId) ?: return false
        return true
    }
}