package com.example.HoneyClass.repository

import com.example.HoneyClass.entity.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

interface LectureInfoRepository : JpaRepository<Lecture, String> {

    fun findByMajor(major: String) : ArrayList<Lecture>

    @Query("select * from lecture where semester=:semester and major = :major order by (sugangpack / sugang) DESC LIMIT 100",nativeQuery = true)
    fun findByOrderedMajor(major: String,semester:Int) : ArrayList<Lecture>

    @Query("select * from lecture where semester=:semester and information like %:category% order by (sugangpack / sugang) DESC LIMIT 100",nativeQuery = true)
    fun findByCategory(category:String,semester:Int) : ArrayList<Lecture>

    @Query("select * from lecture where semester=:semester and professor like %:professor% order by (sugangpack / sugang) DESC LIMIT 100",nativeQuery = true)
    fun findByProfessor(professor:String,semester:Int) : ArrayList<Lecture>

    @Query("select * from lecture where semester=:semester and type like %:type% order by (sugangpack / sugang) DESC LIMIT 100",nativeQuery = true)
    fun findByType(type:String,semester:Int) : ArrayList<Lecture>
}