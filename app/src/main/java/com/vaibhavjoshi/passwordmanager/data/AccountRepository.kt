package com.vaibhavjoshi.passwordmanager.data

import com.vaibhavjoshi.passwordmanager.crypto.CryptoManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountRepository(
    private val dao: AccountDao,
    private val crypto: CryptoManager
) {
    fun getAllDecryptedFlow(): Flow<List<Account>> =
        dao.getAllFlow().map { list -> list.map { it.toDomain(crypto) } }

    suspend fun insert(account: Account) {
        val entity = account.toEntity(crypto)
        dao.insert(entity)
    }

    suspend fun update(account: Account) {
        val entity = account.toEntity(crypto)
        dao.update(entity)
    }

    suspend fun delete(account: Account) {
        dao.delete(account.toEntity(crypto))
    }

    suspend fun getById(id: Long): Account? {
        val e = dao.getById(id) ?: return null
        return e.toDomain(crypto)
    }
}


fun AccountEntity.toDomain(crypto: CryptoManager) = Account(
    id = id,
    accountType = accountType,
    username = username,
    password = try { crypto.decrypt(passwordEncrypted) } catch (e: Exception) { "" },
    createdAt = createdAt
)

fun Account.toEntity(crypto: CryptoManager) = AccountEntity(
    id = id,
    accountType = accountType,
    username = username,
    passwordEncrypted = crypto.encrypt(password)
)