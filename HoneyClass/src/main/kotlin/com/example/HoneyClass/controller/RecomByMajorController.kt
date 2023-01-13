package com.example.HoneyClass.controller

import com.example.HoneyClass.dto.LectureDto
import com.example.HoneyClass.service.RecomByMajorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RecomByMajorController (
    @Autowired val recomByMajorService: RecomByMajorService
        ){

    @GetMapping("/getRecommend")
    fun getRecomByMajor(@RequestParam("major") major: String): ArrayList<LectureDto> {
        return recomByMajorService.recommend(major)
    }
}