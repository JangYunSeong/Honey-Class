package com.example.HoneyClass.controller

import com.example.HoneyClass.dto.LectureDto
import com.example.HoneyClass.service.RecomByCampusService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RecomByCampusController (
    @Autowired val recomByCampusService: RecomByCampusService
){

    @GetMapping("/campus")
    fun getRecomByCampus(@RequestParam("semester") semester:Int): ArrayList<LectureDto> {
        return recomByCampusService.recommendByCampus("상주캠퍼스",semester)
    }
}
