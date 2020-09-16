package com.ivolunteer.ivolunteer.types.VolunteerWithSched

data class NeedHelpUser(
    val aboutMe: Any,
    val applicationUser: ApplicationUser,
    val id: String,
    val needHelpCity: Any,
    val needHelpCityId: Int,
    val volunteers: List<Any>
)