package com.example.HoneyClass.controller

import com.example.HoneyClass.dto.LectureDto
import com.example.HoneyClass.dto.ReviewDto
import com.example.HoneyClass.service.ReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ReviewByIdController (
    @Autowired val reviewByIdService : ReviewService
){
    @GetMapping("/review")
    fun getReviewById(@RequestParam("id") id: String): ArrayList<ReviewDto>{
        return reviewByIdService.recommend(id);
    }
}