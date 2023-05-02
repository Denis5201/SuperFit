package com.example.superfit.domain.repository

interface LocalSettings {

    fun getFirstRunApp(): Boolean

    fun clearUserInfo()

    fun getUserLogin(): String?
}