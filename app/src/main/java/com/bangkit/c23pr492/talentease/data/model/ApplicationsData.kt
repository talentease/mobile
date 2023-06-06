package com.bangkit.c23pr492.talentease.data.model

import com.bangkit.c23pr492.talentease.R

object ApplicationsData {
    private val applicationName = arrayOf(
        "Michael",
        "John",
        "Doe"
    )

    private val applicationPhoto = arrayOf(
        R.drawable.profilepicture,
        R.drawable.profilepicture,
        R.drawable.profilepicture
    )

    private val applicationPosition = arrayOf(
        "Cloud Engineer",
        "Mobile Developer",
        "Machine Learning Engineer"
    )

    private val applicationStatus = arrayOf(
        "Screening",
        "Interview",
        "Unread"
    )

    private val applicationCreatedAt = arrayOf(
        "1 Mei",
        "2 Mei",
        "3 Mei"
    )

    val listData: ArrayList<ApplicationModel>
        get() {
            val list = arrayListOf<ApplicationModel>()
            var id = 0
            repeat(4) {
                for (position in applicationName.indices) {
                    val application = ApplicationModel()
                    application.id = id
                    application.name = applicationName[position]
                    application.photo = applicationPhoto[position]
                    application.position = applicationPosition[position]
                    application.status = applicationStatus[position]
                    application.createdAt = applicationCreatedAt[position]
                    list.add(application)
                    id += 1
                }
            }
            return list
        }
}