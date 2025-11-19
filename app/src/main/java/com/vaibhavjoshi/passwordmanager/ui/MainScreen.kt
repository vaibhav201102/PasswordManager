package com.vaibhavjoshi.passwordmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vaibhavjoshi.passwordmanager.data.Account
import kotlinx.coroutines.launch

// MainScreen.kt
@ExperimentalMaterial3Api
@Composable
fun MainScreen(vm: AccountsViewModel) {

    //region Variables

    val accounts by vm.accountsState.collectAsState()
    var selectedAccount by remember { mutableStateOf<Account?>(null) }
    var showDetailsSheet by remember { mutableStateOf(false) }
    var showAddSheet by remember { mutableStateOf(false) }
    var editModeAccount by remember { mutableStateOf<Account?>(null) } // <-- for editing

    //endregion Variables

    //region Main Screen UI
    Scaffold(
        topBar = { TopAppBar(title = { Text("Password Manager") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editModeAccount = null   // new account
                    showAddSheet = true
                },
                shape = RoundedCornerShape(14.dp),
                containerColor = Color(0xFF2D8CFF),
                modifier = Modifier.size(60.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "add", tint = Color.White)
            }
        }
    ) { innerPadding ->

        //region Accounts List view
        LazyColumn(Modifier.padding(innerPadding)) {
            items(accounts) { ui ->
                AccountRow(ui.account) {
                    selectedAccount = ui.account
                    showDetailsSheet = true
                }
                Spacer(Modifier.height(8.dp))
            }
        }
        //endregion Accounts List view

        //region Show Details Bottom Sheet Dialog
        if (showDetailsSheet && selectedAccount != null) {
            DetailsBottomSheet(
                account = selectedAccount!!,
                onClose = { showDetailsSheet = false },
                onDelete = {
                    vm.deleteAccount(selectedAccount!!, {
                        showDetailsSheet = false
                    }, {})
                },
                onEdit = {
                    showDetailsSheet = false
                    editModeAccount = selectedAccount
                    showAddSheet = true
                }
            )
        }
        //endregion Show Details Bottom Sheet Dialog

        //region Show Add Edit Account Details Bottom Sheet Dialog
        if (showAddSheet) {
            AddEditBottomSheet(
                initial = editModeAccount,  // <-- this fills data if editing
                onDismiss = { showAddSheet = false },
                onSave = { type, user, pass ->
                    if (editModeAccount == null) {
                        // new account
                        vm.addAccount(type, user, pass, {
                            showAddSheet = false
                        }, {})
                    } else {
                        // editing existing
                        vm.updateAccount(
                            editModeAccount!!.copy(
                                accountType = type,
                                username = user,
                                password = pass
                            ),
                            {
                                showAddSheet = false
                            },
                            {}
                        )
                    }
                }
            )
        }
        //endregion Show Add Edit Account Details Bottom Sheet Dialog
    }
    //endregion Main Screen UI
}
