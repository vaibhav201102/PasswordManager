package com.vaibhavjoshi.passwordmanager.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vaibhavjoshi.passwordmanager.data.Account

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsBottomSheet(
    account: Account,
    onClose: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    //region Variables
    var visible by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    //endregion Variables

    //Bottom Sheet Dialog Launch Effect
    LaunchedEffect(Unit) { sheetState.show() }

    //region Material Bottom Sheet to fill the details
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {


            Text(
                text = "Account Details",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF2D8CFF)
            )

            Spacer(Modifier.height(20.dp))

            Text("Account Type", color = Color.Gray)
            Text(account.accountType, style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(20.dp))

            Text("Username / Email", color = Color.Gray)
            Text(account.username, style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(20.dp))

            Text("Password", color = Color.Gray)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    if (visible) account.password else "••••••••",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.width(12.dp))
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "toggle"
                    )
                }
            }

            Spacer(Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Edit Button Functionality
                Button(
                    onClick = onEdit,
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Edit", color = Color.White)
                }

                // Delete Button Functionality
                Button(
                    onClick = onDelete,
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4D4D))
                ) {
                    Text("Delete", color = Color.White)
                }
            }

        }
    }
    //endregion Material Bottom Sheet to fill the details
}

