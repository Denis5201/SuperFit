package com.example.superfit.data.repository

import com.example.superfit.data.SharedPreferences
import com.example.superfit.domain.repository.LocalSettings
import javax.inject.Inject

class LocalSettingsImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : LocalSettings {
    override fun getFirstRunApp(): Boolean {
        return sharedPreferences.getBoolean(SharedPreferences.FIRST_RUN)
    }

    override fun clearUserInfo() {
        sharedPreferences.clearUserInfo()
    }

    override fun getUserLogin(): String? {
        return sharedPreferences.getString(SharedPreferences.USER_EMAIL)
    }
}