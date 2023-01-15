package com.example.HoneyClass.service

import com.example.HoneyClass.dto.LectureDto
import com.example.HoneyClass.repository.LectureInfoRepository
import com.example.HoneyClass.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecomByCategoryService(
        @Autowired val lectureInfoRepository: LectureInfoRepository,
        @Autowired val reviewRepository: ReviewRepository
){

    // 전공, 교양일 경우 findByType , 나머지는 findByCategory

    fun recommend(category : String,semester:Int) : ArrayList<LectureDto>{
        val lectureList = if(category == "전공" || category == "교양"){
            lectureInfoRepository.findByType(category,semester)
        } else {
            lectureInfoRepository.findByCategory(category,semester)
        }

        return ArrayList(lectureList.map {
            it.toLectureDto(reviewRepository.findByLecture_id(it.lecture_id))
        })
    }

}