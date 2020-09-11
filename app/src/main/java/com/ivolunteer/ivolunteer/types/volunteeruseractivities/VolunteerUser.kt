package com.ivolunteer.ivolunteer.types.volunteeruseractivities

import com.ivolunteer.ivolunteer.types.common.Rate

data class VolunteerUser(
    val applicationUser: Any,
    val id: String,
    val rate: Rate,
    val rateId: Int,
    val volunteer: List<Volunteer>,
    val volunteerUserCity: Any,
    val volunteerUserCityId: Int
)