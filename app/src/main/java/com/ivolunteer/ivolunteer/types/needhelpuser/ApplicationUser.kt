package com.ivolunteer.ivolunteer.types.needhelpuser

data class ApplicationUser(
    val accessFailedCount: Int,
    val age: Int,
    val concurrencyStamp: String,
    val email: String,
    val emailConfirmed: Boolean,
    val firstName: String,
    val gender: Any,
    val id: String,
    val lastName: Any,
    val lockoutEnabled: Boolean,
    val lockoutEnd: Any,
    val normalizedEmail: String,
    val normalizedUserName: String,
    val passwordHash: String,
    val phoneNumber: Any,
    val phoneNumberConfirmed: Boolean,
    val securityStamp: String,
    val twoFactorEnabled: Boolean,
    val userName: String,
    val volunteerUser: Any
)