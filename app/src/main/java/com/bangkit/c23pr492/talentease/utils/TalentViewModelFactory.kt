package com.bangkit.c23pr492.talentease.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.c23pr492.talentease.data.TalentRepository
import com.bangkit.c23pr492.talentease.di.Injection
import com.bangkit.c23pr492.talentease.ui.talent.application.TalentApplicationViewModel
import com.bangkit.c23pr492.talentease.ui.talent.vacancy.VacancyViewModel
import com.bangkit.c23pr492.talentease.ui.talent.vacancy.detail.DetailVacancyViewModel

class TalentViewModelFactory private constructor(private val repository: TalentRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(VacancyViewModel::class.java) -> return VacancyViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(DetailVacancyViewModel::class.java) -> return DetailVacancyViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(TalentApplicationViewModel::class.java) -> return TalentApplicationViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: TalentViewModelFactory? = null
        fun getInstance(context: Context): TalentViewModelFactory = instance ?: synchronized(this) {
            instance ?: TalentViewModelFactory(Injection.provideTalentRepository(context))
        }.also { instance = it }
    }
}