package com.vaibhavjoshi.passwordmanager.data

// Domain model + mapping
data class Account(
    val id: Long = 0,
    val accountType: String,
    val username: String,
    val password: String,
    val createdAt: Long = System.currentTimeMillis()
)