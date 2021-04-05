package com.alliswell.patients

import androidx.lifecycle.*
import com.alliswell.data.AppRepository
import com.alliswell.data.Patient
import kotlinx.coroutines.launch

class PatientViewModel(private val repository: AppRepository): ViewModel() {
    val patients: LiveData<List<Patient>> = repository.patients.asLiveData()

    fun insert(patient: Patient) = viewModelScope.launch {
        repository.insert(patient)
    }

}

class PatientViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}