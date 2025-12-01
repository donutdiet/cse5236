package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val textSizeIncreasedLiveData = MutableLiveData(false)

    val isTextSizeIncreased: LiveData<Boolean>
        get() = textSizeIncreasedLiveData
    fun toggleTextSize() {
        val currentlyIncreased = textSizeIncreasedLiveData.value ?: false
        textSizeIncreasedLiveData.value = !currentlyIncreased
    }
}
