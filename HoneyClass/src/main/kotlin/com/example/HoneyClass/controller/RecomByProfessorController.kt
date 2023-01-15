package com.example.HoneyClass.controller

import com.example.HoneyClass.dto.LectureDto
import com.example.HoneyClass.service.RecomByProfessorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RecomByProfessorController (
    @Autowired val recomByProfessorService: RecomByProfessorService
        ){

    @GetMapping("/professor-lecture")
    fun getRecomByProfessor(@RequestParam("professor") professor: String,@RequestParam("semester") semester : Int): ArrayList<LectureDto>{
        return recomByProfessorService.recommend(professor,semester)
    }
}