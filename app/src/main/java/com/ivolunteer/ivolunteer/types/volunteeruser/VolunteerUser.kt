package com.ivolunteer.ivolunteer.types.volunteeruser

data class VolunteerUser(
    val applicationUser: ApplicationUser,
    val id: String,
    val rate: Rate,
    val rateId: Int,
    val volunteer: List<Any>,
    val volunteerUserCity: VolunteerUserCity,
    val volunteerUserCityId: Int
)