package com.example.HoneyClass.exception

import com.example.HoneyClass.response.UserApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomMethodArgumentNotValidHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(e: MethodArgumentNotValidException): ResponseEntity<UserApiResponse> {
        return ResponseEntity
            .ok()
            .body(UserApiResponse(status = 200, message = e.message, data = ""))
    }

}