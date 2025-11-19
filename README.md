🔐 Password Manager – Secure Android App (Jetpack Compose + Room + AES Encryption)

A modern, secure, and user-friendly Password Manager built using Jetpack Compose, Room Database, and AES-256 encryption.
This app allows users to safely store, manage, and access their passwords with biometric authentication.

📌 Features
✅ Core Functionality

Add New Passwords
Store account type (Gmail, Facebook, Instagram, etc.), username/email, and password.

View & Edit Stored Passwords
Access detailed information and edit items in a clean bottom sheet UI.

List of All Passwords
Organized, minimal UI showing masked password entries.

Delete Password Entries

🔐 Security Features

AES Encryption (AndroidX Security Crypto)
All passwords are encrypted locally before being saved in Room.

Biometric Authentication
Fingerprint / Face Unlock or device credential required to access the app.

No sensitive data stored in plaintext

🎨 User Interface (Jetpack Compose)

Clean, modern UI fully built using Jetpack Compose.

Follows the provided Figma design including:

Rounded cards

Smooth bottom sheets

Button styling

Intuitive UX flow

🛠 Technical Highlights
🔸 Room Database

Secure local storage with DAO, entity, repository pattern.

🔸 Encryption

AES-256 via Android Security Crypto.

🔸 Jetpack Compose

Material 3

Modal bottom sheets

Custom text fields

Responsive layouts

🔸 Biometric Prompt

Biometric + device credential authentication on app startup.

⭐ Bonus Features Implemented

Password Strength Indicator

Random Strong Password Generator

Biometric Lock Screen
