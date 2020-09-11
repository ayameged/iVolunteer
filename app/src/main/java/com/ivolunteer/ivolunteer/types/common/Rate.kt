package com.ivolunteer.ivolunteer.types.common

data class Rate(
    val avgRate: Double,
    val numberOfRaters: Int,
    val rateId: Int,
    val volunteerUsers: List<Any>
)