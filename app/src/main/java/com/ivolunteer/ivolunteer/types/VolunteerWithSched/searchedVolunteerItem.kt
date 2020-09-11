package com.ivolunteer.ivolunteer.types.VolunteerWithSched

data class searchedVolunteerItem(
    val details: String,
    val isArchive: Boolean,
    val isCyclic: Boolean,
    val isOccupied: Boolean,
    val needHelpUser: Any,
    val needHelpUserId: String,
    val singleUser: Boolean,
    val volunteerCity: VolunteerCity,
    val volunteerCityId: Int,
    val volunteerId: Int,
    val volunteerScheduler: VolunteerScheduler,
    val volunteerSchedulerId: Int,
    val volunteerType: VolunteerType,
    val volunteerTypeId: Int,
    val volunteerUser: List<Any>
)