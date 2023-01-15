package com.example.HoneyClass.service

import com.example.HoneyClass.dto.LectureDto
import com.example.HoneyClass.repository.LectureInfoRepository
import com.example.HoneyClass.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecomByProfessorService (
    @Autowired val lectureInfoRepository: LectureInfoRepository,
    @Autowired val reviewRepository: ReviewRepository
        ){

    fun recommend(professor: String) : ArrayList<LectureDto>{
        val result = lectureInfoRepository.findByProfessor(professor)
        return ArrayList(result.map {
            it.toLectureDto(reviewRepository.findByLecture_id(it.lecture_id))
        })
    }
}