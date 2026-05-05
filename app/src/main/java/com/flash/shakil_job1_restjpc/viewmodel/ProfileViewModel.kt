package com.flash.shakil_job1_restjpc.viewmodel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

class ProfileViewModel(private val context: Context) : ViewModel() {

    companion object {
        private val NAME_KEY = stringPreferencesKey("profile_name")
        private val EMAIL_KEY = stringPreferencesKey("profile_email")
        private val PHONE_KEY = stringPreferencesKey("profile_phone")
        private val BIO_KEY = stringPreferencesKey("profile_bio")
        private val AVATAR_URI_KEY = stringPreferencesKey("profile_avatar_uri")
    }

    private val _name = MutableStateFlow("John Doe")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _email = MutableStateFlow("john.doe@example.com")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio.asStateFlow()

    private val _avatarUri = MutableStateFlow("")
    val avatarUri: StateFlow<String> = _avatarUri.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                context.dataStore.data.collect { preferences ->
                    _name.value = preferences[NAME_KEY] ?: "John Doe"
                    _email.value = preferences[EMAIL_KEY] ?: "john.doe@example.com"
                    _phone.value = preferences[PHONE_KEY] ?: ""
                    _bio.value = preferences[BIO_KEY] ?: ""
                    _avatarUri.value = preferences[AVATAR_URI_KEY] ?: ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePhone(phone: String) {
        _phone.value = phone
    }

    fun updateBio(bio: String) {
        _bio.value = bio
    }

    fun updateAvatarUri(uri: String) {
        _avatarUri.value = uri
    }

    fun saveProfile() {
        viewModelScope.launch {
            try {
                context.dataStore.edit { preferences ->
                    preferences[NAME_KEY] = _name.value
                    preferences[EMAIL_KEY] = _email.value
                    preferences[PHONE_KEY] = _phone.value
                    preferences[BIO_KEY] = _bio.value
                    preferences[AVATAR_URI_KEY] = _avatarUri.value
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun cancelChanges() {
        loadProfile()
    }
}
