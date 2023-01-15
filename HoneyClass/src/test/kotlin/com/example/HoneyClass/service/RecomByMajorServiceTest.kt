package com.example.HoneyClass.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RecomByMajorServiceTest(
        @Autowired val recomByMajorService: RecomByMajorService
) {

    @Test
    fun recommandTest(){
        var major = "컴퓨터학부"
        val result = recomByMajorService.recommend(major)
        for (lectureDto in result) {
            println("lecture: " +lectureDto.name + " " + lectureDto.number + " " + lectureDto.professor)
            for (review in lectureDto.reviews) {
                println(review.lecture_id + " " + review.professor + " " + review.review_mean + " " + review.favor)
            }
        }
    }
}