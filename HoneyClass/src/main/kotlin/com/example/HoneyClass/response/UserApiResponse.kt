package com.example.HoneyClass.response

data class UserApiResponse(
    val status: Int,
    val message: String,
    val data: Any?
){
    constructor(status: Int, message: String): this(status, message, null)
}

