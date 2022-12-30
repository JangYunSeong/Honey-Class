package com.example.HoneyClass.repository

import com.example.HoneyClass.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

interface ReviewRepository : JpaRepository<Review, String> {

    @Query("select r from Review r where r.lecture_id = :id")
    fun findByLecture_id (@Param("id") lecture_id : String) : ArrayList<Review>
}