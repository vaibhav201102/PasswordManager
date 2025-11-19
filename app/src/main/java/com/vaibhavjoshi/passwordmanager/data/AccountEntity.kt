package com.vaibhavjoshi.passwordmanager.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val accountType: String,
    val username: String,
    val passwordEncrypted: String,
    val createdAt: Long = System.currentTimeMillis()
)