package com.example.HoneyClass.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RecomByCategoryServiceTest(
        @Autowired val recomByCategoryService: RecomByCategoryService
) {

    @Test
    fun recommendTest(){
        var category = "교양"
        var result = recomByCategoryService.recommend(category)
        for (lectureDto in result) {
            println("lecture: " + lectureDto.name + " " + lectureDto.number + " " + lectureDto.professor)
            for (review in lectureDto.reviews) {
                println(review.lecture_id + " " + review.professor + " " + review.review_mean + " " + review.favor)
            }
        }
    }
}
