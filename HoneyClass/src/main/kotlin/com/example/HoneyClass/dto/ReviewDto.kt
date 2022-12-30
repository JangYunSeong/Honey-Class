package com.example.HoneyClass.dto

data class ReviewDto(
        var review_id : String,
        var lecture_id : String,
        var professor : String,
        var sugang_number : Int,
        var review_number : Int,
        var review_mean : Float,
        var univ_mean : Float,
        var standard_deviation : Float,
        var favor: Float
)
