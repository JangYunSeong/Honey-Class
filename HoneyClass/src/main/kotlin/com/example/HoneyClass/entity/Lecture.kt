package com.example.HoneyClass.entity

import com.example.HoneyClass.dto.LectureDto
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "lecture")
class Lecture(
        @Id
        var lecture_id: String, //year+semester+lecture_number(PK)
        var lecture_number: String,
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
        var information : String){

        fun toLectureDto(reviews: ArrayList<Review>) : LectureDto{
                return LectureDto(number = lecture_number, year = year, semester = semester, grade = grade, type = type, college = college, major = major, name = name,
                credit = credit, lecture_credit = lecture_credit, prac_credit = prac_credit, professor = professor, school_time = school_time, real_time = real_time, building = building,
                room = room, total = total, sugang = sugang, sugangpack = sugangpack, information = information, reviews = ArrayList(reviews.map { it.toReviewDto() }))
        }

}