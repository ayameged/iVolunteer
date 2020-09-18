package com.ivolunteer.ivolunteer.types.VolunteerWithSched

data class volunteerwithvolUser(
    val details: String,
    val isArchive: Boolean,
    val isCyclic: Boolean,
    val isOccupied: Boolean,
    val needHelpUser: NeedHelpUser,
    val needHelpUserId: String,
    val singleUser: Boolean,
    val volunteerCity: VolunteerCityX,
    val volunteerCityId: Int,
    val volunteerId: Int,
    val volunteerScheduler: VolunteerSchedulerX,
    val volunteerSchedulerId: Int,
    val volunteerType: VolunteerTypeX,
    val volunteerTypeId: Int,
    val volunteerUser: List<VolunteerUser>
)