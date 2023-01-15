package com.example.HoneyClass.repository

import com.example.HoneyClass.entity.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

interface LectureInfoRepository : JpaRepository<Lecture, String> {

    fun findByMajor(major: String) : ArrayList<Lecture>

    @Query("select l from Lecture l where l.semester=:semester and l.major = :major order by (l.sugangpack / l.sugang) DESC , (l.sugang / l.total) DESC")
    fun findByOrderedMajor(major: String,semester:Int) : ArrayList<Lecture>

    @Query("select l from Lecture l where l.semester=:semester and l.information like %:category% order by (l.sugangpack / l.sugang) DESC , (l.sugang / l.total) DESC")
    fun findByCategory(category:String,semester:Int) : ArrayList<Lecture>

    @Query("select l from Lecture l where l.semester=:semester and l.professor like %:professor% order by (l.sugangpack / l.sugang) DESC, (l.sugang / l.total) DESC")
    fun findByProfessor(professor:String,semester:Int) : ArrayList<Lecture>

    @Query("select l from Lecture l where l.semester=:semester and l.type like %:type% order by (l.sugangpack / l.sugang) DESC")
    fun findByType(type:String,semester:Int) : ArrayList<Lecture>
}