package com.vaibhavjoshi.passwordmanager.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.vaibhavjoshi.passwordmanager.crypto.CryptoManager
import com.vaibhavjoshi.passwordmanager.data.AccountRepository
import com.vaibhavjoshi.passwordmanager.data.AppDatabase
import com.vaibhavjoshi.passwordmanager.utils.BiometricHelper

class MainActivity : FragmentActivity() {

    //region Variables
    private lateinit var db: AppDatabase
    private lateinit var crypto : CryptoManager
    private lateinit var repo : AccountRepository
    private lateinit var accountsViewModel: AccountsViewModel
    //endregion Variables

    //region Override Method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Build database (no DI for now)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "password-manager-db"
        ).build()

        crypto = CryptoManager()
        repo = AccountRepository(db.accountDao(), crypto)
        accountsViewModel = AccountsViewModel(repo)

        mainScreenUIDesign()
    }
    //endregion Override Method

    //region Main Screen Ui Components
    @OptIn(ExperimentalMaterial3Api::class)
    private fun mainScreenUIDesign(){
        ///Setting Screen Content
        setContent {
            /// Initializing Material Theme
            MaterialTheme {

                /// Taking Unlock Flag for checking device has biometric or not
                var unlocked by remember { mutableStateOf(false) }

                //region Launch biometric check
                LaunchedEffect(Unit) {
                    if (BiometricHelper.canAuthenticate(this@MainActivity)) {
                        //Open BioMetric Prompt For the End User to unlock the screen
                        BiometricHelper.showBiometricPrompt(
                            context = this@MainActivity,
                            onAuthSuccess = { unlocked = true },
                            onAuthError = { message ->
                                Toast.makeText(
                                    this@MainActivity,
                                    "Authentication error: $message",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onAuthFailed = {  Toast.makeText(
                                this@MainActivity,
                                "Authentication failed. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                            }
                        )
                    } else {
                        // No biometric available → fallback allow
                        unlocked = true
                    }
                }
                //endregion Launch biometric check

                /// Unlock Flag Based Navigation to the Screen
                if (unlocked) {
                    MainScreen(vm = accountsViewModel)
                } else {
                    LockScreen()   // Small UI while waiting
                }
            }
        }
    }
    //endregion Main Screen Ui Components
}
