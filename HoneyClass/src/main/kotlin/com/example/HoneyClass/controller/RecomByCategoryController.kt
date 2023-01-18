package com.example.HoneyClass.controller

import com.example.HoneyClass.dto.LectureDto
import com.example.HoneyClass.service.RecomByCategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RecomByCategoryController (
        @Autowired val recomByCategoryService: RecomByCategoryService
        ){

    @GetMapping("/api/category")
    fun getRecomByCategory(@RequestParam("category") category : String,@RequestParam("semester") semester : Int): ArrayList<LectureDto> {
        return recomByCategoryService.recommend(category,semester)
    }
}
