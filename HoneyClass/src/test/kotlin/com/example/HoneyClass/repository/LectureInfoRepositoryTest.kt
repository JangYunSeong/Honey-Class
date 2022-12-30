package com.example.HoneyClass.repository

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LectureInfoRepositoryTest(
        @Autowired val lectureInfoRepository: LectureInfoRepository
) {

    @Test
    fun searchByMajor(){
        var major = "컴퓨터학부"
        val result = lectureInfoRepository.findByMajor(major)
        for (lecture in result) {
            println("id: " + lecture.lecture_id + " name: " + lecture.name)
            assertThat(lecture.major).isEqualTo(major)
        }
    }

    @Test
    fun searchByMajor_Ordered(){
        var major = "컴퓨터학부"
        val result = lectureInfoRepository.findByOrderedMajor(major)
        for (lecture in result) {
            var total = lecture.total.toFloat()
            var sugangpack = lecture.sugangpack.toFloat()
            var sugang = lecture.sugang.toFloat()
            println("id: " + lecture.lecture_id + " name: " + lecture.name + " sugangpack / total: " + sugangpack / total + " sugang / total : " + sugang / total)

            assertThat(lecture.major).isEqualTo(major)
        }
    }
}