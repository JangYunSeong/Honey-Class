package com.example.HoneyClass.controller

import com.example.HoneyClass.dto.LectureDto
import com.example.HoneyClass.service.LectureService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LectureByIdController(
        @Autowired val lectureService: LectureService
) {

    @GetMapping("/lecture")
    fun getLectureById(@RequestParam("id") lectureId: String) : ArrayList<LectureDto>{
        return lectureService.findLectureById(lectureId)
    }
}