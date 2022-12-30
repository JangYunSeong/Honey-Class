package com.example.HoneyClass.dto

data class LectureDto(
        var number: String,
        var year: Int,
        var semester : Int,
        var grade : Int,
        var type: String,
        var college : String,
        var major : String,
        var name : String,
        var credit : Int,
        var lecture_credit : Int,
        var prac_credit : Int,
        var professor : String,
        var school_time : String,
        var real_time : String,
        var building : String,
        var room : String,
        var total : Int,
        var sugang : Int,
        var sugangpack : Int,
        var information : String,
        var reviews: ArrayList<ReviewDto>
)
