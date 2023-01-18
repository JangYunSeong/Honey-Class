package com.example.HoneyClass.config.auth

import com.example.HoneyClass.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList


class PrincipalDetails(private val user: User) : UserDetails {


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {

        return user.getRoleList().stream().map { role -> SimpleGrantedAuthority("$role") }.collect(Collectors.toSet())
    }

    override fun getPassword(): String? {
        return user.password
    }

    override fun getUsername(): String {
        return user.name
    }

    fun getMajor(): String {
        return user.major
    }

    fun getStudentId(): String {
        return user.studentId
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}