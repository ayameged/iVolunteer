package com.ivolunteer.ivolunteer.types.VolunteerWithSched

data class VolunteerUser(
    val applicationUser: ApplicationUserX,
    val id: String,
    val rate: Any,
    val rateId: Int,
    val volunteer: List<Any>,
    val volunteerUserCity: Any,
    val volunteerUserCityId: Int
)