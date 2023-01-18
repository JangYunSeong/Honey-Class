package com.example.HoneyClass.controller

import com.example.HoneyClass.dto.userDto.UserJoinRequestDto
import com.example.HoneyClass.dto.userDto.UserProfileRequestDto
import com.example.HoneyClass.dto.userDto.UserProfileResponseDto
import com.example.HoneyClass.response.UserApiResponse
import com.example.HoneyClass.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
class UserController(
    @Autowired val userService: UserService
) {
    @GetMapping("/api/profile")
    fun profile(studentId: String): ResponseEntity<UserApiResponse> {
        val responseDto = userService.profile(studentId)
        return ResponseEntity
            .ok()
            .body(UserApiResponse(status = 200, message = "프로필 조회 완료", data = responseDto))
    }

    @GetMapping("/user-existence")
    fun userExist(studentId: String): ResponseEntity<UserApiResponse> {
        val existence = userService.userExistence(studentId)
        return ResponseEntity
            .ok()
            .body(UserApiResponse(status = 200, message = "회원 존재여부 조회 완료", data = existence))
    }

    /**
     * 기본생성자 ->> 인스턴스 생성 -> setter
     * 모든 필드생성자 -> 바로 필드랑 바인딩해서 값을 넣는데
     * 기본생성자 -> 됐다
     */


    @PostMapping("/join")
    fun join(@RequestBody requestDto: UserJoinRequestDto): ResponseEntity<UserApiResponse> {
        println("requestDto $requestDto")
        try{
            userService.join(requestDto)
        } catch(e: RuntimeException) {
            return ResponseEntity
                .ok()
                .body(UserApiResponse(status = HttpServletResponse.SC_OK, message = e.message?:""))
        }

        return ResponseEntity
            .ok()
            .body(UserApiResponse(status = HttpServletResponse.SC_OK, message = "회원가입 성공"))
    }

}