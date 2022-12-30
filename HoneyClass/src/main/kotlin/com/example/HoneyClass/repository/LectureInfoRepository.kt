package com.example.HoneyClass.repository

import com.example.HoneyClass.entity.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

interface LectureInfoRepository : JpaRepository<Lecture, String> {

    fun findByMajor(major: String) : ArrayList<Lecture>

    @Query("select l from Lecture l where l.major = :major order by (l.sugangpack / l.total) DESC , (l.sugang / l.total) DESC")
    fun findByOrderedMajor(major: String) : ArrayList<Lecture>
}