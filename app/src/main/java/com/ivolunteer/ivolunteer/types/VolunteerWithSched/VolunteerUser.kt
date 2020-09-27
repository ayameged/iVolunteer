package com.ivolunteer.ivolunteer.types.VolunteerWithSched

import com.ivolunteer.ivolunteer.types.common.Rate

data class VolunteerUser(
    val applicationUser: ApplicationUserX,
    val id: String,
    val rate: Rate,
    val rateId: Int,
    val volunteer: List<Any>,
    val volunteerUserCity: Any,
    val volunteerUserCityId: Int
)
