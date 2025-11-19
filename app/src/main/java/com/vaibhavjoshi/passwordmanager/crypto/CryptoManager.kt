package com.vaibhavjoshi.passwordmanager.crypto

import android.util.Base64
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class CryptoManager(
    private val keyAlias: String = "pm_aes_key"
) {
    private val androidKeyStore = "AndroidKeyStore"
    private val transformation = "AES/GCM/NoPadding"

    init {
        ensureKey()
    }

    private fun ensureKey() {
        val ks = KeyStore.getInstance(androidKeyStore).apply { load(null) }
        if (!ks.containsAlias(keyAlias)) {
            val keyGenerator = KeyGenerator.getInstance("AES", androidKeyStore)
            val keySpec = android.security.keystore.KeyGenParameterSpec.Builder(
                keyAlias,
                android.security.keystore.KeyProperties.PURPOSE_ENCRYPT or android.security.keystore.KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build()
            keyGenerator.init(keySpec)
            keyGenerator.generateKey()
        }
    }

    private fun getKey(): SecretKey {
        val ks = KeyStore.getInstance(androidKeyStore).apply { load(null) }
        return (ks.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey
    }

    // returns Base64(iv + ciphertext)

    /// region Text Encryption
    fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val iv = cipher.iv // 12 bytes recommended for GCM
        val cipherBytes = cipher.doFinal(plainText.toByteArray(Charset.forName("UTF-8")))
        val combined = ByteArray(iv.size + cipherBytes.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(cipherBytes, 0, combined, iv.size, cipherBytes.size)
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }
    /// endregion Text Encryption

    // region Text Decryption
    fun decrypt(base64: String): String {
        val combined = Base64.decode(base64, Base64.NO_WRAP)
        val iv = combined.copyOfRange(0, 12)
        val cipherBytes = combined.copyOfRange(12, combined.size)
        val cipher = Cipher.getInstance(transformation)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)
        val plainBytes = cipher.doFinal(cipherBytes)
        return String(plainBytes, Charset.forName("UTF-8"))
    }
    // endregion Text Decryption
}
