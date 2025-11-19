// AccountsViewModel.kt
package com.vaibhavjoshi.passwordmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhavjoshi.passwordmanager.data.Account
import com.vaibhavjoshi.passwordmanager.data.AccountRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UiAccount(val account: Account)

class AccountsViewModel(private val repo: AccountRepository) : ViewModel() {

    //region Variables
    val accountsState: StateFlow<List<UiAccount>> =
        repo.getAllDecryptedFlow()
            .map { list -> list.map { UiAccount(it) } }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    //endregion Variables

    //region Add/Update/Delete Functionality
    fun addAccount(accountType: String, username: String, password: String, onDone: ()->Unit, onError:(String)->Unit) {
        if (accountType.isBlank() || username.isBlank() || password.isBlank()) {
            onError("All fields are required.")
            return
        }
        viewModelScope.launch {
            try {
                repo.insert(Account(accountType = accountType, username = username, password = password))
                onDone()
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Failed to save account")
            }
        }
    }

    fun updateAccount(acc: Account, onDone: ()->Unit, onError:(String)->Unit) {
        viewModelScope.launch {
            try {
                repo.update(acc)
                onDone()
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Failed to update")
            }
        }
    }

    fun deleteAccount(acc: Account, onDone: ()->Unit, onError:(String)->Unit) {
        viewModelScope.launch {
            try {
                repo.delete(acc)
                onDone()
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Failed to delete")
            }
        }
    }
    //endregion Add/Update/Delete Functionality
}
