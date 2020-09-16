package com.ivolunteer.ivolunteer.types.VolunteerWithSched

data class ApplicationUserX(
    val accessFailedCount: Int,
    val age: Int,
    val concurrencyStamp: String,
    val email: String,
    val emailConfirmed: Boolean,
    val firstName: String,
    val gender: String,
    val id: String,
    val imageUrl: Any,
    val lastName: String,
    val lockoutEnabled: Boolean,
    val lockoutEnd: Any,
    val needHelpUser: Any,
    val normalizedEmail: String,
    val normalizedUserName: String,
    val passwordHash: String,
    val phoneNumber: String,
    val phoneNumberConfirmed: Boolean,
    val securityStamp: String,
    val twoFactorEnabled: Boolean,
    val userName: String
)