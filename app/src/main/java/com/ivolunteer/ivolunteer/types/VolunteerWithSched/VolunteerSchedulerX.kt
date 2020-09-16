package com.ivolunteer.ivolunteer.types.VolunteerWithSched

data class VolunteerSchedulerX(
    val internalData: String,
    val isEvening: Boolean,
    val isMorning: Boolean,
    val isNoon: Boolean,
    val volunteerSchedulerId: Int,
    val volunteers: List<Any>,
    val weekDays: List<Int>
)