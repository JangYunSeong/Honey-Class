package com.example.HoneyClass.repository

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReviewRepositoryTest(
        @Autowired val reviewRepository: ReviewRepository
) {

    @Test
    fun searchByLectureId(){
        val lecId = "20221COMP0217001"
        val result = reviewRepository.findByLecture_id(lecId)
        for (review in result) {
            println("revew id: " + review.review_id + " professor: " + review.professor + " mean: " + review.review_mean)
            assertThat(review.lecture_id).isEqualTo(lecId)
        }
    }
}