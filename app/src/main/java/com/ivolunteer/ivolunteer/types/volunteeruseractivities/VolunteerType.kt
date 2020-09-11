package com.ivolunteer.ivolunteer.types.volunteeruseractivities

data class VolunteerType(
    val type: String,
    val volunteerTypeId: Int,
    val volunteers: List<Any>
)