package com.example.HoneyClass.service

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LectureServiceTest(
        @Autowired val lectureService: LectureService
) {

    @Test
    fun findByIdTest(){
        val id = "20222COMP0322004"
        val result = lectureService.findLectureById(id)
        assertThat(result[0].lecture_id).isEqualTo(id)
    }
}