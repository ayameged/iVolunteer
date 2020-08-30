package com.ivolunteer.ivolunteer.types.volunteeruser

data class Rate(
    val avgRate: Double,
    val numberOfRaters: Int,
    val rateId: Int,
    val volunteerUsers: List<Any>
)