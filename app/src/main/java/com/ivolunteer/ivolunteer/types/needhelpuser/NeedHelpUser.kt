package com.ivolunteer.ivolunteer.types.needhelpuser

data class NeedHelpUser(
    val aboutMe: Any,
    val applicationUser: ApplicationUser,
    val id: String,
    val needHelpCity: NeedHelpCity,
    val needHelpCityId: Int,
    val volunteers: Any
)