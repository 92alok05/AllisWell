package com.alliswell.details.ui.journal

import androidx.lifecycle.*
import com.alliswell.data.AppRepository
import com.alliswell.data.Detail
import com.alliswell.data.Patient
import com.alliswell.patients.PatientViewModel
import kotlinx.coroutines.launch

class JournalViewModel: ViewModel {

    lateinit var _repository: AppRepository

    constructor(repository: AppRepository) : super() {
        _repository = repository
    }

    fun insert(detail: Detail) = viewModelScope.launch {
        _repository.insert(detail)
    }

    fun getDetailsForPatientAndSituation(patientId: Int, situationId: Int): LiveData<List<Detail>> {
        return _repository.getDetailsForPatientAndSituation(patientId, situationId).asLiveData()
    }

}


class JournalViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JournalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}