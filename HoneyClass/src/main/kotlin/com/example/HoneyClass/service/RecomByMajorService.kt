package com.example.HoneyClass.service

import com.example.HoneyClass.dto.LectureDto
import com.example.HoneyClass.repository.LectureInfoRepository
import com.example.HoneyClass.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecomByMajorService(
        @Autowired val lectureInfoRepository: LectureInfoRepository,
        @Autowired val reviewRepository: ReviewRepository
) {

    fun recommend(major : String,semester:Int) : ArrayList<LectureDto> {
        val lectureList = lectureInfoRepository.findByOrderedMajor(major,semester)
        return ArrayList(lectureList.map {
            it.toLectureDto()
        })
    }
}