package com.alliswell.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val patientDao: PatientDao,
    private val situationDao: SituationDao,
    private val patientSituationDao: PatientSituationDao,
    private val patientWithSituationsDao: PatientWithSituationsDao
    ) {

    val patients = patientDao.getAll()

    @WorkerThread
    suspend fun insert(patient: Patient) {
        patientDao.insert(patient)
    }

    @WorkerThread
    suspend fun insert(situation: Situation): Long {
        return situationDao.insert(situation)
    }

    @WorkerThread
    suspend fun insert(patientSituation: PatientSituation) {
        patientSituationDao.insert(patientSituation)
    }

    @WorkerThread
    fun getPatientWithSituations(patientId: Int): Flow<List<PatientWithSituations>> {
        return patientWithSituationsDao.getPatientWithSituations(patientId);
    }
}