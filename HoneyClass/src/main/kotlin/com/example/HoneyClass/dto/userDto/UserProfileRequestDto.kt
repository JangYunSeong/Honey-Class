package com.example.HoneyClass.dto.userDto

import javax.validation.constraints.NotNull

data class UserProfileRequestDto (
    @NotNull
    var studentId: String
    )