package com.example.HoneyClass.dto.userDto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class UserJoinRequestDto(
    /*@NotEmpty(message = "학번 정보를 입력해주세요") */var studentId: String,
    /*@NotEmpty(message = "학과 정보를 입력해주세요")*/var major: String,
    /*@NotEmpty(message = "이름 정보를 입력해주세요")*/var name: String,
    /*@NotEmpty(message = "비밀번호 정보를 입력해주세요")*/var password: String
)