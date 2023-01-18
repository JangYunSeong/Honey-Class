package com.example.HoneyClass.entity

import lombok.Getter
import lombok.ToString
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Getter
@ToString
@Table(name="user")
data class User(
    @Id @Column(name = "studentId") var studentId: String,
    @Column(name = "name") var name: String,
    @Column(name = "password") var password: String,
    @Column(name = "major") var major: String,
    @Column(name = "role") var role: String,
    @Column(name = "createDt") var createDt: LocalDateTime = LocalDateTime.now()
) {
    fun getRoleList(): List<Any> {
        if (this.role.isNotEmpty()) {
            return this.role.split(",")
        }
        return listOf<String>()
    }
}