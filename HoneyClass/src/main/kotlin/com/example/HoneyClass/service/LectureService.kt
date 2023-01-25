package com.example.HoneyClass.service

import com.example.HoneyClass.dto.LectureDto
import com.example.HoneyClass.repository.LectureInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LectureService (
        @Autowired val lectureInfoRepository: LectureInfoRepository
        ) {

    fun findLectureById(id: String) : ArrayList<LectureDto>{
        return ArrayList(
                lectureInfoRepository.findByLectureId(id).map {
                    it.toLectureDto()
                })
    }
}