package com.example.HoneyClass.repository

import com.example.HoneyClass.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {
    fun findByStudentId(studentId: String): User?
}