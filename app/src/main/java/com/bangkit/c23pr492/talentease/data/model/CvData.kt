package com.bangkit.c23pr492.talentease.data.model

object CvData {
    private val name = "Fatkhi Nur Akhsan"
    private val location = "Kudus"
    private val contact = listOf("https://www.linkedin.com/in/fnakhsan/")
    private val summary = "Currently, I'm Lead at GDSC UIN Sunan Kalijaga Yogyakarta and learn native Android development"
    private val experience = "Bangkit Academy led by Google, Tokopedia, Gojek, & Traveloka Mobile Development Student"
    private val education = "UIN Sunan Kalijaga Yogyakarta S1"
    private val expertise = "Android Development"
    private val award = "gk punya"
    private val certification = "Belajar Pengembangan Aplikasi Android Intermediate"

    val listCvData: ArrayList<CurriculumVitaeModel>
        get() {
            val list = arrayListOf<CurriculumVitaeModel>()
            repeat(10) {
                for (position in name.indices) {
                    val cvData = CurriculumVitaeModel()
                    cvData.name = name
                    cvData.location = location
                    cvData.contact = contact
                    cvData.summary = summary
                    cvData.experience = experience
                    cvData.education = education
                    cvData.expertise = expertise
                    cvData.award = award
                    cvData.certification = certification
                    list.add(cvData)
                }
            }
            return list
        }
}