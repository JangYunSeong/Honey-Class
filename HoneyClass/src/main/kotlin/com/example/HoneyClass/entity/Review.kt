package com.example.HoneyClass.entity

import com.example.HoneyClass.annotation.AllOpen
import com.example.HoneyClass.cumulative.Cumulative
import com.example.HoneyClass.dto.ReviewDto
import org.springframework.scheduling.annotation.Async
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "review")
@AllOpen
open class Review(
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

        @Async
        open fun toReviewDto() : ReviewDto{

                val cumulative = Cumulative()

                var zScore = (review_mean - univ_mean) / standard_deviation
                var isPos = 1
                if(zScore < 0) {
                        isPos = 0
                        zScore *= -1.0F
                }

                var favor = 0F

                if((zScore * 10.0F).toInt() > 40){
                        if(isPos == 1) favor = 100.0F
                        else favor = 0F
                }
                else{
                        favor = cumulative.cumul[isPos][(zScore * 10.0F).toInt()][(zScore * 100.0F).toInt() % 10].toFloat()
                }

                return ReviewDto(review_id = review_id, lecture_id = lecture_id, professor = professor, sugang_number = sugang_number, review_number = review_number,
                        review_mean = review_mean, univ_mean = univ_mean, standard_deviation = standard_deviation,
                        favor = favor)
        }

}