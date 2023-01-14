package com.example.HoneyClass.repository

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootTest
class LectureInfoRepositoryTest(
        @Autowired val lectureInfoRepository: LectureInfoRepository
) {

//    @Test
//    fun searchByMajor(){
//        var major = "컴퓨터학부"
//        val result = lectureInfoRepository.findByOrderedMajor(major)
//        for (lecture in result) {
//            println("id: " + lecture.lecture_id + " name: " + lecture.name)
//            assertThat(lecture.major).isEqualTo(major)
//        }
//    }
//    @Test
//    fun searchByProfessor(){
//        var professor = "정인욱"
//        val result = lectureInfoRepository.findByProfessor(professor)
//        for (lecture in result){
//            println("id: " + lecture.lecture_id + " name: " + lecture.name)
//            assertThat(lecture.professor).contains(professor)
//        }
//    }
    @Test
    fun searchByCategory(){
        var category = "교양"
        if (category == "교양" || category == "전공"){
            val result = lectureInfoRepository.findByType(category)
            for (lecture in result){
                println("id: " + lecture.lecture_id + " name: " + lecture.name + " sugangpack: "+lecture.sugangpack + " sugang: "+ lecture.sugang)
                assertThat(lecture.type).contains(category)
            }
        }
        else{
            val result = lectureInfoRepository.findByCategory(category)
            for (lecture in result){
                println("id: " + lecture.lecture_id + " name: " + lecture.name + " sugangpack: "+lecture.sugangpack + " sugang: "+ lecture.sugang)
                assertThat(lecture.information).contains(category)
            }
        }
    }

//    @Test
//    fun searchByMajor_Ordered(){
//        var major = "컴퓨터학부"
//        val result = lectureInfoRepository.findByOrderedMajor(major)
//        for (lecture in result) {
//            var total = lecture.total.toFloat()
//            var sugangpack = lecture.sugangpack.toFloat()
//            var sugang = lecture.sugang.toFloat()
//            println("id: " + lecture.lecture_id + " name: " + lecture.name + " sugangpack / total: " + sugangpack / total + " sugang / total : " + sugang / total)
//
//            assertThat(lecture.major).isEqualTo(major)
//        }
//    }
}