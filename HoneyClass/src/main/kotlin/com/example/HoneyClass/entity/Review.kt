package com.example.HoneyClass.entity

import com.example.HoneyClass.dto.ReviewDto
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "review")
class Review(
        @Id
        var review_id : String,
        var lecture_id : String,
        var name : String,
        var professor : String,
        var sugang_number : Int,
        var review_number : Int,
        var review_mean : Float,
        var univ_mean : Float,
        var standard_deviation : Float
) {
        fun toReviewDto() : ReviewDto{
                return ReviewDto(review_id = review_id, lecture_id = lecture_id, professor = professor, sugang_number = sugang_number, review_number = review_number,
                        review_mean = review_mean, univ_mean = univ_mean, standard_deviation = standard_deviation, 0F)
        }
}