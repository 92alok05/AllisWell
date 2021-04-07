package com.alliswell.situation


import androidx.lifecycle.*
import com.alliswell.data.*
import kotlinx.coroutines.launch

class SituationViewModel(private val repository: AppRepository): ViewModel() {


    fun insert(situation: Situation): LiveData<Long> {
        val result = MutableLiveData<Long>()
        viewModelScope.launch {
            val id = repository.insert(situation)
            result.postValue(id)
        }
        return result

    }

    fun getSituationsForPatient(patientId: Int): LiveData<List<PatientWithSituations>> {
        return repository.getPatientWithSituations(patientId).asLiveData()
    }

    fun insert(patientSituation: PatientSituation) = viewModelScope.launch {
        repository.insert(patientSituation)
    }

    fun insert(parameter: Parameter) : LiveData<Long> {
        val result = MutableLiveData<Long>()
        viewModelScope.launch {
            val id = repository.insert(parameter)
            result.postValue(id)
        }
        return result
    }

    fun insert(situationParameter: SituationParameter) = viewModelScope.launch {
        repository.insert(situationParameter)
    }



}

class SituationViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SituationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SituationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}