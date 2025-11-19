package com.vaibhavjoshi.passwordmanager.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vaibhavjoshi.passwordmanager.data.Account
import androidx.compose.material3.TextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBottomSheet(
    initial: Account?,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    //region Variables
    var type by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    //endregion Variables

    //region Bottom Sheet Dialog Launch Effect
    // Pre-fill fields when editing
    LaunchedEffect(initial) {
        type = initial?.accountType ?: ""
        username = initial?.username ?: ""
        password = initial?.password ?: ""
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    LaunchedEffect(Unit) { sheetState.show() }

    //endregion Bottom Sheet Dialog Launch Effect

    //region Material Bottom Sheet to fill the details
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {

            // Title
            if (initial != null) {
                Text(
                    text = "Account Details",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF297BFF)
                )
            }

            Spacer(Modifier.height(24.dp))

            // ACCOUNT TYPE
            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                label = {
                    Text(
                        "Account Name",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                disabledBorderColor = Color.LightGray,
                errorBorderColor = Color.LightGray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.Black // optional
            ),
            )


            Spacer(Modifier.height(20.dp))

            // USERNAME / EMAIL
            OutlinedTextField(
                label = {Text("Username / Email", color = Color.LightGray, style = MaterialTheme.typography.labelMedium)},
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray,
                    disabledBorderColor = Color.LightGray,
                    errorBorderColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.Black // optional
                ),
            )

            Spacer(Modifier.height(20.dp))

            // PASSWORD WITH VISIBILITY TOGGLE
            OutlinedTextField(
                label = {Text("Password", color = Color.LightGray, style = MaterialTheme.typography.labelMedium)},
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray,
                    disabledBorderColor = Color.LightGray,
                    errorBorderColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.Black // optional
                ),
                trailingIcon = {
                    IconButton(onClick = { visible = !visible }) {
                        Icon(
                            if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Password",
                            tint = Color.LightGray, // Change the color of the icon to red
                        )
                    }
                },
                visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation()
            )

            Spacer(Modifier.height(30.dp))

            // BUTTON ROW → ADD + CANCEL OR SAVE + DELETE
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (initial == null) {
                    // ADD Button Functionality
                    Button(
                        onClick = { onSave(type.trim(), username.trim(), password) },
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Add New Account", color = Color.White)
                    }

                } else {
                    // SAVE Button Functionality
                    Button(
                        onClick = { onSave(type.trim(), username.trim(), password) },
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Save", color = Color.White)
                    }

                    // DELETE Button Functionality
                    Button(
                        onClick = { onDelete?.invoke() },
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4D4D))
                    ) {
                        Text("Delete", color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}
