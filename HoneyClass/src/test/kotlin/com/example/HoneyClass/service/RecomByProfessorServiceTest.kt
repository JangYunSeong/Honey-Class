package com.example.HoneyClass.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RecomByProfessorServiceTest (
    @Autowired val recomByProfessorService: RecomByProfessorService
        ){

    @Test
    fun professorTest(){
        var professor = "서영균"
        val result = recomByProfessorService.recommend(professor)
        for (p in result) {
            println("professor: " + p.professor + " name: " + p.name + " major: " + p.major +
                    " sugangpack: " + p.sugangpack + " sugang: " + p.sugang)

            Assertions.assertThat(p.professor).isEqualTo(professor)
        }
    }
}