package com.example.HoneyClass.service

import com.example.HoneyClass.dto.ReviewDto
import com.example.HoneyClass.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReviewService(
    @Autowired val reviewRepository: ReviewRepository
    ){
        fun recommend(id:String) : ArrayList<ReviewDto>{
            val result = reviewRepository.findByLecture_id(id)
            return ArrayList(result.map {
                it.toReviewDto()
            })
        }
}